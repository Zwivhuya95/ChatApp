package com.mycompany.chatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @Test
    public void testUsernameCorrect() {
        Login login = new Login();
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void testUsernameIncorrect() {
        Login login = new Login();
        assertFalse(login.checkUserName("kyle!!!!!!"));
    }

    @Test
    public void testPasswordComplex() {
        Login login = new Login();
        assertTrue(login.checkPasswordComplexity("Ch&&rec#99!"));
    }

    @Test
    public void testPasswordSimple() {
        Login login = new Login();
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    public void testCellPhoneCorrect() {
        Login login = new Login();
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCellPhoneIncorrect() {
        Login login = new Login();
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    @Test
    public void testLoginSuccess() {
        Login login = new Login();
        login.registerUser("kyl_1", "Ch&&rec#99!", "+27838968976", "Kyle", "Test");
        assertTrue(login.loginUser("kyl_1", "Ch&&rec#99!"));
    }

    @Test
    public void testLoginFailure() {
        Login login = new Login();
        login.registerUser("kyl_1", "Ch&&rec#99!", "+27838968976", "Kyle", "Test");
        assertFalse(login.loginUser("kyl_1", "WrongPassword"));
    }
}