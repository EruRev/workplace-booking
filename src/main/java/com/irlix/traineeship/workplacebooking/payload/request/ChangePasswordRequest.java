package com.irlix.traineeship.workplacebooking.payload.request;

public record ChangePasswordRequest(
        String email,
        String oldPassword,
        String newPassword
) {
}
