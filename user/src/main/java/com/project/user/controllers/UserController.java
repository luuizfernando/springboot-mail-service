package com.project.user.controllers;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.user.domain.User;
import com.project.user.services.UserService;


@RestController
public class UserController {
    
    @Autowired
    private UserService service;

    @PostMapping("/send")
    public void send(@RequestBody Map<String, String> body) {
        UUID userId = UUID.fromString(body.get("id"));
        String msg = body.get("msg");
        service.sendMessage(userId, msg);
    }

    @GetMapping("/messages/{id}")
    public List<String> find(@PathVariable UUID id) {
        return service.findAllMessagesById(id);
    }

    @PostMapping("/users")
	public ResponseEntity<User> insert(@RequestBody User obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}

}