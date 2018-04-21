package com.dewpoint.rts.config;

import javax.persistence.EntityManager;

import com.dewpoint.rts.service.CandidateSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@Configuration
public class LuceneSearchConfig {

    @Autowired
    private EntityManager entityManager;

    @Bean
    CandidateSearchService luceneSearcher() {
        CandidateSearchService luceneSearcher = new CandidateSearchService(entityManager);
        luceneSearcher.initializeLuceneSearch();
        return luceneSearcher;
    }
}