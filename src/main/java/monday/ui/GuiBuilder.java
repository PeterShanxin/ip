package monday.ui;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
        dialogContainer.setSpacing(10);
        dialogContainer.prefHeight(javafx.scene.layout.Region.USE_COMPUTED_SIZE);
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
        return userInput;
    }

    /**
     * Creates and configures the send button.
     *
     * @return The configured Button.
     */
    public Button buildSendButton() {
        return new Button("Send");
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
        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        // Layout constraints for scroll pane
        AnchorPane.setTopAnchor(scrollPane, 0.0);
        AnchorPane.setBottomAnchor(scrollPane, 50.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        AnchorPane.setRightAnchor(scrollPane, 0.0);

        // Layout constraints for input field
        AnchorPane.setBottomAnchor(userInput, 10.0);
        AnchorPane.setLeftAnchor(userInput, 10.0);
        AnchorPane.setRightAnchor(userInput, 90.0);

        // Layout constraints for send button
        AnchorPane.setBottomAnchor(sendButton, 10.0);
        AnchorPane.setRightAnchor(sendButton, 10.0);

        return mainLayout;
    }
}
