package com.dewpoint.rts.dto;

import java.util.List;

public class CandidateSearchResponseDTO {

    private List<CandidateSummaryDTO> candidates;

    public List<CandidateSummaryDTO> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<CandidateSummaryDTO> candidates) {
        this.candidates = candidates;
    }

    @Override
    public String toString() {
        return "CandidateSearchResponseDTO{" +
                "candidates=" + candidates +
                '}';
    }
}
