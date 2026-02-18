package monday.storage;

import monday.task.Deadline;
import monday.task.Event;
import monday.task.Task;
import monday.task.ToDo;
import monday.util.DateTimeParser;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Handles task parsing from storage format.
 */
public class TaskDeserializer {

    /**
     * Parses a task from a single line of the storage file.
     *
     * @param line The line to parse.
     * @return The parsed Task, or null if the line is invalid.
     */
    public Task parseTask(String line) {
        // Split by pipe delimiter with optional spaces
        String[] parts = line.split("\\s*\\|\\s*");

        // Minimum: type, status, description
        if (parts.length < 3) {
            return null;
        }

        String type = parts[0].trim();
        boolean isDone = parts[1].trim().equals("1");
        String description = parts[2].trim();

        // Validate description is not empty
        if (description.isEmpty()) {
            return null;
        }

        Task task;

        switch (type) {
        case "T":
            task = new ToDo(description);
            break;
        case "D":
            task = parseDeadline(parts);
            if (task == null) {
                return null;
            }
            break;
        case "E":
            task = parseEvent(parts);
            if (task == null) {
                return null;
            }
            break;
        default:
            // Unknown type, skip this line
            return null;
        }

        // Set the done status
        if (isDone) {
            task.markAsDone();
        }

        return task;
    }

    /**
     * Parses a deadline task from parts.
     *
     * @param parts The parts array.
     * @return The Deadline task, or null if invalid.
     */
    private Task parseDeadline(String[] parts) {
        if (parts.length < 4) {
            return null;
        }
        // Format: D | 0 | description | by: deadline
        String by = extractFieldValue(parts[3]);
        // Validate by field is not empty
        if (by.isEmpty()) {
            return null;
        }
        try {
            LocalDateTime byDateTime = LocalDateTime.parse(by, DateTimeParser.STORAGE_FORMATTER);
            return new Deadline(parts[2].trim(), byDateTime);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Parses an event task from parts.
     *
     * @param parts The parts array.
     * @return The Event task, or null if invalid.
     */
    private Task parseEvent(String[] parts) {
        if (parts.length < 5) {
            return null;
        }
        // Format: E | 0 | description | from: start | to: end
        String from = extractFieldValue(parts[3]);
        String to = extractFieldValue(parts[4]);
        // Validate from and to fields are not empty
        if (from.isEmpty() || to.isEmpty()) {
            return null;
        }
        try {
            LocalDateTime fromDateTime = LocalDateTime.parse(from, DateTimeParser.STORAGE_FORMATTER);
            LocalDateTime toDateTime = LocalDateTime.parse(to, DateTimeParser.STORAGE_FORMATTER);
            return new Event(parts[2].trim(), fromDateTime, toDateTime);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Extracts value from a field part (e.g., "by: Sunday" -> "Sunday").
     *
     * @param fieldPart The field part to extract from.
     * @return The extracted value.
     */
    private String extractFieldValue(String fieldPart) {
        String[] parts = fieldPart.split(":", 2);
        if (parts.length < 2) {
            return "";
        }
        return parts[1].trim();
    }
}
