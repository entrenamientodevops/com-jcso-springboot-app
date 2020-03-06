package com.jcso.springboot.app.service.oauth.security;

import com.jcso.springboot.api.users.commons.models.entity.User;
import com.jcso.springboot.app.service.oauth.service.IUserService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AditionalInfoToken implements TokenEnhancer {

    private final IUserService userService;

    public AditionalInfoToken(IUserService service) {
        this.userService = service;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> stringMap = new HashMap<String, Object>();

        User user = userService.findByUsername(authentication.getName());

        stringMap.put("Nombre", user.getName());
        stringMap.put("Apellido", user.getLastName());
        stringMap.put("Email", user.getEmail());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(stringMap);

        return accessToken;
    }
}
