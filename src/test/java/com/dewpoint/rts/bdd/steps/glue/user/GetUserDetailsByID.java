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
public class GetUserDetailsByID {

    @NonNull
    private BackgroundData backgroundData;
    @Getter
    private HttpHeaders headers = new HttpHeaders();

    @NonNull
    private GenericValidator genericValidator;

    @NonNull
    private CreateUser createUser;

    private RestTemplate restTemplate = new RestTemplate();
    @When("^the client issues a GET request to the uri /users with user id$")
    public void theClientIssuesAGETRequestToTheUriUsersWithUserId() throws Throwable {
        backgroundData.an_active_user (ApiConstants.ROLE_ADMINISTRATOR);
        headers = backgroundData.getHeaders ();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<UserResponseDTO> users = restTemplate.exchange(DefaultBuilders.REST_SERVICE_URI+"/users/" + DefaultBuilders.TEST_USER_ID, HttpMethod.GET, request, UserResponseDTO.class);
        genericValidator.setServiceResponseEntity (users);
    }

    @Then("^the user details are retrieved from the system$")
    public void theUserDetailsAreRetrievedFromTheSystem() throws Throwable {
        assertThat (genericValidator.getServiceResponseEntity ().getStatusCode ()).isEqualTo (HttpStatus.OK);
        UserResponseDTO userResponseDTO = (UserResponseDTO) genericValidator.getServiceResponseEntity ().getBody ();
        assertThat (userResponseDTO.getUsers ().get (0).getUserId ()).isEqualToIgnoringCase (DefaultBuilders.TEST_USER_ID);
    }
}
