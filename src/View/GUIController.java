import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Controller.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;



public class GUIController implements Initializable {
	private int counter = 0;
	@FXML private BorderPane rootPane;
	private BorderPane rPane;
	@FXML private TextField field;
	@FXML private CheckBox online;
	@FXML private Label label;
	@FXML private TextField IP, port;
	@FXML private Button nextbutton,menubutton,menu,xbutton;
	@FXML private Label counterlabel;
	@FXML private Button Sp_T_Button,AI_T_Button,Mp_T_Button,Sp_R_Button,Mp_R_Button;
	private ViewController viewController;

	@FXML
	void handleButtonTTT_SP(ActionEvent event) throws IOException {
		Stage stage;
		Parent root;
		stage = (Stage) Sp_T_Button.getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("SpTicTacView.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void gotomenuscreen(ActionEvent event) throws IOException {
		Stage stage;
		Parent root;
		if(online.isSelected()){
			viewController = new ViewController(IP.getText(),Integer.parseInt(port.getText()));
		} else {
			viewController = new ViewController();
		}
		stage = (Stage) menu.getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("View/MenuWindowView.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}


	@FXML
	void showPlayer(ActionEvent event) throws IOException {
		Image image = null;
		char player = 'X';
		//TODO make sure we can check who plays with which symbol
		if(player == 'X') {
			image = new Image(getClass().getResourceAsStream("img/cross.png"));
		}else{
			image = new Image(getClass().getResourceAsStream("img/circle.png"));
		}
		Button button = (Button) event.getSource();
		button.setDisable(true);
		button.setOpacity(1.0);
		if(image != null) {
			ImageView imageTemp = new ImageView(image);
			imageTemp.setFitHeight(80);
			imageTemp.setFitWidth(80);
			button.setGraphic(imageTemp);
		}
		counter++;
		System.out.print(counter);

	}

	@FXML
	void onlinePlay(ActionEvent event) throws IOException {
		CheckBox box = (CheckBox) event.getSource();
		if(box.isSelected()){
			IP.setVisible(true);
			port.setVisible(true);
		}else{
			IP.setVisible(false);
			port.setVisible(false);
		}
	}




	@FXML
	void handleButtonR_AI(ActionEvent event) {

	}

	@FXML
	void handleButtonR_PVP(ActionEvent event) throws IOException {

	}

	@FXML
	void handleButtonR_SP(ActionEvent event) {

	}

	@FXML
	void handleButtonTTT_AI(ActionEvent event) {

	}

	@FXML
	void handleButtonTTT_PVP(ActionEvent event) throws IOException {
		Stage stage;
		Parent root;
		stage = (Stage) Mp_T_Button.getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("MpTicTacView.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}




	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}


}