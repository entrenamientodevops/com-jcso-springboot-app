package com.jcso.springboot.app.service.oauth.security;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

@RefreshScope
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final Environment environment;

    private final BCryptPasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthorizationServerConfig(BCryptPasswordEncoder passwordEncoder, @Qualifier("authenticationManager") AuthenticationManager authenticationManager, AditionalInfoToken aditionalInfoToken, Environment environment) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.aditionalInfoToken = aditionalInfoToken;
        this.environment = environment;
    }

    private final AditionalInfoToken aditionalInfoToken;


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        System.out.println("Esta por: AuthorizationServerConfig.configure(AuthorizationServerSecurityConfigurer security)" );
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        System.out.println("Esta por: AuthorizationServerConfig.configure(ClientDetailsServiceConfigurer clients)" );
        clients.inMemory().withClient(environment.getProperty("config.security.oauth.client.id"))
                .secret(passwordEncoder.encode(environment.getProperty("config.security.oauth.client.secret")))
                .scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(3600);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        System.out.println("Esta por: AuthorizationServerConfig.configure(AuthorizationServerEndpointsConfigurer endpoints) " );

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();

        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(aditionalInfoToken, accessToken()));


        endpoints.authenticationManager(authenticationManager)
                .tokenStore( tokenStore())
                .accessTokenConverter(accessToken())
                .tokenEnhancer(tokenEnhancerChain);
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(accessToken());
    }

    @Bean
    public JwtAccessTokenConverter accessToken() {
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        tokenConverter.setSigningKey(environment.getProperty("config.security.oauth.jwt.key"));
        return tokenConverter;
    }


}
