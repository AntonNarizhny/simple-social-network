package ru.wanderer.network.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.wanderer.network.domain.Comment;
import ru.wanderer.network.domain.User;
import ru.wanderer.network.domain.Views;
import ru.wanderer.network.dto.EventType;
import ru.wanderer.network.dto.ObjectType;
import ru.wanderer.network.repository.CommentRepository;
import ru.wanderer.network.util.WsSender;

import java.util.function.BiConsumer;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final BiConsumer<EventType, Comment> wsSender;

    @Autowired
    public CommentService(CommentRepository commentRepository, WsSender wsSender) {
        this.commentRepository = commentRepository;
        this.wsSender = wsSender.getSender(ObjectType.COMMENT, Views.FullComment.class);
    }

    public Comment create(Comment comment, User user) {
        comment.setAuthor(user);
        Comment commentFromDb = commentRepository.save(comment);

        wsSender.accept(EventType.CREATE, commentFromDb);

        return commentFromDb;
    }
}
