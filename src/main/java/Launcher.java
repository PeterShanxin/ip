import monday.Monday;

/**
 * Launcher class for MONDAY JavaFX application.
 * Required to avoid JavaFX module loading issues.
 */
public class Launcher {

    /**
     * Entry point for the application.
     * Delegates to Monday's JavaFX main.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Monday.main(args);
    }
}
