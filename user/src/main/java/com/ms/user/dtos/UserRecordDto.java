package com.ms.user.dtos;

import com.ms.user.configs.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRecordDto(@NotBlank String login, @NotBlank @ValidPassword String password, @NotBlank String name, @NotBlank @Email String email) {
}