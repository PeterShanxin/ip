package monday.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for ToDo.
 * Tests the simple todo task functionality.
 */
public class ToDoTest {

    @Test
    public void testConstructor_validDescription() {
        ToDo todo = new ToDo("Read book");
        assertEquals("Read book", todo.getDescription());
    }

    @Test
    public void testGetTypeIcon() {
        ToDo todo = new ToDo("Read book");
        assertEquals("[T]", todo.getTypeIcon());
    }

    @Test
    public void testToString_notDone() {
        ToDo todo = new ToDo("Read book");
        assertEquals("[T][ ] Read book", todo.toString());
    }

    @Test
    public void testToString_done() {
        ToDo todo = new ToDo("Read book");
        todo.markAsDone();
        assertEquals("[T][X] Read book", todo.toString());
    }
}
