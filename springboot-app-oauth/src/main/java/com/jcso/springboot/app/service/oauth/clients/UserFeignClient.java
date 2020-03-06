package com.jcso.springboot.app.service.oauth.clients;

import com.jcso.springboot.api.users.commons.models.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "users-service")
public interface UserFeignClient {

    @GetMapping("/users/search/findUsername")
    User findByUsername(@RequestParam("username") String username);

    @PutMapping("/users/{id}")
    User update(@RequestBody User user, @PathVariable Long id);
}
