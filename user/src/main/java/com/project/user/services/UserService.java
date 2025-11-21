package com.project.user.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.user.domain.User;
import com.project.user.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository repository;
    
    private final RabbitTemplate template;

    public UserService(RabbitTemplate template) {
        this.template = template;
    }

    public void sendMessage(UUID userId, String payload) {
        repository.findById(userId).ifPresent(user -> {
            List<String> msgs = user.getMessages();
            if (msgs == null) msgs = new ArrayList<>();
            msgs.add(payload);
            user.setMessages(msgs);
            repository.save(user);
        });
        template.convertAndSend("", "email-queue", payload);
    }

    public User insert(User user) {
        return repository.save(user);
    }

    public List<String> findAllMessagesById(UUID id) {
        return repository.findById(id).map(User::getMessages).orElse(List.of());
    }

}