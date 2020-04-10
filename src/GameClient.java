import Controller.ViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameClient extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ViewController controller = new ViewController();
        controller.setController(controller);
        FXMLLoader root = new FXMLLoader(getClass().getResource("View/LoginWindowView.fxml"));
        root.setController(controller);
        Parent parentroot = root.load();
        Scene scene = new Scene(parentroot);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }
}