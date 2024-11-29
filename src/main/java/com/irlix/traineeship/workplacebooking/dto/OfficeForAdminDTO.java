package com.irlix.traineeship.workplacebooking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OfficeForAdminDTO(
        @NotBlank String address,
        @NotBlank String name,
        @NotNull @JsonProperty(access = JsonProperty.Access.READ_ONLY) Boolean isDeleted
) implements OfficeDTO
{}
