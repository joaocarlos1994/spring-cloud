package com.thoughtmechanix.authentication.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;


/**
 * This {@code WebSecurityConfigurer} is responsible to settings a user in
 * memory and their credentials for authentication.
 * */
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    /**
     * The AuthenticationManagerBean is used by Spring Security to handle authentication
     * */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    /**
     * The UserDetailsService is used by Spring Security to handle user information that
     * will be returned the Spring Security
     * */
    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    /**
     * Method where is define users, their
     * passwords, and their roles.
     * */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                    .withUser("john.carnell")
                    .password("password1")
                    .roles("USER")
                .and()
                    .withUser("william.woodward")
                    .password("password2")
                    .roles("USER", "ADMIN");
    }
}
