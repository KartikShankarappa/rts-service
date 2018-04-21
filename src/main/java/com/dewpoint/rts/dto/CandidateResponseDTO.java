package com.dewpoint.rts.dto;

import java.util.List;

public class CandidateResponseDTO {

   private List<CandidateDTO> candidates;

    public List<CandidateDTO> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<CandidateDTO> candidates) {
        this.candidates = candidates;
    }

    @Override
    public String toString() {
        return "CandidateResponseDTO{" +
                "candidates=" + candidates +
                '}';
    }
}
