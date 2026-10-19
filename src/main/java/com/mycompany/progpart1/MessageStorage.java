/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.progpart1;

/**
 *
 * @author user
 */
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class MessageStorage {

    public static void storeMessage(Message message, Path file)
            throws IOException {

        String jsonMessage = "  {"
                + "\"messageID\":\"" + escape(message.getMessageID()) + "\","
                + "\"messageHash\":\"" + escape(message.getMessageHash()) + "\","
                + "\"recipient\":\"" + escape(message.getRecipient()) + "\","
                + "\"message\":\"" + escape(message.getMessage()) + "\","
                + "\"status\":\"" + escape(message.getStatus()) + "\""
                + "}";

        String existing = "";

        if (Files.exists(file)) {
            existing = Files.readString(file, StandardCharsets.UTF_8).trim();
        }

        String output;

        if (existing.isEmpty() || existing.equals("[]")) {
            output = "[\n" + jsonMessage + "\n]\n";
        } else if (existing.endsWith("]")) {

            String previousMessages =
                    existing.substring(0, existing.length() - 1).trim();

            if (previousMessages.equals("[")) {
                output = "[\n" + jsonMessage + "\n]\n";
            } else {
                output = previousMessages + ",\n"
                        + jsonMessage + "\n]\n";
            }

        } else {
            throw new IOException("Invalid JSON message file.");
        }

        Path parent = file.toAbsolutePath().getParent();

        if (parent != null) {
            Files.createDirectories(parent);
        }

        Files.writeString(
                file,
                output,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        );
    }

    // Escape special characters so the message remains valid JSON.
    private static String escape(String value) {
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
