import javafx.scene.layout.VBox;
import java.awt.Component;
import javax.swing.JOptionPane;
import java.util.Map;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;

//The class responsible for the ui
public class UI
{
    private Stage _stage;
    private Scene _scene;
    private Button _toEncrypt;
    private Button _toDecrypt;
    private Button _enter;
    private Button _back;
    private TextField _messageBox;
    private Label _Message;
    private Label _OldMessage;
    private Label _printout;
    private String _ogMessage;
    private String _newMessage;
    private Button _close;
    private Button _copy;

    //Starts the gui by constructing the stage
    public UI(final Stage stage) {
        this._stage = stage;
    }

    //Begins the program
    public void start() {
        this._stage.setTitle("");
        (this._toEncrypt = new Button("Encrypt a message")).setOnAction(e -> this.encrypt());
        (this._toDecrypt = new Button("Decrypt a message")).setOnAction(e -> this.decrypt());
        (this._close = new Button("Close")).setOnAction(e -> System.exit(0));
        final HBox container = new HBox(new Node[] { (Node)this._toEncrypt, (Node)this._toDecrypt });
        final StackPane closeHolder = new StackPane(new Node[] { (Node)this._close });
        container.setSpacing(20.0);
        final BorderPane base = new BorderPane();
        base.setRight((Node)closeHolder);
        BorderPane.setMargin((Node)closeHolder, new Insets(100.0, 5.0, 10.0, 10.0));
        base.setCenter((Node)container);
        BorderPane.setMargin((Node)container, new Insets(50.0, 50.0, 0.0, 100.0));
        this._scene = new Scene((Parent)base, 480.0, 144.0);
        this._stage.setScene(this._scene);
        this._stage.show();
    }

    //Decrypts a previously encrypted message
    private void decrypt() {
        this._stage.close();
        (this._stage = new Stage()).initStyle(StageStyle.UTILITY);
        this._stage.setTitle("Enter a message to Decrypt");
        (this._messageBox = new PersistentPromptTextField("", "Enter a message to be decrypted here")).setPrefWidth(300.0);
        (this._enter = new Button("Enter")).setDefaultButton(true);
        this._enter.setOnAction((EventHandler)new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {
                UI.this._stage.close();
                UI.this._ogMessage = UI.this._messageBox.getText();
                System.out.println(UI.this._ogMessage);
                UI.this._OldMessage = new Label("Original Message: " + UI.this._ogMessage);
                UI.this._newMessage = Cypher.decrypt(UI.this._ogMessage);
                System.out.println(UI.this._newMessage);
                UI.this._Message = new Label("Decrypted Message: " + UI.this._newMessage);
                UI.this._printout = new Label(Cypher.info(UI.this._newMessage));
                UI.this.results();
            }
        });
        (this._back = new Button("Back")).setOnAction((EventHandler)new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {
                UI.this._stage.close();
                UI.this.start();
            }
        });
        final AnchorPane layout = new AnchorPane(new Node[] { (Node)this._messageBox, (Node)this._enter, (Node)this._back });
        AnchorPane.setRightAnchor((Node)this._enter, Double.valueOf(10.0));
        AnchorPane.setBottomAnchor((Node)this._enter, Double.valueOf(10.0));
        AnchorPane.setLeftAnchor((Node)this._back, Double.valueOf(10.0));
        AnchorPane.setBottomAnchor((Node)this._back, Double.valueOf(10.0));
        AnchorPane.setRightAnchor((Node)this._messageBox, Double.valueOf(100.0));
        AnchorPane.setTopAnchor((Node)this._messageBox, Double.valueOf(50.0));
        this._scene = new Scene((Parent)layout, 480.0, 144.0);
        this._scene.getStylesheets().add(this.getClass().getResource("persistent-prompt.css").toExternalForm());
        this._stage.setScene(this._scene);
        this._stage.show();
    }

    //Gives the results of encryption or decryption
    private void results() {
        this._stage.setTitle("");
        (this._close = new Button("Close")).setOnAction(e -> System.exit(0));
        (this._copy = new Button("Copy")).setOnAction((EventHandler)new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {
                final ClipboardContent content = new ClipboardContent();
                content.putString(UI.this._newMessage);
                content.putHtml("<b>Bold</b> text");
                Clipboard.getSystemClipboard().setContent((Map)content);
                JOptionPane.showMessageDialog(null, "Copied to clipboard");
                UI.this.results();
            }
        });
        (this._back = new Button("Home")).setOnAction((EventHandler)new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {
                UI.this._stage.close();
                UI.this.start();
            }
        });
        final VBox messages = new VBox(new Node[] { (Node)this._OldMessage, (Node)this._Message, (Node)this._printout });
        messages.setSpacing(10.0);
        final HBox buttons = new HBox(new Node[] { (Node)this._close, (Node)this._copy, (Node)this._back });
        buttons.setSpacing(20.0);
        final BorderPane layout = new BorderPane();
        layout.setBottom((Node)buttons);
        BorderPane.setMargin((Node)buttons, new Insets(0.0, 0.0, 10.0, 150.0));
        layout.setCenter((Node)messages);
        BorderPane.setMargin((Node)messages, new Insets(0.0, 0.0, 10.0, 150.0));
        this._scene = new Scene((Parent)layout, 480.0, 144.0);
        this._stage.setScene(this._scene);
        this._stage.show();
    }

    //Encrypts a given message
    private void encrypt() {
        this._stage.close();
        (this._stage = new Stage()).initStyle(StageStyle.UTILITY);
        this._stage.setTitle("Enter a message to Encrypt");
        (this._messageBox = new PersistentPromptTextField("", "Enter a message to be encrypted here")).setPrefWidth(300.0);
        (this._enter = new Button("Enter")).setDefaultButton(true);
        this._enter.setOnAction((EventHandler)new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {
                UI.this._stage.close();
                UI.this._ogMessage = UI.this._messageBox.getText();
                System.out.println(UI.this._ogMessage);
                UI.this._OldMessage = new Label("Original Message: " + UI.this._ogMessage);
                UI.this._newMessage = Cypher.encrypt(UI.this._ogMessage);
                System.out.println(UI.this._newMessage);
                UI.this._Message = new Label("Encrypted Message: " + UI.this._newMessage);
                UI.this._printout = new Label(Cypher.info(UI.this._newMessage));
                UI.this.results();
            }
        });
        (this._back = new Button("Back")).setOnAction((EventHandler)new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {
                UI.this._stage.close();
                UI.this.start();
            }
        });
        final AnchorPane layout = new AnchorPane(new Node[] { (Node)this._messageBox, (Node)this._enter, (Node)this._back });
        AnchorPane.setRightAnchor((Node)this._enter, Double.valueOf(10.0));
        AnchorPane.setBottomAnchor((Node)this._enter, Double.valueOf(10.0));
        AnchorPane.setLeftAnchor((Node)this._back, Double.valueOf(10.0));
        AnchorPane.setBottomAnchor((Node)this._back, Double.valueOf(10.0));
        AnchorPane.setRightAnchor((Node)this._messageBox, Double.valueOf(100.0));
        AnchorPane.setTopAnchor((Node)this._messageBox, Double.valueOf(50.0));
        this._scene = new Scene((Parent)layout, 480.0, 144.0);
        this._scene.getStylesheets().add(this.getClass().getResource("persistent-prompt.css").toExternalForm());
        this._stage.setScene(this._scene);
        this._stage.show();
    }
}
