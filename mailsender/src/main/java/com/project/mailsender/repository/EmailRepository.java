package com.project.mailsender.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.mailsender.domain.Email;

public interface EmailRepository extends JpaRepository<Email, UUID> {
    
}