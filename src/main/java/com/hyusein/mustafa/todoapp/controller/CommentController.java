package com.hyusein.mustafa.todoapp.controller;

import com.hyusein.mustafa.todoapp.command.CommentCommand;
import com.hyusein.mustafa.todoapp.model.Comment;
import com.hyusein.mustafa.todoapp.service.CommentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping({"/{id}", "/{id}/"})
    public String getViewPage(@PathVariable("id") Long todoId, Model model) {
        model.addAttribute("comment_list", commentService.findAllByTodoId(todoId));
        model.addAttribute("todoId", todoId);

        return "comment/view";
    }

    @GetMapping({"/{id}/new", "/{id}/new/"})
    public String getNewCommentPage(@PathVariable("id") Long todoId, Model model){
        CommentCommand commentCommand = new CommentCommand();
        commentCommand.setTodoId(todoId);

        model.addAttribute("new_comment", commentCommand);

        return "comment/new";
    }

    @GetMapping({"/edit/{id}", "/edit/{id}/"})
    public String getEditCommentPage(@PathVariable("id") Long id, Model model){
        Comment comment = commentService.findById(id);
        if(comment != null) {
            CommentCommand commentCommand = new CommentCommand();
            commentCommand.setId(comment.getId());
            commentCommand.setTodoId(comment.getTodo().getId());
            commentCommand.setComment(comment.getComment());

            model.addAttribute("new_comment", commentCommand);

            return "comment/new";
        }
        return "redirect:/";//comment not found response or not authorized
    }

    @PostMapping({"/save"})
    public String saveNewComment(@Valid @ModelAttribute("new_comment") CommentCommand commentCommand,
                                 BindingResult result){
        if(result.hasErrors()){
            return "comment/new";
        }
        if(commentService.save(commentCommand) != null)
            return "redirect:/comments/" + commentCommand.getTodoId();
        else
            return "redirect:/";
    }
}
