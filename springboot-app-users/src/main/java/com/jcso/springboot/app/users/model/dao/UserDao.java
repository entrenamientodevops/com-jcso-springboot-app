package com.jcso.springboot.app.users.model.dao;

import com.jcso.springboot.api.users.commons.models.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "users")
public interface UserDao extends PagingAndSortingRepository<User, Long> {
    @RestResource(path = "findUsername")
    User findByUsername(@Param("username") String username);
}
