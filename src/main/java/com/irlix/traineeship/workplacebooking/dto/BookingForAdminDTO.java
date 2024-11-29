package com.irlix.traineeship.workplacebooking.dto;

import com.irlix.traineeship.workplacebooking.entities.enums.StatusEnum;

import java.time.LocalDateTime;

public record BookingForAdminDTO (
        String officeName,
        String workspaceName,
        short floorNumber,
        int workplaceNumber,
        LocalDateTime bookingStart,
        LocalDateTime bookingEnd,
        String equipment,
        StatusEnum status,
        String user
) implements BookingDTO
{}
