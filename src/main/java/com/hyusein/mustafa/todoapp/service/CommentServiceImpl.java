package com.hyusein.mustafa.todoapp.service;

import com.hyusein.mustafa.todoapp.command.CommentCommand;
import com.hyusein.mustafa.todoapp.model.Comment;
import com.hyusein.mustafa.todoapp.model.Todo;
import com.hyusein.mustafa.todoapp.model.User;
import com.hyusein.mustafa.todoapp.repository.CommentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final TodoService todoService;
    private final UserService userService;

    public CommentServiceImpl(CommentRepository commentRepository, TodoService todoService, UserService userService) {
        this.commentRepository = commentRepository;
        this.todoService = todoService;
        this.userService = userService;
    }

    @Override
    public Set<Comment> findAllByTodoId(Long id) {
        return this.commentRepository.findByTodo(todoService.findById(id));
    }

    @Override
    public Comment findById(Long id) {
        return this.commentRepository.findById(id).orElse(null);
    }

    @Override
    public Comment save(CommentCommand commentCommand) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Todo todo = todoService.findById(commentCommand.getTodoId());
        User user = userService.findByUsername(authentication.getName());

        if(user != null && todo != null) {
            if(commentCommand.getId() == null) {
                Comment comment = new Comment();
                comment.setComment(commentCommand.getComment());
                comment.setTodo(todo);
                comment.setUser(user);
                return commentRepository.save(comment);
            }else {
                Optional<Comment> comment = commentRepository.findById(commentCommand.getId());
                if(comment.isPresent()) {
                    if(comment.get().getUser().getUsername().equals(authentication.getName()) ||
                            authentication.getAuthorities().stream()
                                    .map(GrantedAuthority::getAuthority).anyMatch("EDIT_COMMENT"::equals)) {
                        comment.get().setComment(commentCommand.getComment());
                        return commentRepository.save(comment.get());
                    }
                }
            }
        }
        return null;
    }
}
