package com.ms.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailUpdateDto(@NotBlank String login, @NotBlank @Email String email) {
}
