package com.hyusein.mustafa.todoapp.repository;

import com.hyusein.mustafa.todoapp.model.Authority;
import com.hyusein.mustafa.todoapp.model.AuthorityType;
import org.springframework.data.repository.CrudRepository;


public interface AuthorityRepository extends CrudRepository<Authority, Long> {
    Authority findByName(AuthorityType name);
}
