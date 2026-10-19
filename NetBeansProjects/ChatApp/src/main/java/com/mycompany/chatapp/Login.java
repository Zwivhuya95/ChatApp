package com.mycompany.chatapp;

import java.util.regex.Pattern;

/**
 * Handles user registration and login authentication.
 */
public class Login {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellPhoneNumber;

    // South African international number: +27 followed by 9 digits.
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^\\+27[0-9]{9}$");

    public Login() {
    }

    public Login(String firstName, String lastName) {
        this.firstName = firstName == null ? "" : firstName.trim();
        this.lastName = lastName == null ? "" : lastName.trim();
    }

    /**
     * Username must contain an underscore and be
     * no longer than five characters.
     */
    public boolean checkUserName(String username) {
        return username != null
                && username.length() <= 5
                && username.contains("_");
    }

    /**
     * Password must contain at least eight characters,
     * one uppercase letter, one digit and one special character.
     */
    public boolean checkPasswordComplexity(String password) {
        return password != null
                && password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[0-9].*")
                && password.matches(".*[^A-Za-z0-9].*");
    }

    /**
     * Validates an international South African cellphone number.
     */
    public boolean checkCellPhoneNumber(String number) {
        return number != null
                && PHONE_PATTERN.matcher(number).matches();
    }

    /**
     * Registers the user if all validation rules are satisfied.
     */
    public String registerUser(
            String username,
            String password,
            String cellPhoneNumber) {

        if (!checkUserName(username)) {
            return "Username is incorrectly formatted. "
                    + "It must contain an underscore and be "
                    + "no more than five characters long.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password is incorrectly formatted. "
                    + "Use at least eight characters, an uppercase "
                    + "letter, a number and a special character.";
        }

        if (!checkCellPhoneNumber(cellPhoneNumber)) {
            return "Cellphone number is incorrectly formatted. "
                    + "Use +27 followed by nine digits.";
        }

        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;

        return "Registration successful!";
    }

    /**
     * Checks whether the supplied login details match
     * the registered account.
     */
    public boolean loginUser(
            String enteredUsername,
            String enteredPassword) {

        return username != null
                && password != null
                && username.equals(enteredUsername)
                && password.equals(enteredPassword);
    }

    /**
     * Returns an appropriate login confirmation or error message.
     */
    public String returnLoginStatus(boolean loginSuccessful) {

        if (loginSuccessful) {
            String fullName =
                    (firstName + " " + lastName).trim();

            return "Welcome " + fullName
                    + ", it is great to see you again.";
        }

        return "Username or password incorrect. Please try again.";
    }

    public String getUsername() {
        return username;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }
}
