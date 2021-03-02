package com.hyusein.mustafa.todoapp.repository;

import com.hyusein.mustafa.todoapp.model.Comment;
import com.hyusein.mustafa.todoapp.model.Todo;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    Set<Comment> findByTodo(Todo todo);
}
