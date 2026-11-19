/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.poe;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author user
 */
public class POETest {
    
     @BeforeClass
    public void setUp() {
        // Clear everything before each test
        POE.sentMessages.clear();
        POE.disregardedMessages.clear();
        POE.storedMessages.clear();
        POE.messageHashes.clear();
        POE.messageIDs.clear();
        POE.messages.clear();
        POE.messagesEntered = 0;

        // Test Data 1 - Sent
        POE.addToArrays(new Message("2783455798", 0, "+27834557986", "Did you get the cake?"), "SENT");
        // Test Data 2 - Stored - Longest
        POE.addToArrays(new Message("2783888456", 1, "+27838884567", "Where are you? You are late! I have asked you to be on time."), "STORED");
        // Test Data 3 - Disregard
        POE.addToArrays(new Message("2783448456", 2, "+27834484567", "Yohoooo, I am at your gate."), "DISCARDED");
        // Test Data 4 - Sent
        POE.addToArrays(new Message("0838888456", 3, "08388884567", "It is dinner time !"), "SENT");
        // Test Data 5 - Stored - Same recipient as #2
        POE.addToArrays(new Message("2783888457", 4, "+27838884567", "Ok, I am leaving without you."), "STORED");
    }

    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        assertEquals("Did you get the cake?", POE.sentMessages.get(0));
        assertEquals("It is dinner time !", POE.sentMessages.get(1));
    }

    @Test
    public void testDisplayLongestMessage() {
        assertEquals("Where are you? You are late! I have asked you to be on time.", POE.getLongestStoredMessage());
    }

    @Test
    public void testSearchForMessageID() {
        String result = POE.searchByMessageID("2783888456");
        assertTrue(result.contains("+27838884567"));
        assertTrue(result.contains("Where are you?"));
    }

    @Test
    public void testSearchForRecipient() {
        String result = POE.searchByRecipient("+27838884567");
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(result.contains("Ok, I am leaving without you."));
    }

    @Test
    public void testDeleteByHash() {
        String hash = POE.storedMessages.get(0).getMessageHash();
        String result = POE.deleteByHash(hash);
        assertTrue(result.contains("successfully deleted"));
        assertEquals(1, POE.storedMessages.size());
    }
}
