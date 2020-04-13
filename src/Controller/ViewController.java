package Controller;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import Controller.NetworkController;
import Model.Game;
import Model.Reversi;
import Model.TicTacToe;
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
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static javafx.scene.paint.Color.*;


public class ViewController implements Initializable {
	private int counter = 0;
	@FXML private BorderPane rootPane;
	@FXML private Button test;
	private BorderPane rPane;
	@FXML private AnchorPane peopleOnline;
	@FXML GridPane board;
	@FXML public TextField field;
	@FXML private Text textOthello;
	@FXML private Text tWins, tLosses, tDraws, rWins, rLosses, rDraws;
	@FXML private CheckBox online;
	@FXML private Label label;
	@FXML private TextField IP, port;
	@FXML private Button nextbutton,menubutton,menu,xbutton,rematchButton;
	@FXML private Label counterlabel;
	@FXML private Button Sp_T_Button,AI_T_Button,Mp_T_Button,Sp_R_Button,Mp_R_Button;
	private NetworkController networkController;
	private List<String> onlinePlayers;
	public String playerName;
	private Game game;
	private ViewController controller;

	@FXML
	void handleButtonTTT_SP(ActionEvent event) throws IOException {
		new TicTacToe(3,3,playerName, this, false);
		Stage stage;
		stage = (Stage) Sp_T_Button.getScene().getWindow();
		changeView(stage,"../View/NewTTView.fxml");
	}

	public void setGame(Game game) {
		this.game = game;
	}

	void initializeGame(String gameType, String playerOne){
		if(game != null){
			game = null;
		}
		if(gameType == "Reversi"){
			game = new Reversi(8,8, playerOne, this, true);
		} else if (gameType == "Tic-tac-toe"){
			game = new TicTacToe(3,3, playerOne, this, true);
		}
	}

	public Game getGame() {
		return game;
	}

	public CheckBox getOnline() {
		return online;
	}

	@FXML
	void gotomenuscreen(ActionEvent event) throws IOException, InterruptedException {
		Stage stage;

		if(game == null) {
			playerName = field.getText();
		}
		if(online.isSelected()){
			System.out.println(field.getText());
			networkController = new NetworkController(this, field.getText(),IP.getText(),Integer.parseInt(port.getText()));
			updateOnlinePlayers();
		}
		stage = (Stage) menu.getScene().getWindow();
		//root = FXMLLoader.load(getClass().getResource("../View/MenuWindowView.fxml"));
		changeView(stage,"../View/NewMenuWindowView.fxml");
		updateWinsLosses();
	}

	@FXML
	void updateWinsLosses() throws IOException{
		String[] temp = new String[5];
		try {
			File file = new File("src/Data/Records");
			if(file.exists()) {
				Scanner reader = new Scanner(file);
				while (reader.hasNextLine()) {
					String data = reader.nextLine();
					temp = data.split(",");
				}
				reader.close();
			}
		} catch(FileNotFoundException e) {
			System.out.println("an error occured");
			e.printStackTrace();
		}
		tWins.setText(temp[0]);
		tLosses.setText(temp[1]);
		tDraws.setText(temp[2]);
		rWins.setText(temp[3]);
		rLosses.setText(temp[4]);
		rDraws.setText(temp[5]);
	}

	@FXML
	void updateOnlinePlayers(){
		HBox box = new HBox();
		for(String person : onlinePlayers) {
			Text t = new Text(person);

			box.getChildren().add(t);
		}
		peopleOnline.getChildren().add(box);
	}

	private void changeView(Stage stage, String path) throws IOException {
		Parent root;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(path));
		loader.setController(this);
		root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void setOnlinePlayers(List<String> onlinePlayers) {
		this.onlinePlayers = onlinePlayers;
	}

	@FXML
	void showPlayer(ActionEvent event) throws IOException {
		Image image = null;
		char player = 'O';
		//TODO make sure we can check who plays with which symbol
		if(player == 'X') {
			image = new Image(getClass().getResourceAsStream("../Img/cross.png"));
		}else{
			image = new Image(getClass().getResourceAsStream("../Img/circle.png"));
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
		Integer test = Integer.parseInt(button.getText());
		System.out.println(playerName);
		System.out.println(game);
		game.makeMove(test);
	}

	@FXML
	public void showPlayer(Integer move){
		Image image = null;
		char player = 'X';
		//TODO make sure we can check who plays with which symbol
		if(player == 'X') {
			image = new Image(getClass().getResourceAsStream("../Img/cross.png"));
		}else{
			image = new Image(getClass().getResourceAsStream("../Img/circle.png"));
		}
		test.setDisable(true);
		test.setOpacity(1.0);
		if(image != null) {
			ImageView imageTemp = new ImageView(image);
			imageTemp.setFitHeight(80);
			imageTemp.setFitWidth(80);
			test.setGraphic(imageTemp);
		}
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
		if(game instanceof Reversi) {
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
		if(game instanceof TicTacToe) {
			if(clickedNode instanceof Rectangle) {
				Image image = null;
				Rectangle rekt = (Rectangle) clickedNode;
				char shape = 'X';
				if(shape == 'X'){
					image = new Image(getClass().getResourceAsStream("../Img/cross.png"));
				}else{
					image = new Image(getClass().getResourceAsStream("../Img/circle.png"));
				}
				if (image != null){
					rekt.setFill(new ImagePattern(image));
				}
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
		root = FXMLLoader.load(getClass().getResource("../View/MenuWindowView.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void handleButtonR_SP(ActionEvent event) throws IOException{
		new Reversi(8,8,playerName, this, false);
		Stage stage;
		stage = (Stage) Sp_R_Button.getScene().getWindow();
		changeView(stage,"../View/NewOthelloView.fxml");
	}

	void alertGameState(String state){

	}

	@FXML
	void handleButtonTTT_AI(ActionEvent event) {
		initializeGame("Tic-tac-toe", field.getText());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}


	public void setController(ViewController controller) {
		this.controller = controller;
	}
}