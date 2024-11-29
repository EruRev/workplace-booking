package com.irlix.traineeship.workplacebooking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserForAdminDTO(
        @NotBlank String fullName,
        @NotNull String phoneNumber,
        @NotNull @Email String email,
        @NotNull @JsonProperty(access = JsonProperty.Access.READ_ONLY) Boolean isDeleted
) implements UserDTO
{}