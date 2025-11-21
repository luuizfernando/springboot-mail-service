package com.project.mailsender.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.mailsender.domain.Message;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    
}