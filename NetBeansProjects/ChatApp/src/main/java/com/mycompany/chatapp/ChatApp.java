package com.mycompany.chatapp;

import java.util.Scanner;

import java.util.ArrayList;
import java.util.Scanner;

public class ChatApp {
    private static ArrayList<String> sentMessages = new ArrayList<>();
    private static ArrayList<String> disregardedMessages = new ArrayList<>();
    private static ArrayList<String> storedMessages = new ArrayList<>();
    private static ArrayList<String> messageHash = new ArrayList<>();
    private static ArrayList<String> messageID = new ArrayList<>();

    public static void main(String[] args) {
        loadInitialTestData();
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        System.out.println("=== QuickChat Registration & Login ===");
        System.out.print("Enter username (must contain '_' and <= 5 chars): ");
        String u = scanner.nextLine();
        System.out.print("Enter password (>= 8 chars, 1 uppercase, 1 number, 1 special): ");
        String p = scanner.nextLine();
        System.out.print("Enter cell phone (+27...): ");
        String c = scanner.nextLine();
        System.out.print("Enter first name: ");
        String fn = scanner.nextLine();
        System.out.print("Enter last name: ");
        String ln = scanner.nextLine();

        String regMsg = login.registerUser(u, p, c, fn, ln);
        System.out.println(regMsg);

        if (!regMsg.contains("successfully")) {
            System.out.println("Registration failed. Exiting.");
            scanner.close();
            return;
        }

        System.out.println("\n--- Login ---");
        System.out.print("Username: ");
        String logU = scanner.nextLine();
        System.out.print("Password: ");
        String logP = scanner.nextLine();

        boolean success = login.loginUser(logU, logP);
        System.out.println(login.returnLoginStatus(success));

        if (!success) {
            scanner.close();
            return;
        }

        System.out.println("\nWelcome to QuickChat.");

        int choice;
        do {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Send Messages");
            System.out.println("2. Show recently sent messages (Coming Soon)");
            System.out.println("3. Quit");
            System.out.println("4. Stored Messages (Part 3)");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Enter a number 1-4.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    handleSendMessages(scanner);
                    break;
                case 2:
                    System.out.println("Coming Soon.");
                    break;
                case 3:
                    System.out.println("Exiting QuickChat. Goodbye!");
                    break;
                case 4:
                    handleStoredMessagesMenu(scanner);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 3);

        scanner.close();
    }

    public static void loadInitialTestData() {
        sentMessages.add("+27834557896: Did you get the cake?");
        storedMessages.add("Where are you? You are late! I have asked you to be on time.");
        messageHash.add("Test Message 2");
        messageID.add("+27838884567");
        disregardedMessages.add("+27834484557: Yohoooo, I am at your gate.");
        sentMessages.add("0838884567: It is dinner time!");
        storedMessages.add("Ok, I am leaving without you.");
        messageHash.add("Test Message 5");
        messageID.add("+27838884567");
    }

    public static void handleSendMessages(Scanner scanner) {
        System.out.print("How many messages would you like to enter? ");
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Please enter a valid number: ");
        }
        int num = scanner.nextInt();
        scanner.nextLine();

