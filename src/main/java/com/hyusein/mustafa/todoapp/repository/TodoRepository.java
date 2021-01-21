package com.hyusein.mustafa.todoapp.repository;

import com.hyusein.mustafa.todoapp.model.Todo;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<Todo, Long> {

}
