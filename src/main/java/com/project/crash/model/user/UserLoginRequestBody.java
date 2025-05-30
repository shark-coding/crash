package com.project.crash.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserLoginRequestBody(
        @NotEmpty String username,
        @NotEmpty String password
) {
}
