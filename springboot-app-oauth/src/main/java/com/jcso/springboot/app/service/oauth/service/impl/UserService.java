package com.jcso.springboot.app.service.oauth.service.impl;

import brave.Tracer;
import com.jcso.springboot.api.users.commons.models.entity.User;
import com.jcso.springboot.app.service.oauth.clients.UserFeignClient;
import com.jcso.springboot.app.service.oauth.service.IUserService;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("userService")
public class UserService implements IUserService, UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserFeignClient feignClient;

    @Autowired
    private Tracer tracer;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {


            User user = feignClient.findByUsername(username);


            List<GrantedAuthority> authorities = user.getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .peek(authority -> logger.info("Role: " + authority.getAuthority()))
                    .collect(Collectors.toList());

            logger.info("Usuario autenticado: " + username);

            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPassword(), user.getStatus(), true, true, true, authorities);
        } catch (FeignException e){
            String error = "Error en la conexión, no existe el usuario '" + username + "' registrado en el sistema";
            logger.error(error);
            tracer.currentSpan().tag("error.message", error + ": " + e.getMessage());
            throw new UsernameNotFoundException(error);
        }
    }

    @Override
    public User findByUsername(String username) {
        return feignClient.findByUsername(username);
    }

    @Override
    public User update(User user, Long id) {
        return feignClient.update(user, id);
    }
}
