package monday.storage;

import monday.constants.MessageConstants;
import monday.exception.ErrorHandler;
import monday.exception.MondayStorageException;
import monday.task.LoadResult;
import monday.task.Task;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles file storage operations for MONDAY's task list.
 * Provides methods to load tasks from disk and save tasks to disk.
 * This class acts as a facade that coordinates file operations and serialization.
 */
public class Storage {

    private final FileStorage fileStorage;
    private final TaskSerializer taskSerializer;
    private final TaskDeserializer taskDeserializer;
    private final CorruptionHandler corruptionHandler;
    private LoadResult lastLoadResult;

    /**
     * Creates a new Storage instance with specified data directory and file name.
     *
     * @param dataDirName The name of the data directory.
     * @param fileName The name of the storage file.
     */
    public Storage(String dataDirName, String fileName) {
        Path dataDir = Paths.get(dataDirName);
        Path filePath = dataDir.resolve(fileName);
        Path corruptedFilePath = dataDir.resolve(fileName + ".corrupted");

        this.fileStorage = new FileStorage(dataDir, filePath);
        this.taskSerializer = new TaskSerializer();
        this.taskDeserializer = new TaskDeserializer();
        this.corruptionHandler = new CorruptionHandler(dataDir, corruptedFilePath);
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
            List<String> lines = fileStorage.loadLines();
            List<Task> tasks = new ArrayList<>();
            int corruptedCount = 0;

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i).trim();

                // Skip empty lines
                if (line.isEmpty()) {
                    continue;
                }

                try {
                    Task task = taskDeserializer.parseTask(line);
                    if (task != null) {
                        tasks.add(task);
                    } else {
                        // Parse returned null - corrupted line
                        corruptedCount++;
                        corruptionHandler.printCorruptedLineMessage(i + 1);
                        corruptionHandler.backupCorruptedLine(lines.get(i));
                    }
                } catch (Exception e) {
                    // Exception during parsing - corrupted line
                    corruptedCount++;
                    corruptionHandler.printCorruptedLineMessage(i + 1);
                    corruptionHandler.backupCorruptedLine(lines.get(i));
                }
            }

            LoadResult result = new LoadResult(tasks, corruptedCount);
            lastLoadResult = result;
            return result;
        } catch (java.io.IOException e) {
            throw new MondayStorageException(
                ErrorHandler.createStorageErrorMessage(MessageConstants.STORAGE_ERROR_DATA_FILE_ACCESS, e)
            );
        }
    }

    /**
     * Gets the result of the last load operation.
     *
     * @return The last LoadResult.
     */
    public LoadResult getLoadResult() {
        return lastLoadResult;
    }

    /**
     * Saves all tasks to the storage file.
     *
     * @param tasks The list of tasks to save.
     * @throws MondayStorageException If an I/O error occurs during saving.
     */
    public void saveTasks(List<Task> tasks) throws MondayStorageException {
        try {
            // Encode all tasks using Streams API
            List<String> lines = tasks.stream()
                    .map(taskSerializer::encodeTask)
                    .collect(Collectors.toList());

            // Save lines to file
            fileStorage.saveLines(lines);
        } catch (java.io.IOException e) {
            throw new MondayStorageException(
                ErrorHandler.createStorageErrorMessage(MessageConstants.STORAGE_ERROR_SAVE_TASKS, e)
            );
        }
    }
}
