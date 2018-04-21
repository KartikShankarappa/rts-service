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
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class ResetUserPassword {

    @NonNull
    private BackgroundData backgroundData;
    @Getter
    private HttpHeaders headers = new HttpHeaders();

    @NonNull
    private GenericValidator genericValidator;

    @NonNull
    private GetUserDetailsByID getUserDetailsByID;

    @NonNull
    private ChangeUserDefaultPassword changeUserDefaultPassword;

    private RestTemplate restTemplate = new RestTemplate();

    @When("^the client issues a PUT request to the uri /users/userId/reset with password reset user details$")
    public void theClientIssuesAPUTRequestToTheUriUsersWithModifiedUserDetails() throws Throwable {
        backgroundData.an_active_user (ApiConstants.ROLE_ADMINISTRATOR);
        headers = backgroundData.getHeaders ();
        changeUserDefaultPassword.theClientIssuesAPUTRequestToTheUriUsersWithNewPasswordAndUserDetails ();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> users = restTemplate.exchange(DefaultBuilders.REST_SERVICE_URI+"/users/" + DefaultBuilders.TEST_USER_ID + "/reset", HttpMethod.PUT, request, String.class);
        genericValidator.setServiceResponseEntity (users);
    }

    @Then ("^the user password is reset to default password in the system$")
    public void theUserPasswordIsResetToDefaultPasswordInTheSystem() throws Throwable {
        getUserDetailsByID.theClientIssuesAGETRequestToTheUriUsersWithUserId ();
         UserResponseDTO responseDTO = (UserResponseDTO) genericValidator.getServiceResponseEntity ().getBody ();
        assertThat (responseDTO.getUsers ().get (0).getUserId ()).isEqualToIgnoringCase (DefaultBuilders.TEST_USER_ID);
    }
}
