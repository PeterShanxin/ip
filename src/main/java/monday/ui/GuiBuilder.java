package monday.ui;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Builder class for creating and configuring GUI components.
 * Handles the construction of all UI elements for the MainWindow.
 */
public class GuiBuilder {

    /**
     * Creates and configures the dialog container VBox.
     *
     * @return The configured VBox for dialog display.
     */
    public VBox buildDialogContainer() {
        VBox dialogContainer = new VBox();
        dialogContainer.setSpacing(8);
        dialogContainer.prefHeight(javafx.scene.layout.Region.USE_COMPUTED_SIZE);
        dialogContainer.getStyleClass().add("dialog-container");
        return dialogContainer;
    }

    /**
     * Creates and configures the scroll pane.
     *
     * @param dialogContainer The dialog container to display in the scroll pane.
     * @return The configured ScrollPane.
     */
    public ScrollPane buildScrollPane(VBox dialogContainer) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(dialogContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setVvalue(1.0);
        scrollPane.getStyleClass().add("scroll-pane");
        return scrollPane;
    }

    /**
     * Creates and configures the user input text field.
     *
     * @return The configured TextField.
     */
    public TextField buildInputField() {
        TextField userInput = new TextField();
        userInput.setPromptText("Tell me what to do...");
        userInput.getStyleClass().add("input-field");
        return userInput;
    }

    /**
     * Creates and configures the send button.
     *
     * @return The configured Button.
     */
    public Button buildSendButton() {
        Button button = new Button("Send");
        button.getStyleClass().add("send-button");
        return button;
    }

    /**
     * Creates and configures the main layout AnchorPane.
     *
     * @param scrollPane The scroll pane to include in the layout.
     * @param userInput The input field to include in the layout.
     * @param sendButton The send button to include in the layout.
     * @return The configured AnchorPane with all components.
     */
    public AnchorPane buildMainLayout(ScrollPane scrollPane, TextField userInput, Button sendButton) {
        // Create input row using HBox with HGrow.ALWAYS on TextField for resize
        HBox inputRow = new HBox(10);
        inputRow.getChildren().addAll(userInput, sendButton);
        HBox.setHgrow(userInput, Priority.ALWAYS);
        inputRow.getStyleClass().add("input-row");

        // Create main layout with AnchorPane
        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, inputRow);

        // Layout constraints for scroll pane
        AnchorPane.setTopAnchor(scrollPane, 0.0);
        AnchorPane.setBottomAnchor(scrollPane, 50.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        AnchorPane.setRightAnchor(scrollPane, 0.0);

        // Layout constraints for input row
        AnchorPane.setBottomAnchor(inputRow, 10.0);
        AnchorPane.setLeftAnchor(inputRow, 10.0);
        AnchorPane.setRightAnchor(inputRow, 10.0);

        return mainLayout;
    }
}
