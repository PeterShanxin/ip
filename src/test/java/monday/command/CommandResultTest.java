package monday.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for CommandResult.
 * Tests command execution result flags for save and exit behavior.
 */
public class CommandResultTest {

    @Test
    public void constructor_saveTrue_exitTrue() {
        // Positive: Both flags true (e.g., ExitCommand saves and exits)
        CommandResult result = new CommandResult(true, true);

        assertTrue(result.shouldSave(),
                "shouldSave() should return true when both flags are true");
        assertTrue(result.shouldExit(),
                "shouldExit() should return true when both flags are true");
    }

    @Test
    public void constructor_saveTrue_exitFalse() {
        // Positive: Save only (e.g., MarkCommand saves but doesn't exit)
        CommandResult result = new CommandResult(true, false);

        assertTrue(result.shouldSave(),
                "shouldSave() should return true");
        assertFalse(result.shouldExit(),
                "shouldExit() should return false");
    }

    @Test
    public void constructor_saveFalse_exitFalse() {
        // Positive: Neither flag (e.g., ListCommand doesn't save or exit)
        CommandResult result = new CommandResult(false, false);

        assertFalse(result.shouldSave(),
                "shouldSave() should return false");
        assertFalse(result.shouldExit(),
                "shouldExit() should return false");
    }

    @Test
    public void constructor_saveFalse_exitTrue() {
        // Positive: Exit only (edge case - exit without save)
        CommandResult result = new CommandResult(false, true);

        assertFalse(result.shouldSave(),
                "shouldSave() should return false");
        assertTrue(result.shouldExit(),
                "shouldExit() should return true");
    }

    @Test
    public void shouldSave_allCombinations() {
        // Verify all four boolean combinations work correctly
        CommandResult saveAndExit = new CommandResult(true, true);
        CommandResult saveOnly = new CommandResult(true, false);
        CommandResult exitOnly = new CommandResult(false, true);
        CommandResult neither = new CommandResult(false, false);

        assertTrue(saveAndExit.shouldSave(), "saveAndExit should require save");
        assertTrue(saveOnly.shouldSave(), "saveOnly should require save");
        assertFalse(exitOnly.shouldSave(), "exitOnly should not require save");
        assertFalse(neither.shouldSave(), "neither should not require save");
    }
}
