package com.mycompany.chatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @Test
    public void testValidUsername() {
        Login login = new Login();

        // Username contains an underscore and is
        // no more than 5 characters long.
        assertTrue(login.checkUserName("ab_1"));
    }

    @Test
    public void testInvalidUsername() {
        Login login = new Login();

        // Username is too long.
        assertFalse(login.checkUserName("username"));
    }

    @Test
    public void testValidPassword() {
        Login login = new Login();

        // Example: uppercase, number, special character,
        // and at least 8 characters.
        assertTrue(login.checkPasswordComplexity("Passw0rd!"));
    }

    @Test
    public void testInvalidPassword() {
        Login login = new Login();

        // Password is too short and lacks complexity.
        assertFalse(login.checkPasswordComplexity("abc"));
    }
}