package ru.wanderer.network.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.wanderer.network.domain.Message;
import ru.wanderer.network.domain.User;
import ru.wanderer.network.domain.Views;
import ru.wanderer.network.dto.MessagePageDto;
import ru.wanderer.network.service.MessageService;

import java.io.IOException;

@RestController
@RequestMapping("messages")
public class MessageController {
    public static final int MESSAGES_PER_PAGE = 3;

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    @JsonView(Views.FullMessage.class)
    public MessagePageDto getAllMessages(
            @PageableDefault(size = MESSAGES_PER_PAGE, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return messageService.findAll(pageable);
    }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message getMessageById(@PathVariable("id") Message message) {
        return message;
    }

    @PostMapping
    public Message create(@RequestBody Message message,
                          @AuthenticationPrincipal User user) throws IOException {
        return messageService.create(message, user);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Message update(@PathVariable("id") Message messageFromDb,
                          @RequestBody Message message) throws IOException {
        return messageService.update(messageFromDb, message);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Message message) {
        messageService.delete(message);
    }
}
