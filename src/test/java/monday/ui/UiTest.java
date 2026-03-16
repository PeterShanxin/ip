package monday.ui;

import monday.constants.ApplicationConstants;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for Ui.
 * Tests GUI-facing response caching behavior.
 */
public class UiTest {

    @Test
    public void showCheerMessage_ansiQuote_storesPlainTextForGui() {
        Ui ui = new Ui();
        String quote = ApplicationConstants.ANSI_YELLOW
                + "Congratulations on doing the bare minimum."
                + ApplicationConstants.ANSI_RESET;

        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        try {
            ui.showCheerMessage(quote);
        } finally {
            System.setOut(originalOut);
            ui.close();
        }

        assertEquals("Congratulations on doing the bare minimum.", ui.getLastResponse());
    }
}
