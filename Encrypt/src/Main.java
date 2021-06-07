import javafx.stage.Stage;
import javafx.application.Application;

public class Main extends Application
{
    public void start(final Stage primaryStage) throws Exception {
        final UI gui = new UI(primaryStage);
        gui.start();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}