import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//TODO: add search text in file use Forward/Back
//TODO: add open large files

/**
 * A {@code Main} is entry point for application
 */
public class Main extends Application {

    private static final double WIDTH = 900;
    private static final double HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoaderMain = new FXMLLoader(getClass().getResource("fxml/ComplexApplication.fxml"));
        Parent root = fxmlLoaderMain.load();
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
