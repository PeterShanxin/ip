package monday.storage;

import monday.constants.MessageConstants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Handles corrupted lines in storage.
 */
public class CorruptionHandler {

    private final Path dataDir;
    private final Path corruptedFilePath;

    /**
     * Creates a new CorruptionHandler.
     *
     * @param dataDir The data directory path.
     * @param corruptedFilePath The path to the corrupted file.
     */
    public CorruptionHandler(Path dataDir, Path corruptedFilePath) {
        this.dataDir = dataDir;
        this.corruptedFilePath = corruptedFilePath;
    }

    /**
     * Backs up a corrupted line to corrupted file for possible recovery.
     *
     * @param line The corrupted line to backup.
     */
    public void backupCorruptedLine(String line) {
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
            System.err.println(MessageConstants.BACKUP_WARNING);
        }
    }

    /**
     * Prints a corrupted line message to stderr.
     *
     * @param lineNumber The line number.
     */
    public void printCorruptedLineMessage(int lineNumber) {
        System.err.println(MessageConstants.CORRUPTED_LINE_MESSAGE_PREFIX + lineNumber);
    }
}
