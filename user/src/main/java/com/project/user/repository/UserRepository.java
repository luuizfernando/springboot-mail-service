package com.project.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.user.domain.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    
}