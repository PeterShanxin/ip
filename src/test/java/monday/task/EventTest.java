package monday.task;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for Event.
 * Tests event tasks with time range functionality.
 */
public class EventTest {

    private static final LocalDateTime FROM_DATE = LocalDateTime.of(2024, 12, 25, 14, 0);
    private static final LocalDateTime TO_DATE = LocalDateTime.of(2024, 12, 25, 18, 0);
    private static final LocalDateTime DIFFERENT_DATE = LocalDateTime.of(2024, 12, 26, 10, 0);
    private static final LocalDateTime SAME_DAY_DIFFERENT_TIME = LocalDateTime.of(2024, 12, 25, 10, 0);

    @Test
    public void testConstructor_validInput() {
        Event event = new Event("project meeting", FROM_DATE, TO_DATE);
        assertEquals("project meeting", event.getDescription());
    }

    @Test
    public void testGetTypeIcon() {
        Event event = new Event("project meeting", FROM_DATE, TO_DATE);
        assertEquals("[E]", event.getTypeIcon());
    }

    @Test
    public void testGetFrom() {
        Event event = new Event("project meeting", FROM_DATE, TO_DATE);
        assertEquals("Dec 25 2024 1400", event.getFrom());
    }

    @Test
    public void testGetTo() {
        Event event = new Event("project meeting", FROM_DATE, TO_DATE);
        assertEquals("Dec 25 2024 1800", event.getTo());
    }

    @Test
    public void testGetFromDateTime() {
        Event event = new Event("project meeting", FROM_DATE, TO_DATE);
        assertEquals(FROM_DATE, event.getFromDateTime());
    }

    @Test
    public void testGetToDateTime() {
        Event event = new Event("project meeting", FROM_DATE, TO_DATE);
        assertEquals(TO_DATE, event.getToDateTime());
    }

    @Test
    public void testGetFromForStorage() {
        Event event = new Event("project meeting", FROM_DATE, TO_DATE);
        assertEquals("2024-12-25 14:00", event.getFromForStorage());
    }

    @Test
    public void testGetToForStorage() {
        Event event = new Event("project meeting", FROM_DATE, TO_DATE);
        assertEquals("2024-12-25 18:00", event.getToForStorage());
    }

    @Test
    public void testIsOnDate_sameDate() {
        Event event = new Event("project meeting", FROM_DATE, TO_DATE);
        assertTrue(event.isOnDate(FROM_DATE));
    }

    @Test
    public void testIsOnDate_sameDayDifferentTime() {
        Event event = new Event("project meeting", FROM_DATE, TO_DATE);
        assertTrue(event.isOnDate(SAME_DAY_DIFFERENT_TIME));
    }

    @Test
    public void testIsOnDate_differentDate() {
        Event event = new Event("project meeting", FROM_DATE, TO_DATE);
        assertFalse(event.isOnDate(DIFFERENT_DATE));
    }

    @Test
    public void testToString() {
        Event event = new Event("project meeting", FROM_DATE, TO_DATE);
        assertEquals("[E][ ] project meeting (from: Dec 25 2024 1400 to: Dec 25 2024 1800)",
                     event.toString());
    }

    @Test
    public void testToString_done() {
        Event event = new Event("project meeting", FROM_DATE, TO_DATE);
        event.markAsDone();
        assertEquals("[E][X] project meeting (from: Dec 25 2024 1400 to: Dec 25 2024 1800)",
                     event.toString());
    }

    @Test
    public void testGetFullDescription() {
        Event event = new Event("project meeting", FROM_DATE, TO_DATE);
        assertEquals("project meeting (from: Dec 25 2024 1400 to: Dec 25 2024 1800)",
                     event.getFullDescription());
    }
}
