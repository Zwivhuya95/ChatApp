package com.mycompany.chatapp;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Message {
    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;
    private int messageNumber;

    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    public boolean checkMessageID(String id) {
        return id != null && id.length() <= 10;
    }

    public boolean checkRecipientCell(String cell) {
        if (cell == null) return false;
        return cell.startsWith("+") && cell.length() <= 12;
    }

    public String generateMessageID() {
        long idNum = (long)(Math.random() * 9000000000L) + 1000000000L;
        return String.valueOf(idNum);
    }

    public String createMessageHash() {
        if (messageID == null || messageID.length() < 2) return "";
        String prefix = messageID.substring(0, 2);
        
        String trimmed = messageText.trim();
        String firstWord = "";
        String lastWord = "";
        if (!trimmed.isEmpty()) {
            String[] words = trimmed.split("\\s+");
            firstWord = words[0].replaceAll("[^a-zA-Z]", "").toUpperCase();
            if (words.length > 1) {
                lastWord = words[words.length - 1].replaceAll("[^a-zA-Z]", "").toUpperCase();
            } else {
                lastWord = firstWord;
            }
        }
        return prefix + ":" + messageNumber + ":" + firstWord + " " + lastWord;
    }

    public boolean checkMessageLength(String text) {
        return text != null && text.length() <= 250;
    }

    public String getMessageID() { return messageID; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    public int getMessageNumber() { return messageNumber; }
}

