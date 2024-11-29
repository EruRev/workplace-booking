package com.irlix.traineeship.workplacebooking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.irlix.traineeship.workplacebooking.entities.UserEntity;
import com.irlix.traineeship.workplacebooking.entities.WorkplaceEntity;
import com.irlix.traineeship.workplacebooking.entities.enums.EnumReport;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProblemReportForUserDTO (
    @NotNull LocalDateTime createdAt,
    @NotNull String description,
    @NotNull @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) UUID workplaceId,
    @NotNull @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) UUID userId,
    @NotNull EnumReport report
)
{}
