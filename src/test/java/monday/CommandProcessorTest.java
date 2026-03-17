package monday;

import monday.constants.MessageConstants;
import monday.parser.Parser;
import monday.storage.Storage;
import monday.task.TaskList;
import monday.ui.GuiResponse;
import monday.ui.Ui;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for CommandProcessor.
 * Tests GUI response generation for command execution.
 */
@ExtendWith(MockitoExtension.class)
public class CommandProcessorTest {

    @Mock
    private Storage storage;

    @Mock
    private TaskList taskList;

    @Test
    public void processCommand_bye_returnsFarewellAndExitFlag() {
        Parser parser = new Parser();
        Ui ui = new Ui();
        CommandProcessor commandProcessor = new CommandProcessor(parser, ui, storage, taskList, false);

        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        try {
            GuiResponse response = commandProcessor.processCommand("bye");

            assertEquals(MessageConstants.FAREWELL_MESSAGE, response.text());
            assertFalse(response.isError());
            assertTrue(response.shouldExit());
        } finally {
            System.setOut(originalOut);
            ui.close();
        }
    }
}
