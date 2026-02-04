package monday.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for Task.
 * Tests the base task functionality including status management.
 */
public class TaskTest {

    @Test
    public void testNewTask_isNotDone() {
        Task task = new Task("Read book");
        assertFalse(task.isDone());
    }

    @Test
    public void testMarkAsDone() {
        Task task = new Task("Read book");
        task.markAsDone();
        assertTrue(task.isDone());
    }

    @Test
    public void testMarkAsNotDone() {
        Task task = new Task("Read book");
        task.markAsDone();
        assertTrue(task.isDone());
        task.markAsNotDone();
        assertFalse(task.isDone());
    }

    @Test
    public void testGetStatusIcon_notDone() {
        Task task = new Task("Read book");
        assertEquals("[ ]", task.getStatusIcon());
    }

    @Test
    public void testGetStatusIcon_done() {
        Task task = new Task("Read book");
        task.markAsDone();
        assertEquals("[X]", task.getStatusIcon());
    }

    @Test
    public void testGetTypeIcon() {
        Task task = new Task("Read book");
        assertEquals("[]", task.getTypeIcon());
    }

    @Test
    public void testGetFullDescription() {
        Task task = new Task("Read book");
        assertEquals("Read book", task.getFullDescription());
    }

    @Test
    public void testToString() {
        Task task = new Task("Read book");
        assertEquals("[][ ] Read book", task.toString());
    }

    @Test
    public void testToString_done() {
        Task task = new Task("Read book");
        task.markAsDone();
        assertEquals("[][X] Read book", task.toString());
    }
}
