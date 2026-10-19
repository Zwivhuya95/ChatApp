package com.mycompany.chatapp;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Message {

    private final String messageId;
    private final String recipient;
    private final String text;
    private final String hash;
    private boolean sent;

    public Message(String messageId, String recipient, String text) {

        if (messageId == null || messageId.length() > 10
                || messageId.isEmpty()) {
            throw new IllegalArgumentException(
                    "Message ID must be between 1 and 10 characters.");
        }

        if (recipient == null
                || !recipient.matches("\\+27\\d{9}")) {
            throw new IllegalArgumentException(
                    "Recipient must use South African format, e.g. +27821234567.");
        }

        if (text == null || text.trim().isEmpty()
                || text.length() > 250) {
            throw new IllegalArgumentException(
                    "Message must contain 1 to 250 characters.");
        }

        this.messageId = messageId;
        this.recipient = recipient;
        this.text = text;
        this.hash = generateHash(messageId + ":" + text);
        this.sent = false;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getText() {
        return text;
    }

    public String getHash() {
        return hash;
    }

    public boolean isSent() {
        return sent;
    }

    public String sendMessage(String action) {

        if (action == null) {
            return "Invalid action.";
        }

        switch (action.toLowerCase()) {
            case "send":
                sent = true;
                return "Message successfully sent.";

            case "store":
                return "Message successfully stored.";

            case "discard":
                return "Message discarded.";

            default:
                return "Invalid action.";
        }
    }

    private String generateHash(String value) {
        try {
            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hashBytes = digest.digest(
                    value.getBytes(StandardCharsets.UTF_8));

            StringBuilder result = new StringBuilder();

            for (byte b : hashBytes) {
                result.append(String.format("%02x", b & 0xff));
            }

            return result.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    "Unable to generate message hash.", e);
        }
    }
}