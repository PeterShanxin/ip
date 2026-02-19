package monday.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles file I/O operations for task storage.
 */
public class FileStorage {

    private final Path dataDir;
    private final Path filePath;

    /**
     * Creates a new FileStorage instance.
     *
     * @param dataDir The data directory path.
     * @param filePath The file path for task storage.
     */
    public FileStorage(Path dataDir, Path filePath) {
        this.dataDir = dataDir;
        this.filePath = filePath;
    }

    /**
     * Loads all lines from the storage file.
     * Creates the directory and file if they don't exist.
     *
     * @return List of lines from the file, or empty list if file doesn't exist.
     * @throws IOException If an I/O error occurs.
     */
    public List<String> loadLines() throws IOException {
        // Create directory and file if they don't exist
        if (!Files.exists(dataDir)) {
            Files.createDirectories(dataDir);
        }
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
            return new ArrayList<>();
        }

        return new ArrayList<>(Files.readAllLines(filePath));
    }

    /**
     * Saves lines to the storage file.
     *
     * @param lines The lines to save.
     * @throws IOException If an I/O error occurs.
     */
    public void saveLines(List<String> lines) throws IOException {
        // Ensure directory exists
        if (!Files.exists(dataDir)) {
            Files.createDirectories(dataDir);
        }

        // Delete existing file if present
        Files.deleteIfExists(filePath);

        // Create new file
        Files.createFile(filePath);

        // Write all lines
        Files.write(filePath, lines);
    }

    /**
     * Gets the data directory path.
     *
     * @return The data directory path.
     */
    public Path getDataDir() {
        return dataDir;
    }

    /**
     * Gets the file path.
     *
     * @return The file path.
     */
    public Path getFilePath() {
        return filePath;
    }
}
