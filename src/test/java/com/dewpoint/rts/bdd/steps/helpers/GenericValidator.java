package com.dewpoint.rts.bdd.steps.helpers;

import cucumber.api.java.en.Then;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class GenericValidator {

    @Getter
    @Setter
    ResponseEntity<?> serviceResponseEntity;

    @Then("^the client receives status code of (\\d+)$")
    public void theClientReceivesStatusCodeOf(int statusCode) throws Throwable {
        //if(serviceResponseEntity!=null){
            assertThat (serviceResponseEntity.getStatusCode ().value ()).isEqualTo (statusCode);
        //}
    }
}
