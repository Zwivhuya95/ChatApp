package com.mycompany.chatapp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages messages created by the application.
 */
public class MessageService {

    private final List<Message> messages = new ArrayList<>();
    private final Path storageFile;

    public MessageService() {
        this(Path.of("messages.jsonl"));
    }

    public MessageService(Path storageFile) {
        this.storageFile = storageFile;
    }

    /**
     * Creates a message and performs the selected action.
     */
    public String createMessage(
            String id,
            String recipient,
            String text,
            String option) {

        Message message = new Message(id, recipient, text);

        if (option == null) {
            return "Invalid option.";
        }

        String action = option.trim().toLowerCase();

        if (!action.equals("send")
                && !action.equals("store")
                && !action.equals("discard")) {
            return "Invalid option. Choose send, store or discard.";
        }

        String status = message.sendMessage(action);

        if (action.equals("send")) {
            messages.add(message);
        } else if (action.equals("store")) {
            saveMessage(message);
            messages.add(message);
        }

        return status + "\n" + message;
    }

    /**
     * Appends stored messages to the local file.
     */
    private void saveMessage(Message message) {

        String line = "{\"messageId\":\""
                + escape(message.getMessageId())
                + "\",\"hash\":\""
                + message.getHash()
                + "\",\"recipient\":\""
                + escape(message.getRecipient())
                + "\",\"message\":\""
                + escape(message.getText())
                + "\"}";

        try {
            Files.writeString(
                    storageFile,
                    line + System.lineSeparator(),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);

        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Unable to save message.", exception);
        }
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }

    public List<Message> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    public int returnTotalMessages() {
        return messages.size();
    }

    /**
     * Displays all messages held during this session.
     */
    public String printMessages() {

        if (messages.isEmpty()) {
            return "No messages available.";
        }

        StringBuilder output = new StringBuilder();

        for (Message message : messages) {
            output.append(message)
                    .append(System.lineSeparator())
                    .append("--------------------")
                    .append(System.lineSeparator());
        }

        return output.toString();
    }
}
