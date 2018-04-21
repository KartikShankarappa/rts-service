package com.dewpoint.rts.config;

import com.dewpoint.rts.service.AuthenticationService;
import com.dewpoint.rts.service.UserSecurityService;
import com.dewpoint.rts.util.ApiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/v1**")
                .hasAnyRole(ApiConstants.ROLE_ADMINISTRATOR, ApiConstants.ROLE_USER)
              //  .and().formLogin().loginPage("/index.html");
                .and().httpBasic().realmName(ApiConstants.APPLICATION_REALM)
                .authenticationEntryPoint(authenticationService);
    }

    @Autowired
    void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSecurityService);
    }
}