        for (int i = 1; i <= num; i++) {
            System.out.println("\n--- Message " + i + " ---");
            System.out.print("Enter recipient number: ");
            String recipient = scanner.nextLine();

            System.out.print("Enter message text (max 250 chars): ");
            String text = scanner.nextLine();

            Message msg = new Message(i, recipient, text);
            if (!msg.checkMessageLength(text)) {
                System.out.println("Message exceeds 250 characters by " + (text.length() - 250) + " characters; please reduce the size.");
                i--; 
                continue;
            } else {
                System.out.println("Message ready to send.");
            }

            System.out.println("Choose an action:");
            System.out.println("1) Send Message");
            System.out.println("2) Disregard Message");
            System.out.println("3) Store Message");
            System.out.print("Enter option (1-3): ");

            String action = scanner.nextLine().trim();
            if (action.equals("1") || action.equalsIgnoreCase("Send Message")) {
                sentMessages.add(recipient + ": " + text);
                System.out.println("Message successfully sent.");
                System.out.println("Message ID generated: " + msg.getMessageID());
                System.out.println("Message Hash: " + msg.getMessageHash());
            } else if (action.equals("2") || action.equalsIgnoreCase("Disregard Message")) {
                disregardedMessages.add(recipient + ": " + text);
                System.out.println("Press 0 to delete the message");
            } else if (action.equals("3") || action.equalsIgnoreCase("Store Message")) {
                storedMessages.add(text);
                messageHash.add("Test Message " + i);
                messageID.add(recipient);
                System.out.println("Message successfully stored");
            } else {
                System.out.println("Invalid option, message disregarded.");
            }
        }
        System.out.println("\nTotal messages sent/processed: " + num);
    }

    public static void handleStoredMessagesMenu(Scanner scanner) {
        int subChoice;
        do {
            System.out.println("\n--- STORED MESSAGES MENU (Part 3) ---");
            System.out.println("a. Display sender and recipient of all stored messages");
            System.out.println("b. Display the longest stored message");
            System.out.println("c. Search for a message ID and display corresponding recipient and message");
            System.out.println("d. Search for all messages stored for a particular recipient");
            System.out.println("e. Delete a message using the message hash");
            System.out.println("f. Display a report listing full details of all stored messages");
            System.out.println("g. Return to Main Menu");
            System.out.print("Enter your option (a-g): ");

            String input = scanner.nextLine().trim().toLowerCase();
            if (input.isEmpty()) continue;
            subChoice = input.charAt(0);

            switch (subChoice) {
                case 'a':
                    displaySenderAndRecipient();
                    break;
                case 'b':
                    displayLongestStoredMessage();
                    break;
                case 'c':
                    System.out.print("Enter Message ID or Recipient: ");
                    String id = scanner.nextLine();
                    searchByMessageID(id);
                    break;
                case 'd':
                    System.out.print("Enter recipient: ");
                    String rec = scanner.nextLine();
                    searchByRecipient(rec);
                    break;
                case 'e':
                    System.out.print("Enter Message Hash to delete: ");
                    String hash = scanner.nextLine();
                    deleteMessageByHash(hash);
                    break;
                case 'f':
                    displayMessageReport();
                    break;
                case 'g':
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        } while (true);
    }

    public static String displaySenderAndRecipient() {
        StringBuilder sb = new StringBuilder();
        System.out.println("\n--- Sender & Recipient of Stored Messages ---");
        for (int i = 0; i < storedMessages.size(); i++) {
            String info = "ID/Recipient: " + messageID.get(i) + " | Hash: " + messageHash.get(i);
            System.out.println(info);
            sb.append(info).append("\n");
        }
        return sb.toString();
    }

    public static String displayLongestStoredMessage() {
        if (storedMessages.isEmpty()) return "";
        String longest = storedMessages.get(0);
        for (String m : storedMessages) {
            if (m.length() > longest.length()) longest = m;
        }
        System.out.println("\nLongest stored message: " + longest);
        return longest;
    }

    public static String searchByMessageID(String id) {
        for (int i = 0; i < messageID.size(); i++) {
            if (messageID.get(i).equals(id)) {
                String res = "ID: " + messageID.get(i) + " | Message: " + storedMessages.get(i);
                System.out.println(res);
                return res;
            }
        }
        System.out.println("Not found.");
        return "Not Found";
    }

    public static String searchByRecipient(String recipient) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < messageID.size(); i++) {
            if (messageID.get(i).equals(recipient)) {
                System.out.println("- " + storedMessages.get(i));
                sb.append(storedMessages.get(i)).append("\n");
            }
        }
        return sb.toString().trim();
    }

    public static boolean deleteMessageByHash(String hash) {
        for (int i = 0; i < messageHash.size(); i++) {
            if (messageHash.get(i).equalsIgnoreCase(hash)) {
                System.out.println("Message: \"" + storedMessages.get(i) + "\" successfully deleted.");
                storedMessages.remove(i);
                messageID.remove(i);
                messageHash.remove(i);
                return true;
            }
        }
        System.out.println("Hash not found.");
        return false;
    }

    public static String displayMessageReport() {
        StringBuilder sb = new StringBuilder();
        System.out.println("\n=== STORED MESSAGE REPORT ===");
        for (int i = 0; i < storedMessages.size(); i++) {
            String line = "Hash: " + messageHash.get(i) + " | Recipient: " + messageID.get(i) + " | Message: " + storedMessages.get(i);
            System.out.println(line);
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    public static ArrayList<String> getStoredMessages() { return storedMessages; }
    public static ArrayList<String> getMessageID() { return messageID; }
    public static ArrayList<String> getMessageHash() { return messageHash; }
}
