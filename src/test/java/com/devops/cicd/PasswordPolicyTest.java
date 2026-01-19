package com.devops.cicd;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordPolicyTest {

    @Test
    void shouldRejectNullOrShortPasswords() {
        assertFalse(PasswordPolicy.isStrong(null));
        assertFalse(PasswordPolicy.isStrong("Short1!"));
    }

    @Test
    void shouldRejectPasswordWithoutUppercase() {
        assertFalse(PasswordPolicy.isStrong("weak123!"));
    }

    @Test
    void shouldRejectPasswordWithoutLowercase() {
        assertFalse(PasswordPolicy.isStrong("WEAK123!"));
    }

    @Test
    void shouldRejectPasswordWithoutDigit() {
        assertFalse(PasswordPolicy.isStrong("WeakPass!"));
    }

    @Test
    void shouldRejectPasswordWithoutSpecialChar() {
        assertFalse(PasswordPolicy.isStrong("WeakPass123"));
    }

    @Test
    void shouldAcceptStrongPassword() {
        assertTrue(PasswordPolicy.isStrong("Strong1!"));
    }
}