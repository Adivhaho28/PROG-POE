/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe;

/**
 *
 * @author user
 */
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class MessageStorage {
    // STORE MESSAGE 

    public static void storeMessage(Message message, Path file)
            throws IOException {

        List<Message> messages = loadMessages(file);

        messages.add(message);

        StringBuilder json = new StringBuilder();

        json.append("[\n");

        for (int i = 0; i < messages.size(); i++) {

            Message m = messages.get(i);

            json.append("  {");
            json.append("\"messageID\":\"")
                    .append(escape(m.getMessageID())).append("\",");
            json.append("\"messageHash\":\"")
                    .append(escape(m.getMessageHash())).append("\",");
            json.append("\"recipient\":\"")
                    .append(escape(m.getRecipient())).append("\",");
            json.append("\"message\":\"")
                    .append(escape(m.getMessage())).append("\",");
            json.append("\"status\":\"")
                    .append(escape(m.getStatus())).append("\"");
            json.append("}");

            if (i < messages.size() - 1) {
                json.append(",");
            }

            json.append("\n");
        }

        json.append("]\n");

        Path parent = file.toAbsolutePath().getParent();

        if (parent != null) {
            Files.createDirectories(parent);
        }

        Files.writeString(
                file,
                json.toString(),
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        );
    }

    //  LOAD MESSAGES 

    public static List<Message> loadMessages(Path file)
            throws IOException {

        List<Message> result = new ArrayList<>();

        if (!Files.exists(file)) {
            return result;
        }

        String content = Files.readString(
                file,
                StandardCharsets.UTF_8
        ).trim();

        if (content.isEmpty() || content.equals("[]")) {
            return result;
        }

        // Validate JSON array boundaries
        if (!content.startsWith("[") || !content.endsWith("]")) {
            throw new IOException("Invalid JSON message file.");
        }

        content = content.substring(1, content.length() - 1).trim();

        if (content.isEmpty()) {
            return result;
        }

        List<String> objects = splitJsonObjects(content);

        for (String object : objects) {

            String messageID = extractValue(object, "messageID");
            String messageHash = extractValue(object, "messageHash");
            String recipient = extractValue(object, "recipient");
            String messageText = extractValue(object, "message");
            String status = extractValue(object, "status");

            if (messageID == null || messageHash == null
                    || recipient == null || messageText == null
                    || status == null) {

                throw new IOException("Invalid stored message data.");
            }

            int messageNumber = 0;

            try {
                String[] parts = messageHash.split(":");

                if (parts.length >= 2) {
                    messageNumber = Integer.parseInt(parts[1]);
                }

            } catch (NumberFormatException e) {
                messageNumber = result.size();
            }

            Message message = new Message(
                    messageID,
                    messageNumber,
                    recipient,
                    messageText
            );

            // Restore the original message status.
            switch (status) {
                case "SENT" -> message.SentMessage("1");
                case "DISCARDED" -> message.SentMessage("2");
                case "STORED" -> message.SentMessage("3");
                default -> throw new IOException("Invalid message status.");
            }

            result.add(message);
        }

        return result;
    }

    // PLIT JSON OBJECTS

    private static List<String> splitJsonObjects(String json)
            throws IOException {

        List<String> objects = new ArrayList<>();

        boolean insideString = false;
        boolean escaped = false;

        int depth = 0;
        int start = -1;

        for (int i = 0; i < json.length(); i++) {

            char c = json.charAt(i);

            if (escaped) {
                escaped = false;
                continue;
            }

            if (c == '\\' && insideString) {
                escaped = true;
                continue;
            }

            if (c == '"') {
                insideString = !insideString;
                continue;
            }

            if (!insideString) {

                if (c == '{') {

                    if (depth == 0) {
                        start = i;
                    }

                    depth++;

                } else if (c == '}') {

                    depth--;

                    if (depth < 0) {
                        throw new IOException("Invalid JSON structure.");
                    }

                    if (depth == 0 && start != -1) {

                        objects.add(json.substring(start, i + 1));

                        start = -1;
                    }
                }
            }
        }

        if (insideString || depth != 0 || start != -1) {
            throw new IOException("Incomplete JSON message data.");
        }

        return objects;
    }

    //EXTRACT JSON VALUES 

    private static String extractValue(String json, String key)
            throws IOException {

        String search = "\"" + key + "\"";

        int keyPosition = json.indexOf(search);

        if (keyPosition == -1) {
            return null;
        }

        int colonPosition = json.indexOf(":", keyPosition);

        if (colonPosition == -1) {
            throw new IOException("Invalid JSON field: " + key);
        }

        int start = colonPosition + 1;

        while (start < json.length()
                && Character.isWhitespace(json.charAt(start))) {
            start++;
        }

        if (start >= json.length() || json.charAt(start) != '"') {
            throw new IOException("Invalid JSON value: " + key);
        }

        start++;

        StringBuilder value = new StringBuilder();

        boolean escaped = false;

        for (int i = start; i < json.length(); i++) {

            char c = json.charAt(i);

            if (escaped) {

                switch (c) {

                    case '"' -> value.append('"');

                    case '\\' -> value.append('\\');

                    case 'n' -> value.append('\n');

                    case 'r' -> value.append('\r');

                    case 't' -> value.append('\t');

                    case 'b' -> value.append('\b');

                    case 'f' -> value.append('\f');

                    case 'u' -> {
                        if (i + 4 >= json.length()) {
                            throw new IOException("Invalid Unicode escape.");
                        }

                        try {
                            int code = Integer.parseInt(
                                    json.substring(i + 1, i + 5), 16
                            );

                            value.append((char) code);

                        } catch (NumberFormatException e) {
                            throw new IOException("Invalid Unicode escape.");
                        }

                        i += 4;
                    }

                    default -> throw new IOException("Invalid JSON escape.");
                }

                escaped = false;

            } else if (c == '\\') {

                escaped = true;

            } else if (c == '"') {

                return value.toString();

            } else {

                value.append(c);
            }
        }

        throw new IOException("Unterminated JSON value: " + key);
    }

    // ESCAPE SPECIAL CHARACTERS 

    private static String escape(String value) {

        if (value == null) {
            return "";
        }

        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t")
                .replace("\b", "\\b")
                .replace("\f", "\\f");
    }
}
