package com.mycompany.chatapp;

import java.util.Scanner;

public class ChatApp {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("===== CHAT APPLICATION =====");

        System.out.print("Enter your first name: ");
        String firstName = input.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = input.nextLine();

        System.out.print("Create username: ");
        String username = input.nextLine();

        System.out.print("Create password: ");
        String password = input.nextLine();

        System.out.print("Enter cellphone (+27XXXXXXXXX): ");
        String cellphone = input.nextLine();

        boolean validUsername =
                username.length() <= 5 && username.contains("_");

        boolean validPassword =
                password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[0-9].*")
                && password.matches(".*[^A-Za-z0-9].*");

        boolean validCellphone =
                cellphone.matches("^\\+27[0-9]{9}$");

        if (!validUsername || !validPassword || !validCellphone) {
            System.out.println("Registration failed.");

            if (!validUsername) {
                System.out.println(
                        "Username must contain an underscore "
                        + "and be no longer than five characters.");
            }

            if (!validPassword) {
                System.out.println(
                        "Password must contain eight characters, "
                        + "an uppercase letter, a number and a special character.");
            }

            if (!validCellphone) {
                System.out.println(
                        "Cellphone must start with +27 followed by nine digits.");
            }

            input.close();
            return;
        }

        System.out.println("Registration successful!");

        System.out.println("\n===== LOGIN =====");

        System.out.print("Enter username: ");
        String loginUsername = input.nextLine();

        System.out.print("Enter password: ");
        String loginPassword = input.nextLine();

        if (username.equals(loginUsername)
                && password.equals(loginPassword)) {

            System.out.println("Welcome " + firstName + " " + lastName
                    + ", it is great to see you again.");

        } else {
            System.out.println(
                    "Username or password incorrect. Please try again.");
            input.close();
            return;
        }

        int totalMessages = 0;
        boolean running = true;

        while (running) {

            System.out.println("\n===== MENU =====");
            System.out.println("1. Send a message");
            System.out.println("2. Show total messages");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String choice = input.nextLine();

            switch (choice) {

                case "1":
                    System.out.print("Recipient cellphone (+27XXXXXXXXX): ");
                    String recipient = input.nextLine();

                    if (!recipient.matches("^\\+27[0-9]{9}$")) {
                        System.out.println("Invalid recipient cellphone.");
                        break;
                    }

                    System.out.print("Enter message (maximum 250 characters): ");
                    String message = input.nextLine();

                    if (message.trim().isEmpty() || message.length() > 250) {
                        System.out.println("Invalid message length.");
                        break;
                    }

                    System.out.println("1. Send");
                    System.out.println("2. Discard");
                    System.out.println("3. Store");
                    System.out.print("Choose an action: ");

                    String action = input.nextLine();

                    if (action.equals("1")) {
                        totalMessages++;
                        System.out.println("Message successfully sent.");
                    } else if (action.equals("2")) {
                        System.out.println("Message discarded.");
                    } else if (action.equals("3")) {
                        System.out.println("Message selected for storage.");
                    } else {
                        System.out.println("Invalid action.");
                    }
                    break;

                case "2":
                    System.out.println("Total messages sent: " + totalMessages);
                    break;

                case "0":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }

        System.out.println("Thank you for using the Chat App.");
        input.close();
    }
}
