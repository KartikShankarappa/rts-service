package com.dewpoint.rts.util;

import com.dewpoint.rts.dto.CandidateRequestDTO;
import com.dewpoint.rts.dto.CandidateResponseDTO;
import com.dewpoint.rts.dto.CandidateSearchRequestDTO;
import com.dewpoint.rts.dto.CandidateSearchResponseDTO;
import com.dewpoint.rts.dto.UserRequestDTO;
import com.dewpoint.rts.dto.UserResponseDTO;
import com.dewpoint.rts.errorconfig.ApiOperationException;
import com.dewpoint.rts.service.*;

public class ApiValidation {

    private ApiValidation(){
        // private constructor
    }

    public static void validateSearchUserRequest(String userId){
        if(userId == null || userId.isEmpty()) {
            throwException("Please supply userId.");
        }
    }

    public static void validateRetriveAllUsersResponse(UserResponseDTO response){
        if(response == null || response.getUsers() == null || response.getUsers().size() == 0) {
            throwException("Unable to find matching users. Refine your search criteria.");
        }
    }

    public static void validateSearchUserResponse(UserResponseDTO response){
        if(response == null || response.getUsers() == null || response.getUsers().size() == 0) {
            throwException("Unable to find matching user records.");
        }
    }

    public static void validateCreateUserRequest(UserRequestDTO requestDTO){
        if(requestDTO == null || requestDTO.getUserId() == null || requestDTO.getRole() == null || requestDTO.getUserFullName() == null) {
            throwException("Please supply unique user id, role name and user full name.");
        }
    }

    public static void validateUpdateUserRequest(UserRequestDTO requestDTO){
        if(requestDTO == null || requestDTO.getUserId() == null || requestDTO.getPassword() == null) {
            throwException("Please supply user id and password.");
        }
    }

    public static void validateRetriveAllCandidatesResponse(CandidateResponseDTO response){
        if(response == null || response.getCandidates() == null || response.getCandidates().size() == 0) {
            throwException("Unable to find matching candidates. Refine your search criteria.");
        }
    }

    public static void validateSearchCandidateRequest(String candidateId){
        if(candidateId == null || candidateId.isEmpty()) {
            throwException("Please supply candidate id");
        }
    }

    public static void validateSearchCandidateResponse(CandidateResponseDTO response){
        if(response == null || response.getCandidates() == null || response.getCandidates().size() == 0) {
            throwException("Unable to find matching candidate records.");
        }
    }

    public static void validateCreateCandidateRequest(CandidateRequestDTO requestDTO){
        if(requestDTO == null || requestDTO.getFirstName() == null
        || requestDTO.getLastName() == null || requestDTO.getEmail() == null
                || requestDTO.getSkills() == null) {
            throwException("Please supply mandatory fields (first name, last name, email, skills) to create new candidate profile.");
        }
    }

    public static void validateUpdateCandidateRequest(CandidateRequestDTO requestDTO){
        if(requestDTO == null
                || requestDTO.getFirstName() == null
                || requestDTO.getLastName() == null) {
            throwException("Please supply mandatory first and last name field values and any other fields to update existing candidate profile.");
        }
    }

    public static void validateSearchCandidatesRequest(CandidateSearchRequestDTO requestDTO){
        if(requestDTO == null
                || (requestDTO.getResumeKeywords() == null
                       && requestDTO.getSkills() == null)) {
            throwException("Please supply correct search criteria");
        }
    }

    public static void validateSearchCandidatesResponse(CandidateSearchResponseDTO response){
        if(response == null || response.getCandidates() == null || response.getCandidates().size() == 0) {
            throwException("Unable to find matching candidate records. Refine your search criteria.");
        }
    }

    private static void throwException(String exceptionMessage) {
        throw new ApiOperationException(exceptionMessage);
    }
}
