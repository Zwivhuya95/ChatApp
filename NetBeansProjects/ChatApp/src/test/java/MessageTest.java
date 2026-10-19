package com.mycompany.chatapp;

import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    @Test
    void validMessageCanBeCreated() {
        Message message = new Message(
                "1234567890", "+27821234567", "Hello there!");

        assertEquals("Hello there!", message.getText());
        assertEquals("+27821234567", message.getRecipient());
    }

    @Test
    void invalidMessageIdIsRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                new Message("12345678901",
                        "+27821234567", "Hello"));
    }

    @Test
    void invalidRecipientIsRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                new Message("123", "0821234567", "Hello"));
    }

    @Test
    void messageLongerThan250CharactersIsRejected() {
        String longText = "A".repeat(251);

        assertThrows(IllegalArgumentException.class, () ->
                new Message("123", "+27821234567", longText));
    }

    @Test
    void sendingMessageChangesSentStatus() {
        Message message = new Message(
                "123", "+27821234567", "Hello");

        assertEquals("Message successfully sent.",
                message.sendMessage("send"));

        assertTrue(message.isSent());
    }

    @Test
    void messageHashIsGenerated() {
        Message message = new Message(
                "123", "+27821234567", "Hello");

        assertNotNull(message.getHash());
        assertEquals(64, message.getHash().length());
    }

    @Test
    void storingMessageWritesToFile() throws Exception {
        Path file = Files.createTempFile("chat-test", ".jsonl");

        try {
            MessageService service = new MessageService(file);

            service.createMessage(
                    "123", "+27821234567", "Hello", "store");

            assertEquals(1, service.returnTotalMessages());
            assertTrue(Files.size(file) > 0);
        } finally {
            Files.deleteIfExists(file);
        }
    }
}