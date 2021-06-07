import javafx.scene.control.TextField;

//A class responsible for gui text prompts
public class PersistentPromptTextField extends TextField
{
    //A method that adds a prompt to the text field for inputting a message
    public PersistentPromptTextField(final String text, final String prompt) {
        super(text);
        this.setPromptText(prompt);
        this.getStyleClass().add("persistent-prompt");
        this.refreshPromptVisibility();
        this.textProperty().addListener(observable -> this.refreshPromptVisibility());
    }

    //A method that refreshes the prompt
    private void refreshPromptVisibility() {
        final String text = this.getText();
        if (this.isEmptyString(text)) {
            this.getStyleClass().remove("no-prompt");
        }
        else if (!this.getStyleClass().contains("no-prompt")) {
            this.getStyleClass().add("no-prompt");
        }
    }

    //checks if the given string is empty
    private boolean isEmptyString(final String text) {
        return text == null || text.isEmpty();
    }
}