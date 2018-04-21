package com.dewpoint.rts.bdd.steps.glue.user;

import com.dewpoint.rts.bdd.steps.helpers.BackgroundData;
import com.dewpoint.rts.bdd.steps.helpers.DefaultBuilders;
import com.dewpoint.rts.bdd.steps.helpers.GenericValidator;
import com.dewpoint.rts.dto.UserResponseDTO;
import com.dewpoint.rts.util.ApiConstants;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class InactivateUserStatus {
    @NonNull
    private BackgroundData backgroundData;
    @Getter
    private HttpHeaders headers = new HttpHeaders();

    @NonNull
    private GenericValidator genericValidator;

    @NonNull
    private ChangeUserDefaultPassword changeUserDefaultPassword;

    @NonNull
    private CreateUser createUser;

    @NonNull
    private GetUserDetailsByID getUserDetailsByID;

    private RestTemplate restTemplate = new RestTemplate();

    @When ("^the client issues a DELETE request to the uri /users with user id$")
    public void theClientIssuesADELETERequestToTheUriUsersUserIdInactivateWithUserId() throws Throwable {
        backgroundData.an_active_user (ApiConstants.ROLE_ADMINISTRATOR);
        headers = backgroundData.getHeaders ();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> users = restTemplate.exchange(DefaultBuilders.REST_SERVICE_URI+"/users/" + DefaultBuilders.TEST_USER_ID , HttpMethod.DELETE, request, String.class);
        genericValidator.setServiceResponseEntity (users);
    }

    @Then ("^the user is inactivated in the system$")
    public void theUserIsInactivatedInTheSystem() throws Throwable {
        getUserDetailsByID.theClientIssuesAGETRequestToTheUriUsersWithUserId ();
        UserResponseDTO userResponseDTO = (UserResponseDTO) genericValidator.getServiceResponseEntity ().getBody ();
        Assertions.assertThat (userResponseDTO.getUsers ().get (0).getStatus ()).isEqualToIgnoringCase (ApiConstants.USER_STATUS_INACTIVE);
    }
}
