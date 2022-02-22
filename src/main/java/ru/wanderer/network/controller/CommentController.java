package ru.wanderer.network.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.wanderer.network.domain.Comment;
import ru.wanderer.network.domain.User;
import ru.wanderer.network.domain.Views;
import ru.wanderer.network.service.CommentService;

@RestController
@RequestMapping("comment")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @JsonView(Views.FullComment.class)
    public Comment create(@RequestBody Comment comment,
                          @AuthenticationPrincipal User user) {
        return commentService.create(comment, user);
    }
}
