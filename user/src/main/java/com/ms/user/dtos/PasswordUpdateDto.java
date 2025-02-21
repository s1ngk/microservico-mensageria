package com.ms.user.dtos;

import jakarta.validation.constraints.NotBlank;

public record PasswordUpdateDto(@NotBlank String login, @NotBlank String password) {
}
