package com.hyusein.mustafa.todoapp.repository;

import com.hyusein.mustafa.todoapp.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);
}
