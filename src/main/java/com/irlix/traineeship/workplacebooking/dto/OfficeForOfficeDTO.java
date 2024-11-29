package com.irlix.traineeship.workplacebooking.dto;

import jakarta.validation.constraints.NotNull;

public record OfficeForOfficeDTO(
        @NotNull String officeAddress,
        @NotNull String officeName
) implements OfficeDTO
{}
