package monday.command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import monday.task.TaskList;
import monday.ui.Ui;
import monday.storage.Storage;

/**
 * Command to display a grumpy motivational quote.
 * Reads quotes from a file and displays one at random with colored output.
 */
public class CheerCommand extends Command {

    private static final String CHEER_FILE_PATH = "data/cheer.txt";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String DEFAULT_QUOTE =
            "Congratulations on doing the bare minimum. That's still more than most people manage.";

    /**
     * Executes the cheer command.
     * Reads quotes from the cheer file and displays a random one.
     *
     * @param taskList The task list (not used).
     * @param ui The UI for displaying messages.
     * @param storage The storage (not used).
     * @return A command result indicating no save or exit needed.
     */
    @Override
    public CommandResult execute(TaskList taskList, Ui ui, Storage storage) {
        List<String> quotes = loadQuotes();
        String quote = selectRandomQuote(quotes);
        ui.showCheerMessage(quote);
        return new CommandResult(false, false);
    }

    /**
     * Checks if this command should exit the application.
     *
     * @return false, as this is not an exit command.
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * Loads quotes from the cheer file.
     * Returns a default quote if the file cannot be read.
     *
     * @return A list of quotes from the file, or a list with the default quote.
     */
    private List<String> loadQuotes() {
        List<String> quotes = new ArrayList<>();
        Path path = Paths.get(CHEER_FILE_PATH);

        if (Files.exists(path)) {
            try {
                quotes = Files.readAllLines(path);
                // Filter out empty lines
                quotes.removeIf(String::isBlank);
            } catch (IOException e) {
                // Fall through to return default quote
            }
        }

        if (quotes.isEmpty()) {
            quotes.add(DEFAULT_QUOTE);
        }

        return quotes;
    }

    /**
     * Selects a random quote from the list.
     *
     * @param quotes The list of quotes to choose from.
     * @return A randomly selected quote, wrapped in ANSI color codes.
     */
    private String selectRandomQuote(List<String> quotes) {
        int index = (int) (Math.random() * quotes.size());
        return ANSI_YELLOW + quotes.get(index) + ANSI_RESET;
    }
}
