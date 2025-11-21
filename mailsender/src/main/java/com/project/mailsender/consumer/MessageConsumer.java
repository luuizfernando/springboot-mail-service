package com.project.mailsender.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.project.mailsender.domain.Message;
import com.project.mailsender.repository.MessageRepository;

@Component
public class MessageConsumer {

    @Autowired
    private MessageRepository repository;
    
    @RabbitListener(queues = "email-queue")
    public void listenEmailQueue(@Payload String msg) {
        Message message = new Message();
        message.setContent(msg);
        repository.save(message);
        System.out.println(msg);
    }

}