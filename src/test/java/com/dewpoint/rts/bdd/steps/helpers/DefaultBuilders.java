package com.dewpoint.rts.bdd.steps.helpers;

import com.dewpoint.rts.dto.CandidateDTO;
import com.dewpoint.rts.dto.UserDTO;
import com.dewpoint.rts.dto.UserRequestDTO;
import com.dewpoint.rts.model.User;
import com.dewpoint.rts.util.ApiConstants;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;

import java.math.BigDecimal;

public class DefaultBuilders {

    public static final String REST_SERVICE_URI = "http://localhost:8087/rts-service/v1";
    public static final String TEST_USER_ID = "testuser";
    public static final String TEST_CANDIDATE_FIRST_NAME = "TestFirst";
    public static final String TEST_CANDIDATE_LAST_NAME = "TestLast";
    public static final String TEST_CANDIDATE_ID = "TestFirst_TestLast";
    public static final String TEST_CANDIDATE_EMAIL = "hello@dewtest.com";

    public static CandidateDTO createDefaultCandidateDTO() {

        CandidateDTO candidateDTO = new CandidateDTO();
        candidateDTO.setCurrentJobTitle ("SDET");
        candidateDTO.setClientCity ("Okemos");
        candidateDTO.setClientName ("DD");
        candidateDTO.setClientState ("MI");
        candidateDTO.setClientZip ("48864");
        candidateDTO.setBillRate (new BigDecimal (80.12));
        candidateDTO.setFirstName (DefaultBuilders.TEST_CANDIDATE_FIRST_NAME);
        candidateDTO.setLastName (DefaultBuilders.TEST_CANDIDATE_LAST_NAME);
        candidateDTO.setLastJobTitle ("JSDET");
        candidateDTO.setPhoneNumber ("123-456-7890");
        candidateDTO.setSkills ("Java, Spring, Hibernate, .NET");
        candidateDTO.setSource ("Referral");
        candidateDTO.setEmail (DefaultBuilders.TEST_CANDIDATE_EMAIL);
        return candidateDTO;
    }

    public static UserDTO createDefaultUserDTO() {
        UserDTO userDTO = new UserDTO ();
        userDTO.setUserFullName ("Test User Fullname");
        userDTO.setRole ("User");
        userDTO.setUserId (DefaultBuilders.TEST_USER_ID);
        userDTO.setStatus (ApiConstants.USER_STATUS_ACTIVE);
        return userDTO;
    }

    public static UserRequestDTO updateDefaultUserDTO() {
        UserRequestDTO userDTO = new UserRequestDTO ();
        userDTO.setUserFullName ("Test User Fullname");
        userDTO.setRole ("User");
        userDTO.setUserId (DefaultBuilders.TEST_USER_ID);
        userDTO.setPassword ("newPassword");
        return userDTO;
    }

    public static HttpHeaders getDefaultAdminAuthorizationHeader() {
        return getAuthorizationHeader(ApiConstants.API_ADMIN_USER,ApiConstants.API_ADMIN_USER_PWD);
    }

    public static HttpHeaders getDefaultUserAuthorizationHeader() {
        return getAuthorizationHeader(ApiConstants.API_USER,ApiConstants.API_USER_PWD);
    }

    private static HttpHeaders getAuthorizationHeader(String userId, String password){
        HttpHeaders headers = new HttpHeaders ();
        String plainCreds = userId + ":" + password;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);
        headers.add("Authorization", "Basic " + base64Creds);
        return headers;
    }
}
