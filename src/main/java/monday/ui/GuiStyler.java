package monday.ui;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Handles styling for GUI components.
 * Loads the external CSS stylesheet; all visual styles are defined in styles.css.
 */
public class GuiStyler {

    private static final String STYLESHEET_PATH = "/monday/ui/styles.css";
    private static final double MIN_WIDTH = 300.0;
    private static final double MIN_HEIGHT = 400.0;

    /**
     * Loads the external CSS stylesheet onto the scene.
     *
     * @param scene The Scene to add the stylesheet to.
     */
    public void loadStylesheet(Scene scene) {
        String css = GuiStyler.class.getResource(STYLESHEET_PATH).toExternalForm();
        scene.getStylesheets().add(css);
    }

    /**
     * Configures the stage with title, scene, and minimum size.
     *
     * @param stage The Stage to configure.
     * @param scene The Scene to set on the stage.
     */
    public void configureStage(Stage stage, Scene scene) {
        stage.setScene(scene);
        stage.setTitle("MONDAY - Grumpy Task Manager");
        stage.setMinWidth(MIN_WIDTH);
        stage.setMinHeight(MIN_HEIGHT);
    }
}
