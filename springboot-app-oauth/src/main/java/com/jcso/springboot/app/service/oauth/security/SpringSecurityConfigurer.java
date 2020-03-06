package com.jcso.springboot.app.service.oauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
public class SpringSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final AuthenticationEventPublisher eventPublisher;

    private final UserDetailsService userService;

    public SpringSecurityConfigurer(@Qualifier("userService") UserDetailsService userService, AuthenticationEventPublisher eventPublisher) {
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("Esta por: SpringSecurityConfigurer.configure(AuthenticationManagerBuilder auth)" );
        auth.userDetailsService(this.userService)
            .passwordEncoder(passwordEncoder())
            .and()
            .authenticationEventPublisher(eventPublisher);
    }

    @Override
    @Bean("authenticationManager")
    protected AuthenticationManager authenticationManager() throws Exception {
        System.out.println("Esta por: SpringSecurityConfigurer.AuthenticationManager authenticationManager()" );
        return super.authenticationManager();
    }

}
