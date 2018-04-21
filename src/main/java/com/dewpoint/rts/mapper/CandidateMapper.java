package com.dewpoint.rts.mapper;


import com.dewpoint.rts.dto.CandidateDTO;
import com.dewpoint.rts.dto.CandidateRequestDTO;
import com.dewpoint.rts.model.Candidate;
import com.dewpoint.rts.util.ApiConstants;
import com.dewpoint.rts.util.ApplicationStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.util.Date;

@Component
public class CandidateMapper {

    public Candidate formatCreateEntry(CandidateRequestDTO candidateRequestDTO, Integer sameNameCount, Principal principal) {
        Candidate candidate = new Candidate();
        candidate.setFirstName(candidateRequestDTO.getFirstName());
        candidate.setLastName(candidateRequestDTO.getLastName());
        candidate.setEmail(candidateRequestDTO.getEmail());
        candidate.setSkills(candidateRequestDTO.getSkills());
        candidate.setSource(candidateRequestDTO.getSource());
        candidate.setLastJobTitle(candidateRequestDTO.getLastJobTitle());
        candidate.setCurrentJobTitle(candidateRequestDTO.getCurrentJobTitle());
        candidate.setBillRate(candidateRequestDTO.getBillRate());
        candidate.setClientName(candidateRequestDTO.getClientName());
        candidate.setClientCity(candidateRequestDTO.getClientCity());
        candidate.setClientState(candidateRequestDTO.getClientState());
        candidate.setClientZip(candidateRequestDTO.getClientZip());
        candidate.setCreatedBy(principal.getName());
        candidate.setStatus(ApplicationStatus.NEVER_INTERVIEWED.getKey());
        // below field is internal and in future if website is open for end candidates to update their profile, this can be leveraged
        if(sameNameCount == null || sameNameCount.intValue() == 0) {
            candidate.setCandidateId(candidateRequestDTO.getFirstName().toLowerCase().substring(0, 1) + candidateRequestDTO.getLastName().toLowerCase());
        } else {
            candidate.setCandidateId(candidateRequestDTO.getFirstName().toLowerCase().substring(0, 1) + candidateRequestDTO.getLastName().toLowerCase() + sameNameCount);
        }
        candidate.setCreatedOn(new Date());
        return candidate;
    }

    public Candidate formatSearchEntry(CandidateRequestDTO candidateRequestDTO) {
        Candidate candidate = new Candidate();
        candidate.setFirstName(candidateRequestDTO.getFirstName());
        candidate.setLastName(candidateRequestDTO.getLastName());
        return candidate;
    }

    public Candidate formatUpdateEntry(Candidate candidate, CandidateRequestDTO candidateRequestDTO, Principal principal) {

        if(!StringUtils.isEmpty(candidateRequestDTO.getFirstName())) {
            candidate.setFirstName(candidateRequestDTO.getFirstName());
        }

        if(!StringUtils.isEmpty(candidateRequestDTO.getLastName())) {
            candidate.setLastName(candidateRequestDTO.getLastName());
        }

        if(!StringUtils.isEmpty(candidateRequestDTO.getEmail())) {
            candidate.setEmail(candidateRequestDTO.getEmail());
        }

        if(!StringUtils.isEmpty(candidateRequestDTO.getSkills())) {
            candidate.setSkills(candidateRequestDTO.getSkills());
        }

        if(!StringUtils.isEmpty(candidateRequestDTO.getSource())) {
            candidate.setSource(candidateRequestDTO.getSource());
        }

        if(!StringUtils.isEmpty(candidateRequestDTO.getLastJobTitle())) {
            candidate.setLastJobTitle(candidateRequestDTO.getLastJobTitle());
        }

        if (!StringUtils.isEmpty(candidateRequestDTO.getClientName())) {
            candidate.setClientName(candidateRequestDTO.getClientName());
        }

        if (!StringUtils.isEmpty(candidateRequestDTO.getClientCity())) {
            candidate.setClientCity(candidateRequestDTO.getClientCity());
        }

        if (!StringUtils.isEmpty(candidateRequestDTO.getClientState())) {
            candidate.setClientState(candidateRequestDTO.getClientState());
        }

        if (!StringUtils.isEmpty(candidateRequestDTO.getClientZip())) {
            candidate.setClientZip(candidateRequestDTO.getClientZip());
        }

        if(!StringUtils.isEmpty(candidateRequestDTO.getCurrentJobTitle())) {
            candidate.setCurrentJobTitle(candidateRequestDTO.getCurrentJobTitle());
        }

        if(!StringUtils.isEmpty(candidateRequestDTO.getBillRate())) {
            candidate.setBillRate(candidateRequestDTO.getBillRate());
        }

        if(!StringUtils.isEmpty(candidateRequestDTO.getApplicationStatus())) {
            candidate.setStatus(candidateRequestDTO.getApplicationStatus());
        }
        candidate.setModifiedBy(principal.getName());
        candidate.setModifiedOn(new Date());
        return candidate;
    }

    public CandidateDTO convertEntityToDTO(Candidate candidate) {
        CandidateDTO candidateDTO = new CandidateDTO();
        candidateDTO.setApplicationStatus(candidate.getStatus());
        candidateDTO.setBillRate(candidate.getBillRate());
        candidateDTO.setCurrentJobTitle(candidate.getCurrentJobTitle());
        candidateDTO.setDateAvailable(candidate.getDateAvailable());
        candidateDTO.setEmail(candidate.getEmail());
        candidateDTO.setFirstName(candidate.getFirstName());
        candidateDTO.setLastJobTitle(candidate.getLastJobTitle());
        candidateDTO.setLastName(candidate.getLastName());
        candidateDTO.setPhoneNumber(candidate.getPhoneNumber());
        candidateDTO.setSkills(candidate.getSkills());
        candidateDTO.setSource(candidate.getSource());
        candidateDTO.setClientName(candidate.getClientName());
        candidateDTO.setClientCity(candidate.getClientCity());
        candidateDTO.setClientState(candidate.getClientState());
        candidateDTO.setClientZip(candidate.getClientZip());
        candidateDTO.setCandidateId(candidate.getCandidateId());

        return candidateDTO;
    }
}
