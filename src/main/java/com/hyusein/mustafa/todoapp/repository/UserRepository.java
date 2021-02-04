package com.hyusein.mustafa.todoapp.repository;

import com.hyusein.mustafa.todoapp.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User getUserByUsername(String username);
}