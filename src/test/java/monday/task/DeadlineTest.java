package monday.task;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for Deadline.
 * Tests deadline tasks with due date functionality.
 */
public class DeadlineTest {

    private static final LocalDateTime TEST_DATE = LocalDateTime.of(2024, 12, 2, 18, 0);
    private static final LocalDateTime DIFFERENT_DATE = LocalDateTime.of(2024, 12, 3, 10, 0);
    private static final LocalDateTime SAME_DAY_DIFFERENT_TIME = LocalDateTime.of(2024, 12, 2, 10, 0);

    @Test
    public void testConstructor_validInput() {
        Deadline deadline = new Deadline("return book", TEST_DATE);
        assertEquals("return book", deadline.getDescription());
    }

    @Test
    public void testGetTypeIcon() {
        Deadline deadline = new Deadline("return book", TEST_DATE);
        assertEquals("[D]", deadline.getTypeIcon());
    }

    @Test
    public void testGetBy() {
        Deadline deadline = new Deadline("return book", TEST_DATE);
        assertEquals("Dec 02 2024 1800", deadline.getBy());
    }

    @Test
    public void testGetByDateTime() {
        Deadline deadline = new Deadline("return book", TEST_DATE);
        assertEquals(TEST_DATE, deadline.getByDateTime());
    }

    @Test
    public void testGetByForStorage() {
        Deadline deadline = new Deadline("return book", TEST_DATE);
        assertEquals("2024-12-02 18:00", deadline.getByForStorage());
    }

    @Test
    public void testIsOnDate_sameDate() {
        Deadline deadline = new Deadline("return book", TEST_DATE);
        assertTrue(deadline.isOnDate(TEST_DATE));
    }

    @Test
    public void testIsOnDate_sameDayDifferentTime() {
        Deadline deadline = new Deadline("return book", TEST_DATE);
        assertTrue(deadline.isOnDate(SAME_DAY_DIFFERENT_TIME));
    }

    @Test
    public void testIsOnDate_differentDate() {
        Deadline deadline = new Deadline("return book", TEST_DATE);
        assertFalse(deadline.isOnDate(DIFFERENT_DATE));
    }

    @Test
    public void testToString_notDone() {
        Deadline deadline = new Deadline("return book", TEST_DATE);
        assertEquals("[D][ ] return book (by: Dec 02 2024 1800)", deadline.toString());
    }

    @Test
    public void testToString_done() {
        Deadline deadline = new Deadline("return book", TEST_DATE);
        deadline.markAsDone();
        assertEquals("[D][X] return book (by: Dec 02 2024 1800)", deadline.toString());
    }

    @Test
    public void testGetFullDescription() {
        Deadline deadline = new Deadline("return book", TEST_DATE);
        assertEquals("return book (by: Dec 02 2024 1800)", deadline.getFullDescription());
    }
}
