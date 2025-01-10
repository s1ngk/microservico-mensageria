package com.ms.user.dtos;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDto(@NotBlank String login, @NotBlank String password) {
}