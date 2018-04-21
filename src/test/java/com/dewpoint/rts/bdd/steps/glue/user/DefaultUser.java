package com.dewpoint.rts.bdd.steps.glue.user;

import com.dewpoint.rts.bdd.steps.helpers.BackgroundData;
import com.dewpoint.rts.bdd.steps.helpers.GenericValidator;
import cucumber.api.java.en.Given;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class DefaultUser {

    @NonNull
    private BackgroundData backgroundData;
    @Getter
    private HttpHeaders headers = new HttpHeaders();

    @NonNull
    private GenericValidator genericValidator;

    @NonNull
    private CreateUser createUser;

    @NonNull
    private  InactivateUserStatus inactivateUserStatus;

    private RestTemplate restTemplate = new RestTemplate();

    @Given("^(a|an active|an Inactive) user in the system$")
    public void aUserInTheSystem(String userStatus) throws Throwable {

        if(userStatus.equalsIgnoreCase ("a")){
            createUser.theClientIssuesAPOSTRequestToTheUriUsersWithUserDetails();
        } else if(userStatus.equalsIgnoreCase ("an active")) {
            createUser.theClientIssuesAPOSTRequestToTheUriUsersWithUserDetails();
        } else if(userStatus.equalsIgnoreCase ("an Inactive")) {
            createUser.theClientIssuesAPOSTRequestToTheUriUsersWithUserDetails();
            inactivateUserStatus.theClientIssuesADELETERequestToTheUriUsersUserIdInactivateWithUserId ();
        }

    }

}
