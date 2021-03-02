package com.hyusein.mustafa.todoapp.repository;

import com.hyusein.mustafa.todoapp.model.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
