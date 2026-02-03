package monday;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the result of loading tasks from storage.
 * Contains both the successfully loaded tasks and corruption statistics.
 */
public class LoadResult {

    private final ArrayList<Task> tasks;
    private final int corruptedLineCount;

    /**
     * Creates a new load result with the specified tasks and corruption count.
     *
     * @param tasks The list of successfully loaded tasks.
     * @param corruptedLineCount The number of corrupted lines that were skipped.
     */
    public LoadResult(ArrayList<Task> tasks, int corruptedLineCount) {
        this.tasks = tasks;
        this.corruptedLineCount = corruptedLineCount;
    }

    /**
     * Returns the list of successfully loaded tasks.
     *
     * @return The list of tasks.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Returns the number of corrupted lines that were skipped during loading.
     *
     * @return The count of corrupted lines.
     */
    public int getCorruptedLineCount() {
        return corruptedLineCount;
    }

    /**
     * Checks whether any corrupted lines were encountered during loading.
     *
     * @return true if corrupted lines were found, false otherwise.
     */
    public boolean hasCorruption() {
        return corruptedLineCount > 0;
    }
}
