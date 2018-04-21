package com.dewpoint.rts.bdd.steps.glue.user;

import com.dewpoint.rts.bdd.steps.helpers.BackgroundData;
import com.dewpoint.rts.bdd.steps.helpers.DefaultBuilders;
import com.dewpoint.rts.bdd.steps.helpers.GenericValidator;
import com.dewpoint.rts.dto.UserDTO;
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
public class CreateUser {

    @NonNull
    private BackgroundData backgroundData;
    @Getter
    private HttpHeaders headers = new HttpHeaders();

    @NonNull
    private GenericValidator genericValidator;

    private RestTemplate restTemplate = new RestTemplate();

    @When("^the client issues a POST request to the uri /users with user details$")
    public void theClientIssuesAPOSTRequestToTheUriUsersWithUserDetails() throws Throwable {

        backgroundData.an_active_user (ApiConstants.ROLE_ADMINISTRATOR);
        headers = backgroundData.getHeaders ();
        headers.setContentType(MediaType.APPLICATION_JSON);
        UserDTO userDTO = DefaultBuilders.createDefaultUserDTO ();
        HttpEntity<UserDTO> request = new HttpEntity<>(userDTO, headers);
        ResponseEntity<String> users = restTemplate.exchange(DefaultBuilders.REST_SERVICE_URI+"/users/", HttpMethod.POST, request, String.class);
        genericValidator.setServiceResponseEntity (users);
    }

    @Then("^the new user is created in the system$")
    public void theNewUserIsCreatedInTheSystem() throws Throwable {
        assertThat (genericValidator.getServiceResponseEntity().getStatusCode ()).isEqualTo (HttpStatus.OK);

    }
}
