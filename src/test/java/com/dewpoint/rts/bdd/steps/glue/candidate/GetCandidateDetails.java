package com.dewpoint.rts.bdd.steps.glue.candidate;

import com.dewpoint.rts.bdd.steps.helpers.BackgroundData;
import com.dewpoint.rts.bdd.steps.helpers.DefaultBuilders;
import com.dewpoint.rts.bdd.steps.helpers.GenericValidator;
import com.dewpoint.rts.dto.CandidateResponseDTO;
import com.dewpoint.rts.util.ApiConstants;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class GetCandidateDetails {


    @NonNull
    private BackgroundData backgroundData;
    @Getter
    private HttpHeaders headers = new HttpHeaders();

    private RestTemplate restTemplate = new RestTemplate();

    @NonNull
    private GenericValidator genericValidator;

    @NonNull
    private CreateCandidate createCandidate;


    @Given ("^a candidate in the system$")
    public void aCandidateInTheSystem() throws Throwable {
        createCandidate.theClientIssuesAPOSTRequestToTheUriCandidatesWithNewCandidateDetails();
    }

    @When ("^the client issues a GET request to the uri /candidates with the candidate id$")
    public void theClientIssuesAGETRequestToTheUriCandidatesWithTheCandidateId() throws Throwable {
        backgroundData.an_active_user (ApiConstants.ROLE_USER);
        headers = backgroundData.getHeaders ();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<CandidateResponseDTO> candidateDTOResponseEntity = restTemplate.exchange(DefaultBuilders.REST_SERVICE_URI+"/candidates/" + DefaultBuilders.TEST_CANDIDATE_ID, HttpMethod.GET, request, CandidateResponseDTO.class);
        genericValidator.setServiceResponseEntity (candidateDTOResponseEntity);
    }

    @Then("^the candidate details are retrieved$")
    public void theCandidateResumeIsRetrieved() throws Throwable {
        assertThat (genericValidator.getServiceResponseEntity ().getStatusCode ()).isEqualTo (HttpStatus.OK);
        CandidateResponseDTO candidateResponseDTO = (CandidateResponseDTO) genericValidator.getServiceResponseEntity ().getBody ();
        assertThat (candidateResponseDTO.getCandidates ().size ()).isGreaterThan (0);
        assertThat (candidateResponseDTO.getCandidates ().get (0).getEmail ()).isEqualToIgnoringCase (DefaultBuilders.TEST_CANDIDATE_EMAIL);
    }
}
