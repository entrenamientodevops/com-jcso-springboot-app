package com.jcso.springboot.app.service.oauth.security.event;

import brave.Tracer;
import com.jcso.springboot.api.users.commons.models.entity.User;
import com.jcso.springboot.app.service.oauth.service.IUserService;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

    private Logger log = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);
    private String message;
    
    @Value("${config.security.oauth.intents}")
    private Integer maxIntents; 

    private final IUserService userService;

    @Autowired
    private Tracer tracer;

    public AuthenticationSuccessErrorHandler(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {
        UserDetails user = (UserDetails)authentication.getPrincipal();

        message = "Success Login: " + user.getUsername();

        System.out.println(message);
        log.info(message);

        User userIntents = userService.findByUsername(authentication.getName());

        if(userIntents.getIntents() != null && userIntents.getIntents() > 0){
            userIntents.setIntents(0);
            userService.update(userIntents, userIntents.getId());
        }
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        message = "Login Error: " + exception.getMessage();
        System.out.println(message);
        log.error(message);

        try {
            StringBuilder errores = new StringBuilder();
            errores.append(message);
            User user = userService.findByUsername(authentication.getName());
            if(user.getIntents() == null){
                user.setIntents(0);
            }
            log.info("Numero de intentos: " + user.getIntents());

            user.setIntents(user.getIntents() + 1);

            log.info("Numero actualizado de intentos: " + user.getIntents());
            errores.append(" - Numero de intentos del Login: " + user.getIntents());

            if(user.getIntents() >= maxIntents){
                String errorMaxIntent = String.format("El usuario %s ha sido inhabilitado por maximo numero de intentos", user.getUsername());
                log.error(errorMaxIntent);
                errores.append(" - " + errorMaxIntent);
                user.setStatus(false);
            }

            userService.update(user, user.getId());

            tracer.currentSpan().tag("error.message", errores.toString());

        }catch (FeignException fe){
            String errorUser = String.format("El usuario %s no esta registrado en el sistema.", authentication.getName());
            tracer.currentSpan().tag("error.message", errorUser);
            log.error(errorUser);
        }

    }
}
