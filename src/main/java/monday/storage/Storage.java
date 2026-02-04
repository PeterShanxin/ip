package monday.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import monday.task.Task;
import monday.task.ToDo;
import monday.task.Deadline;
import monday.task.Event;
import monday.task.LoadResult;
import monday.util.DateTimeParser;
import monday.exception.MondayStorageException;

/**
 * Handles file storage operations for MONDAY's task list.
 * Provides methods to load tasks from disk and save tasks to disk.
 */
public class Storage {

    private static final String CORRUPTED_LINE_MESSAGE = "Ugh. Skipping corrupted line ";

    private final String dataDirName;
    private final String fileName;
    private final Path dataDir;
    private final Path filePath;
    private final Path corruptedFilePath;

    /**
     * Creates a new Storage instance with the specified data directory and file name.
     *
     * @param dataDirName The name of the data directory.
     * @param fileName The name of the storage file.
     */
    public Storage(String dataDirName, String fileName) {
        this.dataDirName = dataDirName;
        this.fileName = fileName;
        this.dataDir = Paths.get(dataDirName);
        this.filePath = dataDir.resolve(fileName);
        this.corruptedFilePath = dataDir.resolve(fileName + ".corrupted");
    }

    /**
     * Loads tasks from the storage file.
     * If the file does not exist, creates it and returns an empty result.
     *
     * @return The load result containing tasks and corruption statistics.
     * @throws MondayStorageException If an I/O error occurs during loading.
     */
    public LoadResult loadTasks() throws MondayStorageException {
        try {
            // Create directory and file if they don't exist
            if (!Files.exists(dataDir)) {
                Files.createDirectories(dataDir);
            }
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                return new LoadResult(new ArrayList<>(), 0);
            }

            List<Task> tasks = new ArrayList<>();
            ArrayList<String> lines = new ArrayList<>(Files.readAllLines(filePath));
            int corruptedCount = 0;

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
                    } else {
                        // Parse returned null - corrupted line
                        corruptedCount++;
                        System.err.println(CORRUPTED_LINE_MESSAGE + (i + 1));
                        backupCorruptedLine(lines.get(i));
                    }
                } catch (Exception e) {
                    // Exception during parsing - corrupted line
                    corruptedCount++;
                    System.err.println(CORRUPTED_LINE_MESSAGE + (i + 1));
                    backupCorruptedLine(lines.get(i));
                }
            }

            return new LoadResult(tasks, corruptedCount);
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
    private Task parseTask(String line) {
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
                task = new Deadline(description, byDateTime);
            } catch (DateTimeParseException e) {
                return null;
            }
            break;
        case "E":
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
                task = new Event(description, fromDateTime, toDateTime);
            } catch (DateTimeParseException e) {
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
     * Extracts the value from a field part (e.g., "by: Sunday" -> "Sunday").
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

    /**
     * Backs up a corrupted line to the corrupted file for possible recovery.
     *
     * @param line The corrupted line to backup.
     */
    private void backupCorruptedLine(String line) {
        try {
            // Ensure directory exists
            if (!Files.exists(dataDir)) {
                Files.createDirectories(dataDir);
            }
            // Append to corrupted file (create if doesn't exist)
            String lineWithNewline = line + System.lineSeparator();
            Files.write(corruptedFilePath, lineWithNewline.getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            // Backup failure should not prevent loading - just warn
            System.err.println("Warning: Couldn't backup corrupted line.");
        }
    }

    /**
     * Saves all tasks to the storage file.
     *
     * @param tasks The list of tasks to save.
     * @throws MondayStorageException If an I/O error occurs during saving.
     */
    public void saveTasks(List<Task> tasks) throws MondayStorageException {
        try {
            // Ensure directory exists
            if (!Files.exists(dataDir)) {
                Files.createDirectories(dataDir);
            }

            // Delete existing file if present
            Files.deleteIfExists(filePath);

            // Create new file
            Files.createFile(filePath);

            // Encode and write all tasks
            ArrayList<String> lines = new ArrayList<>();
            for (Task task : tasks) {
                lines.add(encodeTask(task));
            }

            Files.write(filePath, lines);
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
    private String encodeTask(Task task) {
        String type = task.getTypeIcon().replaceAll("[\\[\\]]", "");
        String done = task.isDone() ? "1" : "0";
        String desc = task.getDescription();

        if (task instanceof Deadline) {
            String by = ((Deadline) task).getByForStorage();
            return String.format("%s | %s | %s | by: %s", type, done, desc, by);
        } else if (task instanceof Event) {
            String from = ((Event) task).getFromForStorage();
            String to = ((Event) task).getToForStorage();
            return String.format("%s | %s | %s | from: %s | to: %s", type, done, desc, from, to);
        } else {
            return String.format("%s | %s | %s", type, done, desc);
        }
    }
}
