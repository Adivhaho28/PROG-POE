/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.progpart1;



import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    private Message message;

    @Before
    public void setUp() {
        // Your test data
        message = new Message("001", 0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
    }

    @Test
    public void testCheckMessageID() {
        System.out.println("checkMessageID");
        assertTrue(message.checkMessageID());
    }

    @Test
    public void testCheckRecipientCell() {
        System.out.println("checkRecipientCell");
        String expResult = "Cell phone number successfully captured.";
        String result = message.checkRecipientCell();
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckMessageLength() {
        System.out.println("checkMessageLength");
        String expResult = "Message ready to send.";
        Boolean result = message.checkMessageLength();
        assertEquals(expResult, result);
    }

    @Test
    public void testCreateMessageHash() {
        System.out.println("createMessageHash");
        String expResult = "00:0:HITONIGHT";
        String result = message.createMessageHash();
        assertEquals(expResult, result);
    }

    @Test
    public void testSentMessage() {
        System.out.println("SentMessage");
        assertEquals("Message successfully sent.", message.SentMessage("1"));
        assertEquals("Press 0 to delete the message.", message.SentMessage("2"));
        assertEquals("Message successfully stored.", message.SentMessage("3"));
    }

    @Test
    public void testPrintMessages() {
        System.out.println("printMessages");
        message.SentMessage("1");
        String result = message.printMessages();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testReturnTotalMessages() {
        System.out.println("returnTotalMessages");
        int before = Message.returnTotalMessages();
        message.SentMessage("1");
        int after = Message.returnTotalMessages();
        assertEquals(before + 1, after);
    }

    @Test
    public void testGetMessageID() {
        System.out.println("getMessageID");
        assertEquals("001", message.getMessageID());
    }

    @Test
    public void testGetMessageNumber() {
        System.out.println("getMessageNumber");
        assertEquals(0, message.getMessageNumber());
    }

    @Test
    public void testGetRecipient() {
        System.out.println("getRecipient");
        assertEquals("+27718693002", message.getRecipient());
    }

    @Test
    public void testGetMessage() {
        System.out.println("getMessage");
        assertEquals("Hi Mike, can you join us for dinner tonight?", message.getMessage());
    }

    @Test
    public void testGetMessageHash() {
        System.out.println("getMessageHash");
        // After creating hash, getter should return same
        message.createMessageHash();
        assertEquals("00:0:HITONIGHT", message.getMessageHash());
    }

    @Test
    public void testGetStatus() {
        System.out.println("getStatus");
        message.SentMessage("1");
        assertEquals("Message successfully sent.", message.getStatus());
    }
}