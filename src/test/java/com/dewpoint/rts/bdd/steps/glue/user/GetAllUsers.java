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
public class GetAllUsers {

    @NonNull
    private BackgroundData backgroundData;
    @Getter
    private HttpHeaders headers = new HttpHeaders();

    @NonNull
    private GenericValidator genericValidator;

    private RestTemplate restTemplate = new RestTemplate();

    @When("^the client issues a get request to the uri /users$")
    public void theClientIssuesAGetRequestToTheUriUsers() throws Throwable {
        backgroundData.an_active_user (ApiConstants.ROLE_ADMINISTRATOR);
        headers = backgroundData.getHeaders ();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<UserResponseDTO> users = restTemplate.exchange(DefaultBuilders.REST_SERVICE_URI+"/users/", HttpMethod.GET, request, UserResponseDTO.class);
        genericValidator.setServiceResponseEntity (users);
    }

    @Then("^the client receives list of all users in the system$")
    public void theClientReceivesListOfAllUsersInTheSystem() throws Throwable {
        UserResponseDTO responseDTO = (UserResponseDTO) genericValidator.getServiceResponseEntity ().getBody ();
        assertThat (responseDTO.getUsers ().size ()).isGreaterThan (0);
    }
}
