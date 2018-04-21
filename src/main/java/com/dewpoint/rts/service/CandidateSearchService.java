package com.dewpoint.rts.service;

import com.dewpoint.rts.dto.CandidateDTO;
import com.dewpoint.rts.dto.CandidateResponseDTO;
import com.dewpoint.rts.dto.CandidateSearchRequestDTO;
import com.dewpoint.rts.dto.CandidateSearchResponseDTO;
import com.dewpoint.rts.dto.CandidateSummaryDTO;
import com.dewpoint.rts.errorconfig.ApiOperationException;
import com.dewpoint.rts.model.Candidate;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This service is responsible for retrieving information from Lucene index files
 */
@Component
public class CandidateSearchService {

    private final static Logger logger = Logger.getLogger("CandidateSearchService");

    @Value("${document.url}")
    private String documentURL;

    @Value("${document.directorylocation}")
    private String documentLocation;

    @Autowired
    private final EntityManager entityManager;

    @Autowired
    public CandidateSearchService(EntityManager entityManager) {
       this.entityManager = entityManager;
    }

    public void initializeLuceneSearch() {
        try {
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Could not initialize hibernate Lucene Indexing " + e.getMessage());
        }
    }

    @Transactional
    public CandidateSearchResponseDTO searchForCandidates(CandidateSearchRequestDTO searchRequestDTO) {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Candidate.class).get();
        String searchWords = searchRequestDTO.getStatus() + " " + searchRequestDTO.getSource() + " "
                + searchRequestDTO.getClientName() + " " + searchRequestDTO.getClientCity() + " " + searchRequestDTO.getClientState()
                + " " + searchRequestDTO.getClientZip();


//        Query luceneResumeQuery = qb.keyword();

//        Query luceneSkillsQuery = qb.keyword()
//                .onFields("skills")
//                .matching(searchRequestDTO.getSkills().toLowerCase())
//                .createQuery();

//        Query luceneOptionalQuery = qb.keyword()
//                .wildcard()
//                .onFields("status", "source", "email", "clientName", "clientCity", "clientState", "clientZip")
//                .matching(searchWords.toLowerCase())
//                .createQuery();
//
//        Query combinedQuery = qb
//                .bool()
//                .should( luceneSkillsQuery )
//                .should( luceneOptionalQuery )
//                .createQuery();

        Query luceneSkillsQuery = qb.keyword()
                .onFields("status", "source", "email", "clientName", "clientCity", "clientState", "clientZip")
                .matching(searchWords)
                .createQuery();

        javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneSkillsQuery, Candidate.class);

        List<Candidate> candidateList = new ArrayList<>();
        try {
            candidateList = jpaQuery.getResultList();
        } catch (NoResultException nre) {
            logger.log(Level.WARNING, "Could not initialize hibernate Lucene Indexing " + nre.getMessage());
        }

        CandidateSearchResponseDTO response = new CandidateSearchResponseDTO();

        if(!candidateList.isEmpty()) {
            List<CandidateSummaryDTO> candidatesPartialList = new ArrayList<>();
            for(Candidate aCandidate: candidateList) {
                candidatesPartialList.add(convertEntityToSummaryDTO(aCandidate));
            }
            response.setCandidates(candidatesPartialList);
        }

        return response;
    }

    public CandidateSearchResponseDTO searchForCandidates(CandidateResponseDTO candidateResponseDTO) {
        CandidateSearchResponseDTO response = new CandidateSearchResponseDTO();

        if(candidateResponseDTO != null
                && !candidateResponseDTO.getCandidates().isEmpty()) {
            List<CandidateSummaryDTO> candidatesPartialList = new ArrayList<>();
            for(CandidateDTO aCandidate: candidateResponseDTO.getCandidates()) {
                candidatesPartialList.add(convertEntityToSummaryDTO(aCandidate));
            }
            response.setCandidates(candidatesPartialList);
        }

        return response;
    }

    private CandidateSummaryDTO convertEntityToSummaryDTO(Candidate candidate) {
        CandidateSummaryDTO candidateDTO = new CandidateSummaryDTO();
        candidateDTO.setStatus(candidate.getStatus());
        candidateDTO.setEmail(candidate.getEmail());
        candidateDTO.setFirstName(candidate.getFirstName());
        candidateDTO.setLastName(candidate.getLastName());

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(documentURL);
        candidateDTO.setResumeLink(uriComponentsBuilder.buildAndExpand(candidate.getCandidateId()).toString());
        return candidateDTO;
    }

    private CandidateSummaryDTO convertEntityToSummaryDTO(CandidateDTO candidate) {
        CandidateSummaryDTO candidateDTO = new CandidateSummaryDTO();
        candidateDTO.setStatus(candidate.getApplicationStatus());
        candidateDTO.setEmail(candidate.getEmail());
        candidateDTO.setFirstName(candidate.getFirstName());
        candidateDTO.setLastName(candidate.getLastName());
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(documentURL);
        candidateDTO.setResumeLink(uriComponentsBuilder.buildAndExpand(candidate.getFirstName() + "_" + candidate.getLastName()).toString());
        return candidateDTO;
    }

    public File identifyFile(String candidateId) {
        String resumeName = searchForCandidateResume(candidateId);
        if (resumeName == null || resumeName.isEmpty()) {
            throw new ApiOperationException("Missing Resume for candidate " + candidateId + ". Please upload candidate resume.");
        }

        return new File(documentLocation + resumeName);
    }

    @Transactional
    public String searchForCandidateResume(String candidateId) {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Candidate.class).get();
        Query luceneQuery = qb.keyword()
                .onField("candidateId")
                .matching(candidateId.toLowerCase())
                .createQuery();

        javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Candidate.class);

        List<Candidate> candidateList = new ArrayList<>();
        try {
            candidateList = jpaQuery.getResultList();
        } catch (NoResultException nre) {
            logger.log(Level.WARNING, "Could not initialize hibernate Lucene Indexing " + nre.getMessage());
        }

        // handle multiple resumes scenario
        Candidate candidate = candidateList.get(0);
        return candidate.getCandidateId();
    }

    public String determineMediaTypeFromFile(File file){
        return MediaType.APPLICATION_PDF_VALUE;
    }
}