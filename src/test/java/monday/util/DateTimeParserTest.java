package monday.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for DateTimeParser.
 * Tests date/time parsing with multiple formats.
 */
public class DateTimeParserTest {

    @Test
    public void testParseDateTime_format1_valid() {
        LocalDateTime result = DateTimeParser.parseDateTime("2019-12-02 1800");
        assertEquals(LocalDateTime.of(2019, 12, 2, 18, 0), result);
    }

    @Test
    public void testParseDateTime_format1_midnight() {
        LocalDateTime result = DateTimeParser.parseDateTime("2019-12-02 0000");
        assertEquals(LocalDateTime.of(2019, 12, 2, 0, 0), result);
    }

    @Test
    public void testParseDateTime_format1_withMinutes() {
        LocalDateTime result = DateTimeParser.parseDateTime("2019-12-02 2359");
        assertEquals(LocalDateTime.of(2019, 12, 2, 23, 59), result);
    }

    @Test
    public void testParseDateTime_format2_valid() {
        LocalDateTime result = DateTimeParser.parseDateTime("2/12/2019 1800");
        assertEquals(LocalDateTime.of(2019, 12, 2, 18, 0), result);
    }

    @Test
    public void testParseDateTime_format2_singleDigitDay() {
        LocalDateTime result = DateTimeParser.parseDateTime("1/1/2024 0800");
        assertEquals(LocalDateTime.of(2024, 1, 1, 8, 0), result);
    }

    @Test
    public void testParseDateTime_format2_doubleDigitDay() {
        LocalDateTime result = DateTimeParser.parseDateTime("31/12/2024 1800");
        assertEquals(LocalDateTime.of(2024, 12, 31, 18, 0), result);
    }

    @Test
    public void testParseDateTime_invalidFormat() {
        assertThrows(DateTimeParseException.class, () -> {
            DateTimeParser.parseDateTime("invalid");
        });
    }

    @Test
    public void testParseDateTime_invalidDate() {
        assertThrows(DateTimeParseException.class, () -> {
            DateTimeParser.parseDateTime("2019-13-01 1800");
        });
    }

    @Test
    public void testParseDateTime_invalidTime() {
        assertThrows(DateTimeParseException.class, () -> {
            DateTimeParser.parseDateTime("2019-12-02 2500");
        });
    }

    @Test
    public void testParseDateTime_leapYear_valid() {
        LocalDateTime result = DateTimeParser.parseDateTime("2024-02-29 1200");
        assertEquals(LocalDateTime.of(2024, 2, 29, 12, 0), result);
    }

    @Test
    public void testParseDateTime_leapYear_format2() {
        LocalDateTime result = DateTimeParser.parseDateTime("29/2/2024 1200");
        assertEquals(LocalDateTime.of(2024, 2, 29, 12, 0), result);
    }

    @Test
    public void testParseDateTime_nonLeapYear_feb29() {
        // Java's LocalDateTime handles Feb 29 on non-leap years by rolling to Feb 28
        LocalDateTime result = DateTimeParser.parseDateTime("2023-02-29 1200");
        assertEquals(LocalDateTime.of(2023, 2, 28, 12, 0), result);
    }

    @Test
    public void testParseDateTime_emptyString() {
        assertThrows(DateTimeParseException.class, () -> {
            DateTimeParser.parseDateTime("");
        });
    }

    @Test
    public void testParseDateTime_missingTime() {
        assertThrows(DateTimeParseException.class, () -> {
            DateTimeParser.parseDateTime("2019-12-02");
        });
    }
}
