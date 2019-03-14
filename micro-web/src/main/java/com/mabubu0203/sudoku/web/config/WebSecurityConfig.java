package com.mabubu0203.sudoku.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.securityContext().disable();
    }
}
