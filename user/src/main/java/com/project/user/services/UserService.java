package com.project.user.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private final RabbitTemplate template;

    public UserService(RabbitTemplate template) {
        this.template = template;
    }

    public void sendMessage(String payload) {
        template.convertAndSend("", "email-queue", payload);
    }

}