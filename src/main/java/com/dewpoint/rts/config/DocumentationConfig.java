package com.dewpoint.rts.config;

import com.dewpoint.rts.util.ApiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class DocumentationConfig {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public Docket api() {
        List<Parameter> listParams = new ArrayList<>();
        listParams.add(parameterBasicAuthorization());

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dewpoint.rts.controller"))
                .paths(PathSelectors.ant("/v1/**"))
                .build()
                .directModelSubstitute(LocalDateTime.class, String.class)
//                .globalOperationParameters(listParams)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "RTS REST API",
                "Resume Tracking Application API.",
                "API 1.0.0",
                "Terms of service",
                new Contact("Dew Admin", "www.xyz.com", "admin@xyz.com"),
                "License of API", "API license URL", Collections.emptyList());
    }


    private Parameter parameterBasicAuthorization(){
        String usernamePass =  ApiConstants.API_ADMIN_USER + ":" + ApiConstants.API_ADMIN_USER_PWD;
        String basicAuth = "Basic " + passwordEncoder.encode(usernamePass);
        return new ParameterBuilder().name("Authorization")
                .description("Basic authorization header")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .defaultValue(basicAuth)
                .build();
    }
}