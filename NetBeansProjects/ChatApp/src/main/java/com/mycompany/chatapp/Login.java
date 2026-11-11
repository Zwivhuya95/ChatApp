package com.mycompany.chatapp;

import java.util.regex.Pattern;

public class Login {
    private String username;
    private String password;
    private String cellphone;
    private String firstName;
    private String lastName;
    private boolean isLoggedIn = false;

    public boolean checkUserName(String username) {
        if (username == null) return false;
        // Contains an underscore (_) and is no more than 5 characters long
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        if (password == null) return false;
        // At least 8 characters long, contain a capital letter, a number, and a special character.
        boolean lengthOk = password.length() >= 8;
        boolean upperOk = false;
        boolean numOk = false;
        boolean specialOk = false;

        String specialChars = "!@#$%^&*()_+-=[]{};':\"|,.<>/?~`";

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) upperOk = true;
            else if (Character.isDigit(c)) numOk = true;
            else if (specialChars.indexOf(c) >= 0) specialOk = true;
        }

        return lengthOk && upperOk && numOk && specialOk;
    }

    public boolean checkCellPhoneNumber(String cellphone) {
        if (cellphone == null) return false;
        String regex = "^\\+[0-9]{1,3}[0-9]{9,10}$";
        return Pattern.matches(regex, cellphone);
    }

    public String registerUser(String username, String password, String cellphone, String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;

        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(cellphone)) {
            return "Cell phone number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }

        this.username = username;
        this.password = password;
        this.cellphone = cellphone;
        return "The two above conditions have been met, and the user has been registered successfully.";
    }

    public boolean loginUser(String enteredUsername, String enteredPassword) {
        if (this.username == null || this.password == null) return false;
        isLoggedIn = this.username.equals(enteredUsername) && this.password.equals(enteredPassword);
        return isLoggedIn;
    }

    public String returnLoginStatus(boolean success) {
        if (success) {
            return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }
}