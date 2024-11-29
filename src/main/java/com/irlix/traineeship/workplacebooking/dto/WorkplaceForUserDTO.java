package com.irlix.traineeship.workplacebooking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record WorkplaceForUserDTO(
        @NotNull Integer number,
        @NotBlank String description,
        @NotNull @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) UUID workspaceId
) implements WorkplaceDTO
{}
