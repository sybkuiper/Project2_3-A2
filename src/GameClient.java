import Controller.NetworkController;
import Controller.ViewController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameClient extends Application {
    ViewController viewController;
    NetworkController networkController;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("View/LoginWindowView.fxml"));
//        De volgende is voor testing purposes van de front-end
//        Parent root = FXMLLoader.load(getClass().getResource("View/OthelloView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        //viewController = new ViewController();
        //networkController = new NetworkController();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }
}