/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.progpart1;

/**
 *
 * @author user
 */

import java.security.SecureRandom;
import java.util.Locale;
import java.util.regex.Pattern;

public class Message {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static int totalMessagesSent = 0;

    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String message;
    private final String messageHash;
    private String status = "PENDING";

    // Constructor: creates a new message.
    public Message(int messageNumber, String recipient, String message) {
        this(generateMessageID(), messageNumber, recipient, message);
    }

    // Constructor also allows a specific ID for unit testing.
    public Message(String messageID, int messageNumber,
                   String recipient, String message) {

        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient == null ? "" : recipient.trim();
        this.message = message == null ? "" : message.trim();
        this.messageHash = createMessageHash();
    }

    // Generate a random ten-digit message ID.
    private static String generateMessageID() {
        long id = 1_000_000_000L
                + RANDOM.nextLong(9_000_000_000L);

        return String.valueOf(id);
    }

    // Check that the message ID contains exactly ten digits.
    public boolean checkMessageID() {
        return messageID != null
                && messageID.matches("\\d{10}");
    }

    // Validate the international recipient number.
    public String checkRecipientCell() {

        Pattern internationalNumber =
                Pattern.compile("^\\+[1-9]\\d{7,14}$");

        if (internationalNumber.matcher(recipient).matches()) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted "
                + "or does not contain an international code. "
                + "Please correct the number and try again.";
    }

    // Check the message length.
    public boolean checkMessageLength() {
        return message.length() <= 250;
    }

    // Create the message hash using the first two ID digits,
    // message number, and first and last words.
    public String createMessageHash() {

        String firstTwoDigits = "00";

        if (messageID != null && messageID.length() >= 2) {
            firstTwoDigits = messageID.substring(0, 2);
        }

        String[] words = message.trim().isEmpty()
                ? new String[0]
                : message.trim().split("\\s+");

        String firstWord = "";
        String lastWord = "";

        if (words.length > 0) {
            firstWord = words[0].replaceAll("[^A-Za-z0-9]", "");
            lastWord = words[words.length - 1]
                    .replaceAll("[^A-Za-z0-9]", "");
        }

        return (firstTwoDigits + ":"
                + messageNumber + ":"
                + firstWord + lastWord).toUpperCase(Locale.ROOT);
    }

    // Send, discard, or store the message.
    public String SentMessage(String choice) {

        if (choice == null) {
            return "Invalid option.";
        }

        switch (choice.trim()) {

            case "1":
            case "Send":
            case "send":

                if (!checkMessageLength()) {
                    return "Please enter a message of less than 250 characters.";
                }

                if (!checkRecipientCell().startsWith(
                        "Cell phone number successfully")) {
                    return checkRecipientCell();
                }

                status = "SENT";
                totalMessagesSent++;

                return "Message successfully sent.";

            case "2":
            case "Discard":
            case "discard":

                status = "DISCARDED";
                return "Press 0 to delete the message.";

            case "3":
            case "Store":
            case "store":

                status = "STORED";
                return "Message successfully stored.";

            default:
                return "Invalid option. Choose 1, 2, or 3.";
        }
    }

    // Display all details of a message.
    public String printMessages() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + message;
    }

    // Return the total number of successfully sent messages.
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    public String getMessageID() {
        return messageID;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getStatus() {
        return status;
    }

}
