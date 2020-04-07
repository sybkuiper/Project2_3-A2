import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

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
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static javafx.scene.paint.Color.*;


public class GUIController implements Initializable {
	private int counter = 0;
	@FXML private BorderPane rootPane;
	private BorderPane rPane;
	@FXML private GridPane board;
	@FXML private TextField field;
	@FXML private Text textOthello;
	@FXML private CheckBox online;
	@FXML private Label label;
	@FXML private TextField IP, port;
	@FXML private Button nextbutton,menubutton,menu,xbutton, rematchButton;
	@FXML private Label counterlabel;
	@FXML private Button Sp_T_Button,AI_T_Button,Mp_T_Button,Sp_R_Button,Mp_R_Button;
	private ViewController viewController;

	@FXML
	void handleButtonTTT_SP(ActionEvent event) throws IOException {
		Stage stage;
		Parent root;
		stage = (Stage) Sp_T_Button.getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("View/SpTicTacView.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void gotomenuscreen(ActionEvent event) throws IOException {
		Stage stage;
		Parent root;
//		if(online.isSelected()){
//			viewController = new ViewController(IP.getText(),Integer.parseInt(port.getText()));
//		} else {
//			viewController = new ViewController();
//		}
		stage = (Stage) menu.getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("View/MenuWindowView.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}


	@FXML
	void showPlayer(ActionEvent event) {
		Image image = null;
		char player = 'O';
		//TODO make sure we can check who plays with which symbol
		if(player == 'X') {
			image = new Image(getClass().getResourceAsStream("Img/cross.png"));
		}else{
			image = new Image(getClass().getResourceAsStream("Img/circle.png"));
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

	}

	@FXML
	void onlinePlay(ActionEvent event) {
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
	void gridClicked(javafx.scene.input.MouseEvent event) {
		Node clickedNode = event.getPickResult().getIntersectedNode();
		if(clickedNode != board) {
			Integer collIndex = GridPane.getColumnIndex(clickedNode);
			Integer rowIndex = GridPane.getRowIndex(clickedNode);
			if(collIndex == null){
				collIndex = 0;
			}
			if(rowIndex == null){
				rowIndex = 0;
			}
			collIndex += 1;
			rowIndex += 1;
			System.out.println("Clicked: " + collIndex + ", " + rowIndex);
		}
		if (clickedNode instanceof Circle) {
			Circle clickedNodes = (Circle) clickedNode;
			clickedNodes.setOpacity(1);
			//TODO make a variable that declares who has which color
			char color = 'Z';
			if (color == 'Z') {
				clickedNodes.setFill(BLACK);
				clickedNodes.setStroke(BLACK);
			} else {
				clickedNodes.setFill(WHITE);
				clickedNodes.setStroke(WHITE);
			}
		}
	}

	@FXML
	void givenUp(ActionEvent event) {
		textOthello.setVisible(true);
		textOthello.setText("Je hebt opgegeven");
		Button button = (Button) event.getSource();
		button.setVisible(false);
		rematchButton.setVisible(true);
	}


	@FXML
	void handleButtonR_AI(ActionEvent event) throws IOException{
		Stage stage;
		Parent root;
		stage = (Stage) textOthello.getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("View/OthelloView.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void handleButtonR_PVP(ActionEvent event) throws IOException {
		Stage stage;
		Parent root;
		stage = (Stage) textOthello.getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("View/OthelloView.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void handleButtonR_SP(ActionEvent event) throws IOException{
		Stage stage;
		Parent root;
		stage = (Stage) Sp_R_Button.getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("View/OthelloView.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
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