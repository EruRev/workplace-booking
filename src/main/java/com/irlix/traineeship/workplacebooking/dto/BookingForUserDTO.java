package com.irlix.traineeship.workplacebooking.dto;

import java.time.LocalDateTime;

public record BookingForUserDTO(
        String officeName,
        String workspaceName,
        short floorNumber,
        int workplaceNumber,
        LocalDateTime bookingStart,
        LocalDateTime bookingEnd,
        String equipment
) implements BookingDTO
{}
