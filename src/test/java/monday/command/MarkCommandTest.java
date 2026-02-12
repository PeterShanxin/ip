package monday.command;

import monday.storage.Storage;
import monday.task.Task;
import monday.task.TaskList;
import monday.ui.Ui;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for MarkCommand.
 * Tests mark and unmark operations including error handling.
 */
@ExtendWith(MockitoExtension.class)
public class MarkCommandTest {

    @Mock
    private TaskList taskList;

    @Mock
    private Ui ui;

    @Mock
    private Storage storage;

    @Mock
    private Task mockTask;

    @Test
    public void execute_markTask_success() throws CommandException {
        // Positive: Mark task as done
        int taskNumber = 1;
        MarkCommand command = new MarkCommand(taskNumber, true);

        when(taskList.isValidTaskNumber(taskNumber)).thenReturn(true);
        when(taskList.getTask(taskNumber)).thenReturn(mockTask);

        CommandResult result = command.execute(taskList, ui, storage);

        assertTrue(result.shouldSave(), "Marking task should require save");
        assertFalse(result.shouldExit(), "Mark command should not exit");
        verify(mockTask).markAsDone();
        verify(ui).showTaskMarked(mockTask, true);
    }

    @Test
    public void execute_unmarkTask_success() throws CommandException {
        // Positive: Unmark task (mark as not done)
        int taskNumber = 2;
        MarkCommand command = new MarkCommand(taskNumber, false);

        when(taskList.isValidTaskNumber(taskNumber)).thenReturn(true);
        when(taskList.getTask(taskNumber)).thenReturn(mockTask);

        CommandResult result = command.execute(taskList, ui, storage);

        assertTrue(result.shouldSave(), "Unmarking task should require save");
        assertFalse(result.shouldExit(), "Unmark command should not exit");
        verify(mockTask).markAsNotDone();
        verify(ui).showTaskMarked(mockTask, false);
    }

    @Test
    public void execute_invalidTaskNumber_emptyList() throws CommandException {
        // Negative: Empty list throws CommandException
        int taskNumber = 1;
        MarkCommand command = new MarkCommand(taskNumber, true);

        when(taskList.isValidTaskNumber(taskNumber)).thenReturn(false);
        when(taskList.getInvalidTaskNumberMessage())
                .thenReturn("Skeptical. You haven't told me to do anything yet.");

        assertThrows(CommandException.class, () -> command.execute(taskList, ui, storage),
                "Should throw CommandException for invalid task number on empty list");
    }

    @Test
    public void execute_invalidTaskNumber_outOfRange() throws CommandException {
        // Negative: Task number too high throws CommandException
        int taskNumber = 99;
        MarkCommand command = new MarkCommand(taskNumber, true);

        when(taskList.isValidTaskNumber(taskNumber)).thenReturn(false);
        when(taskList.getInvalidTaskNumberMessage())
                .thenReturn("Ugh, that task doesn't exist. Pick between 1 and 3.");

        assertThrows(CommandException.class, () -> command.execute(taskList, ui, storage),
                "Should throw CommandException for out of range task number");
    }

    @Test
    public void execute_negativeTaskNumber() throws CommandException {
        // Negative: Task number = 0 throws CommandException
        int taskNumber = 0;
        MarkCommand command = new MarkCommand(taskNumber, false);

        when(taskList.isValidTaskNumber(taskNumber)).thenReturn(false);
        when(taskList.getInvalidTaskNumberMessage())
                .thenReturn("Ugh, that task doesn't exist. Pick between 1 and 3.");

        assertThrows(CommandException.class, () -> command.execute(taskList, ui, storage),
                "Should throw CommandException for negative task number");
    }
}
