package com.dewpoint.rts.bdd;

import com.dewpoint.rts.SpringBootRestApiApp;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
@SpringBootTest(
        classes = SpringBootRestApiApp.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public abstract class ApiGatewayFeatureTest {
}
