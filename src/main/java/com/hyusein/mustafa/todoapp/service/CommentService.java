package com.hyusein.mustafa.todoapp.service;

import com.hyusein.mustafa.todoapp.command.CommentCommand;
import com.hyusein.mustafa.todoapp.model.Comment;

import java.util.Set;

public interface CommentService {
    Comment findById(Long id);
    Set<Comment> findAllByTodoId(Long id);
    Comment save(CommentCommand commentCommand);
}
