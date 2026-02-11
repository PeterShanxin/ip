package monday.task;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for LoadResult.
 * Tests the storage load result wrapper functionality.
 */
public class LoadResultTest {

    @Test
    public void testConstructor_validInput() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("Task 1"));
        LoadResult result = new LoadResult(tasks, 0);
        assertEquals(1, result.getTasks().size());
        assertEquals(0, result.getCorruptedLineCount());
    }

    @Test
    public void testGetTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("Task 1"));
        tasks.add(new ToDo("Task 2"));
        LoadResult result = new LoadResult(tasks, 0);
        assertEquals(2, result.getTasks().size());
    }

    @Test
    public void testGetCorruptedLineCount() {
        LoadResult result = new LoadResult(new ArrayList<>(), 5);
        assertEquals(5, result.getCorruptedLineCount());
    }

    @Test
    public void testHasCorruption_true() {
        LoadResult result = new LoadResult(new ArrayList<>(), 1);
        assertTrue(result.hasCorruption());
    }

    @Test
    public void testHasCorruption_false() {
        LoadResult result = new LoadResult(new ArrayList<>(), 0);
        assertFalse(result.hasCorruption());
    }

    @Test
    public void testHasCorruption_multipleCorruptions() {
        LoadResult result = new LoadResult(new ArrayList<>(), 10);
        assertTrue(result.hasCorruption());
        assertEquals(10, result.getCorruptedLineCount());
    }
}
