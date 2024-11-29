package com.irlix.traineeship.workplacebooking.payload.request;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChangePasswordRequestTest {

    @Test
    public void testToString() {
        String email = "test@example.com";
        String oldPassword = "oldPassword123";
        String newPassword = "newPassword456";

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(email, oldPassword, newPassword);

        String toStringOutput = changePasswordRequest.toString();

        assertTrue(toStringOutput.contains("test@example.com"));
        assertTrue(toStringOutput.contains("oldPassword123"));
        assertTrue(toStringOutput.contains("newPassword456"));
    }
}
