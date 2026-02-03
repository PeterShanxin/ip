package monday;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Handles file storage operations for MONDAY's task list.
 * Provides methods to load tasks from disk and save tasks to disk.
 */
public class Storage {

    private static final String DATA_DIR_NAME = "data";
    private static final String FILE_NAME = "monday.txt";
    private static final Path DATA_DIR = Paths.get(DATA_DIR_NAME);
    private static final Path FILE_PATH = DATA_DIR.resolve(FILE_NAME);

    /**
     * Loads tasks from the storage file.
     * If the file does not exist, creates it and returns an empty list.
     *
     * @return The list of loaded tasks, or an empty list if file doesn't exist.
     * @throws MondayStorageException If an I/O error occurs during loading.
     */
    public static ArrayList<Task> loadTasks() throws MondayStorageException {
        try {
            // Create directory and file if they don't exist
            if (!Files.exists(DATA_DIR)) {
                Files.createDirectories(DATA_DIR);
            }
            if (!Files.exists(FILE_PATH)) {
                Files.createFile(FILE_PATH);
                return new ArrayList<>();
            }

            ArrayList<Task> tasks = new ArrayList<>();
            ArrayList<String> lines = new ArrayList<>(Files.readAllLines(FILE_PATH));

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i).trim();

                // Skip empty lines
                if (line.isEmpty()) {
                    continue;
                }

                try {
                    Task task = parseTask(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                } catch (Exception e) {
                    // Skip corrupted lines but warn the user (line number only for privacy)
                    System.err.println("Ugh. Skipping corrupted line " + (i + 1));
                }
            }

            return tasks;
        } catch (IOException e) {
            throw new MondayStorageException("Ugh. I can't access your data file. " + e.getMessage());
        }
    }

    /**
     * Parses a task from a single line of the storage file.
     *
     * @param line The line to parse.
     * @return The parsed Task, or null if the line is invalid.
     */
    private static Task parseTask(String line) {
        // Split by pipe delimiter with optional spaces
        String[] parts = line.split("\\s*\\|\\s*");

        // Minimum: type, status, description
        if (parts.length < 3) {
            return null;
        }

        String type = parts[0].trim();
        boolean isDone = parts[1].trim().equals("1");
        String description = parts[2].trim();

        Task task;

        switch (type) {
        case "T":
            task = new ToDo(description);
            break;
        case "D":
            if (parts.length < 4) {
                return null;
            }
            // Format: D | 0 | description | by: deadline
            String by = extractFieldValue(parts[3]);
            task = new Deadline(description, by);
            break;
        case "E":
            if (parts.length < 5) {
                return null;
            }
            // Format: E | 0 | description | from: start | to: end
            String from = extractFieldValue(parts[3]);
            String to = extractFieldValue(parts[4]);
            task = new Event(description, from, to);
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
     * Extracts the value from a field part (e.g., "by: Sunday" -> "Sunday").
     *
     * @param fieldPart The field part to extract from.
     * @return The extracted value.
     */
    private static String extractFieldValue(String fieldPart) {
        String[] parts = fieldPart.split(":", 2);
        if (parts.length < 2) {
            return "";
        }
        return parts[1].trim();
    }

    /**
     * Saves all tasks to the storage file.
     *
     * @param tasks The list of tasks to save.
     * @throws MondayStorageException If an I/O error occurs during saving.
     */
    public static void saveTasks(ArrayList<Task> tasks) throws MondayStorageException {
        try {
            // Ensure directory exists
            if (!Files.exists(DATA_DIR)) {
                Files.createDirectories(DATA_DIR);
            }

            // Delete existing file if present
            Files.deleteIfExists(FILE_PATH);

            // Create new file
            Files.createFile(FILE_PATH);

            // Encode and write all tasks
            ArrayList<String> lines = new ArrayList<>();
            for (Task task : tasks) {
                lines.add(encodeTask(task));
            }

            Files.write(FILE_PATH, lines);
        } catch (IOException e) {
            throw new MondayStorageException("Ugh. I couldn't save your tasks. " + e.getMessage());
        }
    }

    /**
     * Encodes a task into a string format for storage.
     *
     * @param task The task to encode.
     * @return The encoded string representation.
     */
    private static String encodeTask(Task task) {
        String type = task.getTypeIcon().replaceAll("[\\[\\]]", "");
        String done = task.isDone() ? "1" : "0";
        String desc = task.getDescription();

        if (task instanceof Deadline) {
            String by = ((Deadline) task).getBy();
            return String.format("%s | %s | %s | by: %s", type, done, desc, by);
        } else if (task instanceof Event) {
            String from = ((Event) task).getFrom();
            String to = ((Event) task).getTo();
            return String.format("%s | %s | %s | from: %s | to: %s", type, done, desc, from, to);
        } else {
            return String.format("%s | %s | %s", type, done, desc);
        }
    }
}
