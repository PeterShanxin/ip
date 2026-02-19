package monday;

import monday.storage.Storage;
import monday.task.LoadResult;
import monday.ui.MainWindow;
import monday.ui.Ui;

import javafx.stage.Stage;

/**
 * Orchestrates GUI setup and initialization.
 * Manages the coordination between MainWindow and application components.
 */
public class GuiOrchestrator {

    private final Monday monday;
    private final Ui ui;
    private final Storage storage;
    private final boolean hasCorruption;

    /**
     * Creates a new GuiOrchestrator with the required components.
     *
     * @param monday The Monday instance for command processing.
     * @param ui The Ui for generating messages.
     * @param storage The Storage for accessing load results.
     * @param hasCorruption Whether corruption was detected during load.
     */
    public GuiOrchestrator(Monday monday, Ui ui, Storage storage, boolean hasCorruption) {
        this.monday = monday;
        this.ui = ui;
        this.storage = storage;
        this.hasCorruption = hasCorruption;
    }

    /**
     * Sets up the GUI with the given stage.
     *
     * @param primaryStage The primary stage for the GUI.
     */
    public void setupGui(Stage primaryStage) {
        MainWindow mainWindow = new MainWindow();
        mainWindow.setMonday(monday);
        mainWindow.start(primaryStage);

        displayGreeting(mainWindow);
        displayCorruptionWarning(mainWindow);
    }

    /**
     * Displays the greeting message in the GUI.
     *
     * @param mainWindow The MainWindow to display the greeting in.
     */
    private void displayGreeting(MainWindow mainWindow) {
        String greeting = ui.getGreetingForGui();
        mainWindow.showMessage(greeting);
    }

    /**
     * Displays the corruption warning message if corruption was detected.
     *
     * @param mainWindow The MainWindow to display the warning in.
     */
    private void displayCorruptionWarning(MainWindow mainWindow) {
        if (hasCorruption) {
            LoadResult loadResult = storage.getLoadResult();
            mainWindow.showMessage("Ugh. I skipped " + loadResult.getCorruptedLineCount()
                + " corrupted lines.\nCheck monday.txt.corrupted for recovery.");
        }
    }
}
