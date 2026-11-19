/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.poe;

import java.nio.file.Path;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author user
 */
public class MessageStorageTest {
    
    public MessageStorageTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of storeMessage method, of class MessageStorage.
     */
    @Test
    public void testStoreMessage() throws Exception {
        System.out.println("storeMessage");
        Message message = null;
        Path file = null;
        MessageStorage.storeMessage(message, file);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadMessages method, of class MessageStorage.
     */
    @Test
    public void testLoadMessages() throws Exception {
        System.out.println("loadMessages");
        Path file = null;
        List<Message> expResult = null;
        List<Message> result = MessageStorage.loadMessages(file);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
