package monday.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for TaskList.
 * Tests task collection operations including add, delete, mark, and filter.
 */
public class TaskListTest {

    private static final LocalDateTime TEST_DATE_1 = LocalDateTime.of(2024, 12, 25, 14, 0);
    private static final LocalDateTime TEST_DATE_2 = LocalDateTime.of(2024, 12, 26, 18, 0);
    private static final LocalDateTime FILTER_DATE = LocalDateTime.of(2024, 12, 25, 10, 0);

    private TaskList emptyTaskList;
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        emptyTaskList = new TaskList();
        List<Task> tasks = Arrays.asList(
                new ToDo("Read book"),
                new Deadline("Return book", TEST_DATE_1),
                new Event("Meeting", TEST_DATE_1, TEST_DATE_2)
        );
        taskList = new TaskList(tasks);
    }

    @Test
    public void testConstructor_emptyList() {
        TaskList list = new TaskList();
        assertEquals(0, list.getTaskCount());
        assertTrue(list.isEmpty());
    }

    @Test
    public void testConstructor_withTasks() {
        List<Task> tasks = Arrays.asList(
                new ToDo("Task 1"),
                new ToDo("Task 2")
        );
        TaskList list = new TaskList(tasks);
        assertEquals(2, list.getTaskCount());
    }

    @Test
    public void testAddTask() {
        TaskList list = new TaskList();
        Task task = new ToDo("New task");
        list.addTask(task);
        assertEquals(1, list.getTaskCount());
        assertEquals(task, list.getTask(1));
    }

    @Test
    public void testDeleteTask_validIndex() {
        Task deleted = taskList.deleteTask(1);
        assertEquals("Read book", deleted.getDescription());
        assertEquals(2, taskList.getTaskCount());
    }

    @Test
    public void testDeleteTask_invalidIndex_emptyList() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            emptyTaskList.deleteTask(1);
        });
    }

    @Test
    public void testDeleteTask_invalidIndex_tooSmall() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            taskList.deleteTask(0);
        });
    }

    @Test
    public void testDeleteTask_invalidIndex_tooLarge() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            taskList.deleteTask(4);
        });
    }

    @Test
    public void testMarkTaskAsDone() {
        taskList.markTaskAsDone(1);
        assertTrue(taskList.getTask(1).isDone());
    }

    @Test
    public void testMarkTaskAsNotDone() {
        taskList.markTaskAsDone(1);
        taskList.markTaskAsNotDone(1);
        assertFalse(taskList.getTask(1).isDone());
    }

    @Test
    public void testGetTask() {
        Task task = taskList.getTask(1);
        assertEquals("Read book", task.getDescription());
    }

    @Test
    public void testGetTasks() {
        List<Task> tasks = taskList.getTasks();
        assertEquals(3, tasks.size());
    }

    @Test
    public void testGetTasks_returnsCopy() {
        List<Task> tasks1 = taskList.getTasks();
        List<Task> tasks2 = taskList.getTasks();
        // Should be different objects but same content
        assertEquals(tasks1, tasks2);
    }

    @Test
    public void testGetTaskCount() {
        assertEquals(0, emptyTaskList.getTaskCount());
        assertEquals(3, taskList.getTaskCount());
    }

    @Test
    public void testIsEmpty_empty() {
        assertTrue(emptyTaskList.isEmpty());
    }

    @Test
    public void testIsEmpty_notEmpty() {
        assertFalse(taskList.isEmpty());
    }

    @Test
    public void testIsAtMaxCapacity_false() {
        assertFalse(taskList.isAtMaxCapacity());
    }

    @Test
    public void testIsAtMaxCapacity_true() {
        TaskList list = new TaskList();
        for (int i = 0; i < 100; i++) {
            list.addTask(new ToDo("Task " + i));
        }
        assertTrue(list.isAtMaxCapacity());
    }

    @Test
    public void testIsValidTaskNumber_valid() {
        assertTrue(taskList.isValidTaskNumber(1));
        assertTrue(taskList.isValidTaskNumber(2));
        assertTrue(taskList.isValidTaskNumber(3));
    }

    @Test
    public void testIsValidTaskNumber_invalid_emptyList() {
        assertFalse(emptyTaskList.isValidTaskNumber(1));
    }

    @Test
    public void testIsValidTaskNumber_invalid_tooSmall() {
        assertFalse(taskList.isValidTaskNumber(0));
        assertFalse(taskList.isValidTaskNumber(-1));
    }

    @Test
    public void testIsValidTaskNumber_invalid_tooLarge() {
        assertFalse(taskList.isValidTaskNumber(4));
        assertFalse(taskList.isValidTaskNumber(100));
    }

    @Test
    public void testFilterTasksByDate_deadlineMatch() {
        List<Task> filtered = taskList.filterTasksByDate(FILTER_DATE);
        // Both Deadline and Event are on 2024-12-25
        assertEquals(2, filtered.size());
        assertTrue(filtered.stream().anyMatch(t -> t instanceof Deadline));
    }

    @Test
    public void testFilterTasksByDate_eventMatch() {
        List<Task> filtered = taskList.filterTasksByDate(FILTER_DATE);
        // Both Deadline and Event are on 2024-12-25
        assertEquals(2, filtered.size());
        assertTrue(filtered.stream().anyMatch(t -> t instanceof Event));
    }

    @Test
    public void testFilterTasksByDate_todoExcluded() {
        List<Task> filtered = taskList.filterTasksByDate(FILTER_DATE);
        // ToDo should not be in filtered results
        assertFalse(filtered.stream().anyMatch(t -> t instanceof ToDo));
    }

    @Test
    public void testFilterTasksByDate_noMatch() {
        LocalDateTime differentDate = LocalDateTime.of(2024, 12, 30, 10, 0);
        List<Task> filtered = taskList.filterTasksByDate(differentDate);
        assertTrue(filtered.isEmpty());
    }

    @Test
    public void testGetInvalidTaskNumberMessage_empty() {
        String message = emptyTaskList.getInvalidTaskNumberMessage();
        assertEquals("Skeptical. You haven't told me to do anything yet.", message);
    }

    @Test
    public void testGetInvalidTaskNumberMessage_hasTasks() {
        String message = taskList.getInvalidTaskNumberMessage();
        assertEquals("Ugh, that task doesn't exist. Pick between 1 and 3.", message);
    }
}
