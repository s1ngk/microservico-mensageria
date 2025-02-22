package com.ms.user.dtos;

import com.ms.user.configs.ValidPassword;
import jakarta.validation.constraints.NotBlank;

public record PasswordUpdateDto(@NotBlank String login, @NotBlank @ValidPassword String password) {
}
