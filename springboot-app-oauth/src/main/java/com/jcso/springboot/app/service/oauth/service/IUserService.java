package com.jcso.springboot.app.service.oauth.service;

import com.jcso.springboot.api.users.commons.models.entity.User;

public interface IUserService {
    User findByUsername(String username);
    User update(User user, Long id);
}
