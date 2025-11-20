package com.project.mailsender.dto;

import java.util.UUID;

public record EmailDto(
    UUID id,
    String subject,
    String body
) {}