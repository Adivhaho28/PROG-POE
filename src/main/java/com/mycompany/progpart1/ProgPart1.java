/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.progpart1;

/**
 *
 * @author user
 */
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProgPart1 {
    
    static Scanner myInput = new Scanner(System.in);

    static Login login = new Login();
    
    static List<Message> messages  = new ArrayList<>();
    static int messagesEntered = 0;
    
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
            
            if (ok) {
                System.out.println("Welcome to QuickChat!");
                quickChatMenu();
            }
        }
        else 
        {
            System.out.println("Registration failed.");

            System.out.println("Please restart the registration process.\n");

            registrationProcess();
        }
    }
    
// Display the main QuickChat menu.
public static void quickChatMenu()
{
System.out.print("How many messages would you like to enter? ");
int messageLimit = readMessageLimit();
boolean running = true;

while (running)
{
System.out.println("\n=== QuickChat Menu ===");
System.out.println("1. Send Messages");
System.out.println("2. Show recently sent messages");
System.out.println("3. Quit");
System.out.print("Choose an option: ");

String option = myInput.nextLine().trim();

switch (option) {

                case "1" -> {
                    if (messagesEntered >= messageLimit) {
                        System.out.println(
                                "You have reached your message limit.");
                    } else {
                        createMessage();
                    }
        }

                case "2" -> printRecentlySentMessages();

                case "3" -> running = false;

                default -> System.out.println(
                            "Invalid option. Please choose 1, 2, or 3.");
            }
        }

        System.out.println("\nTotal messages sent: "
                + Message.returnTotalMessages());

        System.out.println("Thank you for using QuickChat.");
    }

    // Read and validate the number of messages.
    public static int readMessageLimit() {

        while (true) {

            String input = myInput.nextLine().trim();

            try {
                int number = Integer.parseInt(input);

                if (number >= 0) {
                    return number;
                }

            } catch (NumberFormatException e) {
                // An invalid number is handled below.
            }

            System.out.print(
                    "Enter a valid whole number of zero or more: ");
        }
    }

    // Create and process one message.
    public static void createMessage() {

        System.out.print(
                "Enter recipient cell number with international code: ");

        String recipient = myInput.nextLine().trim();

        // Validate the recipient before requesting the message.
        Message draft = new Message(
                messagesEntered, recipient, "");

        String recipientResult = draft.checkRecipientCell();

        if (!recipientResult.startsWith(
                "Cell phone number successfully")) {

            System.out.println(recipientResult);
            return;
        }

        System.out.print(
                "Enter your message (maximum 250 characters): ");

        String messageText = myInput.nextLine();

        if (messageText.length() > 250) {
            System.out.println(
                    "Please enter a message of less than 250 characters.");
            return;
        }

        Message message = new Message(
                messagesEntered, recipient, messageText);

        messagesEntered++;

        System.out.println("\nMessage details:");
        System.out.println(message.printMessages());

        System.out.println("\nChoose an action:");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message to send later");
        System.out.print("Enter your choice: ");

        String action = myInput.nextLine().trim();

        String result = message.SentMessage(action);
        System.out.println(result);

        if (message.getStatus().equals("SENT")) {

            messages.add(message);

            System.out.println("\nFull message details:");
            System.out.println(message.printMessages());

        } else if (message.getStatus().equals("DISCARDED")) {

            System.out.print("Press 0 to delete the message: ");

            String deleteChoice = myInput.nextLine().trim();

            if (deleteChoice.equals("0")) {
                System.out.println("Message deleted.");
            } else {
                System.out.println("Message disregarded.");
            }

        } else if (message.getStatus().equals("STORED")) {

            try {
                MessageStorage.storeMessage(
                        message, Path.of("messages.json"));

                messages.add(message);

                System.out.println(
                        "Message successfully stored in messages.json.");

            } catch (IOException e) {
                System.out.println(
                        "Error storing message: " + e.getMessage());
            }
        }
    }

    // Display messages sent successfully during this session.
    public static void printRecentlySentMessages() {

        boolean found = false;

        for (Message message : messages) {

            if (message.getStatus().equals("SENT")) {
                System.out.println("\n" + message.printMessages());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No messages have been sent yet.");
        }
    }
}
