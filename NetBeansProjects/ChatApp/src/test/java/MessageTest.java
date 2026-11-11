package com.mycompany.chatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageLengthValid() {
        Message msg = new Message(1, "+27718691002", "Hi Mike, can you join us for dinner tonight?");
        assertTrue(msg.checkMessageLength(msg.getMessageText()));
    }

    @Test
    public void testMessageLengthInvalid() {
        Message msg = new Message(1, "+27718691002", "A".repeat(251));
        assertFalse(msg.checkMessageLength(msg.getMessageText()));
    }

    @Test
    public void testRecipientValid() {
        Message msg = new Message(1, "+27718691002", "Hello");
        assertTrue(msg.checkRecipientCell(msg.getRecipient()));
    }

    @Test
    public void testRecipientInvalid() {
        Message msg = new Message(1, "08575925889", "Hello");
        assertFalse(msg.checkRecipientCell(msg.getRecipient()));
    }

    @Test
    public void testMessageHashGeneration() {
        Message msg = new Message(1, "+27718691002", "Hi Mike, can you join us for dinner tonight?");
        String hash = msg.getMessageHash();
        assertNotNull(hash);
        assertTrue(hash.contains(":1:"));
    }

    @Test
    public void testMessageIDGenerated() {
        Message msg = new Message(1, "+27718691002", "Hello");
        assertNotNull(msg.getMessageID());
        assertTrue(msg.checkMessageID(msg.getMessageID()));
    }
}
 One attachment
  •  Scanned by Gmail
