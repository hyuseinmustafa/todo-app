package com.hyusein.mustafa.todoapp.service;

import com.hyusein.mustafa.todoapp.command.CommentCommand;
import com.hyusein.mustafa.todoapp.model.Comment;

import java.util.Set;

public interface CommentService {
    Set<Comment> findAllByTodoId(Long id);
    Comment add(CommentCommand commentCommand);
}
