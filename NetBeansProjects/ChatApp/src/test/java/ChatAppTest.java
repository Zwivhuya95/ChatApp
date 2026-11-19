package com.mycompany.chatapp;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ChatAppTest {
    @BeforeEach
    public void setUp() {
        ChatApp.getStoredMessages().clear();
        ChatApp.getMessageID().clear();
        ChatApp.getMessageHash().clear();
        ChatApp.loadInitialTestData();
    }

    @Test
    public void testStoredMessagesPopulated() {
        assertFalse(ChatApp.getStoredMessages().isEmpty());
    }

    @Test
    public void testLongestStoredMessage() {
        String longest = ChatApp.displayLongestStoredMessage();
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest);
    }

    @Test
    public void testSearchByMessageID() {
        String res = ChatApp.searchByMessageID("+27838884567");
        assertTrue(res.contains("Where are you?"));
    }

    @Test
    public void testDeleteMessageByHash() {
        boolean deleted = ChatApp.deleteMessageByHash("Test Message 2");
        assertTrue(deleted);
    }

    @Test
    public void testDisplayReport() {
        String report = ChatApp.displayMessageReport();
        assertTrue(report.contains("Test Message 2"));
    }
}