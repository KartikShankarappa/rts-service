package com.dewpoint.rts.bdd.steps.glue.user;

import com.dewpoint.rts.bdd.steps.helpers.BackgroundData;
import com.dewpoint.rts.bdd.steps.helpers.DefaultBuilders;
import com.dewpoint.rts.bdd.steps.helpers.GenericValidator;
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
public class ActivateUserStatus {

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

    private RestTemplate restTemplate = new RestTemplate();


    @When ("^the client issues a PUT request to the uri /users/userId/activate with user id$")
    public void theClientIssuesAPUTRequestToTheUriUsersUserIdActivateWithUserId() throws Throwable {
        backgroundData.an_active_user (ApiConstants.ROLE_ADMINISTRATOR);
        headers = backgroundData.getHeaders ();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> users = restTemplate.exchange(DefaultBuilders.REST_SERVICE_URI+"/users/" + DefaultBuilders.TEST_USER_ID + "/activate", HttpMethod.PUT, request, String.class);
        genericValidator.setServiceResponseEntity (users);
    }

    @Then("^the user is activated in the system$")
    public void theUserIsUpdatedInTheSystem() throws Throwable {
        assertThat (genericValidator.getServiceResponseEntity ().getStatusCode ()).isEqualTo (HttpStatus.OK);
    }
}
