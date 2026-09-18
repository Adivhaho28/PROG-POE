/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.progpart1;

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
public class LoginTest {
    
    public LoginTest() {
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
     * Test of checkUserName method, of class Login.
     */
   // Test valid username

    // Username correctly formatted

    @Test
    public void testCheckUserNameCorrect() {

        String username = "kyl_1";
        boolean expResult = true;
        boolean result = Login.checkUserName(username);
        assertEquals(expResult, result);
    }

    // Username incorrectly formatted

    @Test
    public void testCheckUserNameIncorrect() {
        String username = "kyle!!!!!";
        boolean expResult = false;
        boolean result = Login.checkUserName(username);
        assertEquals(expResult, result);
    }

    // Password correctly formatted

    @Test
    public void testCheckPasswordComplexityCorrect() {
        String password = "Ch&8$sec@ke99!";
        boolean expResult = true;
        boolean result = Login.checkPasswordComplexity(password);
        assertEquals(expResult, result);
    }

    // Password incorrectly formatted

    @Test
    public void testCheckPasswordComplexityIncorrect() {
        String password = "password";
        boolean expResult = false;
        boolean result = Login.checkPasswordComplexity(password);
        assertEquals(expResult, result);
    }

    // Cell phone correctly formatted

    @Test
    public void testCheckCellPhoneNumberCorrect() {
        String cellPhoneNumber = "+27838968976";
        boolean expResult = true;
        boolean result = Login.checkCellPhoneNumber(cellPhoneNumber);
        assertEquals(expResult, result);
    }

    // Cell phone incorrectly formatted

    @Test
    public void testCheckCellPhoneNumberIncorrect() {
        String cellPhoneNumber = "08966553";
        boolean expResult = false;
        boolean result = Login.checkCellPhoneNumber(cellPhoneNumber);
        assertEquals(expResult, result);
    }

    // Login successful

    @Test
    public void testLoginSuccessful() {
        Login instance = new Login();
        instance.registerUser(
                "kyl_1",
                "Ch&8$sec@ke99!",
                "+27838968976"
        );
        boolean expResult = true;
        boolean result = instance.loginUser(
                "kyl_1",
                "Ch&8$sec@ke99!"
        );
        assertEquals(expResult, result);
    }

    // Login failed

    @Test
    public void testLoginFailed() {
        Login instance = new Login();
        instance.registerUser(
                "kyl_1",
                "Ch&8$sec@ke99!",
                "+27838968976"
        );
        boolean expResult = false;
        boolean result = instance.loginUser(
                "wrong",
                "wrong"
        );
        assertEquals(expResult, result);
    }
}
