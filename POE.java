/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.poe;

/**
 *
 * @author user
 */
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class POE {

     static Scanner myInput = new Scanner(System.in);
static Login login = new Login();
static List<Message> messages = new ArrayList<>();
static int messagesEntered = 0;
// PART 2 ARRAYS
static List<String> sentMessages = new ArrayList<>();
static List<String> disregardedMessages = new ArrayList<>();
static List<Message> storedMessages = new ArrayList<>();
static List<String> messageHashes = new ArrayList<>();
static List<String> messageIDs = new ArrayList<>();
public static void main(String[] args) {
registrationProcess();
}
// REGISTRATION AND LOGIN 
public static void registrationProcess() {
String username;
String password;
String phone;
System.out.println("=== Registration ===");
System.out.print("Enter username: ");
username = myInput.nextLine();
System.out.print("Enter password: ");
password = myInput.nextLine();
System.out.print("Enter international cell number (e.g. +2783...): ");
phone = myInput.nextLine();
String regMessage = login.registerUser(username, password, phone);
System.out.println(regMessage);
if (regMessage.equals("Password successfully captured.")) {
System.out.println("=== Login ===");
System.out.print("Enter username to login: ");
String loginUsername = myInput.nextLine();
System.out.print("Enter password to login: ");
String loginPassword = myInput.nextLine();
boolean ok = login.loginUser(loginUsername, loginPassword);
String statusMessage =
login.returnLoginStatus(ok, loginUsername);
System.out.println(statusMessage);
if (ok) {
System.out.println("Welcome to QuickChat!");
quickChatMenu();
} else {
System.out.println("Login unsuccessful. Please restart.");
}
} else {
System.out.println("Registration failed.");
System.out.println("Please try registering again.");
registrationProcess();
}
}
// Add messages to the correct arrays
public static void addToArrays(Message m, String status) {
messageIDs.add(m.getMessageID());
messageHashes.add(m.getMessageHash());
if (status.equals("SENT")) {
sentMessages.add(m.getMessage());
} else if (status.equals("DISCARDED")) {
disregardedMessages.add(m.getMessage());
} else if (status.equals("STORED")) {
storedMessages.add(m);
}
}
// MAIN MENU 
public static void quickChatMenu() {
System.out.print("How many messages would you like to enter? ");
int messageLimit = readMessageLimit();
boolean running = true;
while (running) {
System.out.println("=== QuickChat Menu ===");
System.out.println("1. Send Messages");
System.out.println("2. Show recently sent messages");
System.out.println("3. Quit");
System.out.println("4. Stored Messages");
System.out.print("Choose an option: ");
String option = myInput.nextLine().trim();
switch (option) {
case "1":
if (messagesEntered >= messageLimit) {
System.out.println("You have reached your message limit.");
} else {
createMessage();
}
break;
case "2":
printRecentlySentMessages();
break;
case "3":
running = false;
break;
case "4":
storedMessagesMenu();
break;
default:
System.out.println("Invalid option. Please choose 1, 2, 3 or 4.");
}
}
System.out.println("Total messages sent: " + Message.returnTotalMessages());
System.out.println("Thank you for using QuickChat.");
}
//  STORED MESSAGES MENU
public static void storedMessagesMenu() {
boolean inStored = true;
while (inStored) {
System.out.println("=== Stored Messages ===");
System.out.println("a. Display sender and recipient of stored messages");
System.out.println("b. Display the longest stored message");
System.out.println("c. Search for a message ID and display recipient and message");
System.out.println("d. Search for all messages stored for a particular recipient");
System.out.println("e. Delete a message using a message hash");
System.out.println("f. Display a report of stored messages");
System.out.println("g. Back to Main Menu");
System.out.print("Choose an option: ");
String choice = myInput.nextLine().trim().toLowerCase();
switch (choice) {
case "a":
displaySenderAndRecipient();
break;
case "b":
displayLongestStoredMessage();
break;
case "c":
System.out.print("Enter Message ID to search: ");
searchByMessageID(myInput.nextLine().trim());
break;
case "d":
System.out.print("Enter Recipient to search: ");
searchByRecipient(myInput.nextLine().trim());
break;
case "e":
System.out.print("Enter Message Hash to delete: ");
deleteByHash(myInput.nextLine().trim());
break;
case "f":
displayReport();
break;
case "g":
inStored = false;
break;
default:
System.out.println("Invalid option.");
}
}
}
// ================= OPTION A =================
public static void displaySenderAndRecipient() {
if (storedMessages.isEmpty()) {
System.out.println("No stored messages.");
return;
}
for (Message m : storedMessages) {
System.out.println( "Recipient: " + m.getRecipient() + "Message: " + m.getMessage());
}
}
// ================= OPTION B =================
public static String getLongestStoredMessage() {
String longest = "";
for (Message m : storedMessages) {
if (m.getMessage().length() > longest.length()) {
longest = m.getMessage();
}
}
return longest;
}
public static void displayLongestStoredMessage() {
String longest = getLongestStoredMessage();
if (longest.isEmpty()) {
System.out.println("No stored messages.");
} else {
System.out.println("Longest stored message: " + longest);
}
}
// ================= OPTION C =================
public static String searchByMessageID(String id) {
for (Message m : storedMessages) {
if (m.getMessageID().equals(id)) {
String result =
"Recipient: " + m.getRecipient() + "Message: " + m.getMessage();
System.out.println(result);
return result;
}
}
System.out.println("Message ID not found.");
return "Message ID not found.";
}
// ================= OPTION D =================
public static String searchByRecipient(String recipient) {
StringBuilder sb = new StringBuilder();
for (Message m : storedMessages) {
if (m.getRecipient().equals(recipient))
{
sb.append(m.getMessage()).append("");
}
}
if (sb.length() == 0) {
System.out.println("No stored messages for this recipient.");
return "No stored messages for this recipient.";
}
System.out.println(sb.toString());
return sb.toString();
}
// ================= OPTION E =================
public static String deleteByHash(String hash) {
for (int i = 0; i < storedMessages.size(); i++) {
Message m = storedMessages.get(i);
if (m.getMessageHash().equals(hash)) {
String deleted = m.getMessage();
String deletedID = m.getMessageID();
storedMessages.remove(i);
messageHashes.remove(hash);
messageIDs.remove(deletedID);
String result =
"Message: " + deleted
+ "successfully deleted.";
System.out.println(result);
return result;
}
}
System.out.println("Hash not found.");
return "Hash not found.";
}
// ================= OPTION F =================
public static void displayReport() {
if (storedMessages.isEmpty()) {
System.out.println("No stored messages to report.");
return;
}
System.out.println(
"=== REPORT - STORED MESSAGES ===");
for (Message m : storedMessages) {
System.out.println("Message ID: " + m.getMessageID());
System.out.println("Message Hash: " + m.getMessageHash());
System.out.println("Recipient: " + m.getRecipient());
System.out.println("Message: " + m.getMessage());
System.out.println("-----------------------------");
}
}
// ================= MESSAGE LIMIT =================
public static int readMessageLimit() {
while (true) {
String input = myInput.nextLine().trim();
try {
int number = Integer.parseInt(input);
if (number >= 0) {
return number;
}
} catch (NumberFormatException e) {
// Invalid number entered
}
System.out.print(
"Enter a valid whole number of zero or more: "
);
}
}
// CREATE MESSAGE
public static void createMessage() {
System.out.print("Enter recipient cell number with international code: ");
String recipient = myInput.nextLine().trim();
Message draft = new Message(messagesEntered, recipient, "");
String recipientResult = draft.checkRecipientCell();
if (!recipientResult.startsWith("Cell phone number successfully")) {
System.out.println(recipientResult);
return;
}
System.out.print("Enter your message (maximum 250 characters): ");
String messageText = myInput.nextLine();
if (messageText.length() > 250) {
System.out.println(
"Please enter a message of less than 250 characters."
);
return;
}
Message message =
new Message(messagesEntered, recipient, messageText);
messagesEntered++;
System.out.println("Message details:");
System.out.println(message.printMessages());
System.out.println("Choose an action:");
System.out.println("1. Send Message");
System.out.println("2. Disregard Message");
System.out.println("3. Store Message to send later");
System.out.print("Enter your choice: ");
String action = myInput.nextLine().trim();
String result = message.SentMessage(action);
System.out.println(result);
if (message.getStatus().equals("SENT")) {
messages.add(message);
addToArrays(message, "SENT");
System.out.println(
"Full message details:");
System.out.println(message.printMessages());
} else if (message.getStatus().equals("DISCARDED")) {
addToArrays(message, "DISCARDED");
System.out.print("Press 0 to delete the message: ");
String deleteChoice = myInput.nextLine().trim();
if (deleteChoice.equals("0")) {
messageHashes.remove(message.getMessageHash());
messageIDs.remove(message.getMessageID());
disregardedMessages.remove(message.getMessage());
System.out.println("Message deleted.");
} else {
System.out.println("Message disregarded.");
}
} else if (message.getStatus().equals("STORED")) {
try {
MessageStorage.storeMessage(
message,
Path.of("messages.json")
);
messages.add(message);
addToArrays(message, "STORED");
System.out.println(
"Message successfully stored in messages.json."
);
} catch (IOException e) {
System.out.println(
"Error storing message: " + e.getMessage()
);
}
}
}
// ================= RECENTLY SENT MESSAGES =================
public static void printRecentlySentMessages() {
boolean found = false;
for (Message message : messages) {
if (message.getStatus().equals("SENT")) {
System.out.println("" + message.printMessages());
found = true;
}
}
if (!found) {
System.out.println("No messages have been sent yet.");
}
}
}

