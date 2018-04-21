package com.dewpoint.rts.bdd.steps.helpers;

import cucumber.api.java.en.Given;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpHeaders;

@AllArgsConstructor
public class BackgroundData {

    @Getter
    private HttpHeaders headers;

    @Given("^a client with an active primary (User|Administrator) account$")
    public void an_active_user(String userType) throws Throwable {
        if(userType.equalsIgnoreCase ("User")){
            this.headers = DefaultBuilders.getDefaultUserAuthorizationHeader ();
        } else if(userType.equalsIgnoreCase ("Administrator")){
            this.headers = DefaultBuilders.getDefaultAdminAuthorizationHeader ();
        }
    }
}
