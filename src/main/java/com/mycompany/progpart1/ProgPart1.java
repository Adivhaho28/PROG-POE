/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.progpart1;

/**
 *
 * @author user
 */
import java.util.Scanner;

public class ProgPart1 {
    
    static Scanner myInput = new Scanner(System.in);

    static Login login = new Login();
    
    public static void main(String[] args) {
        
        registrationProcess();
        myInput.close();
    }
    public static void registrationProcess() {
        
        // Declarations
        String username, password, phone;
        String regMessage, loginUsername, loginPassword, statusMessage;
        
        System.out.println("=== Registration ===");
        
        System.out.print("Enter username: ");
        username = myInput.nextLine();

        System.out.print("Enter password: ");
        password = myInput.nextLine();

        System.out.print("Enter international cell number (e.g. +2783...): ");
        phone = myInput.nextLine();

        // Register user
        regMessage = login.registerUser(username, password, phone);
        System.out.println(regMessage);

        // Decision: Was registration successful?
        if (regMessage.equals("Password successfully captured.")) {
            
            System.out.println("\n=== Login ===");
           
            System.out.print("Enter username to login: ");
            loginUsername = myInput.nextLine();

            System.out.print("Enter password to login: ");
            loginPassword = myInput.nextLine();

            boolean ok = login.loginUser(loginUsername, loginPassword);

            statusMessage = login.returnLoginStatus(ok, loginUsername);

            System.out.println(statusMessage);
        }
        else 
        {
            System.out.println("Registration failed.");

            System.out.println("Please restart the registration process.\n");

            registrationProcess();
        }
    }
}
