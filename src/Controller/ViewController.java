package Controller;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import Controller.NetworkController;
import Model.Game;
import Model.Reversi;
import Model.TicTacToe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static javafx.scene.paint.Color.*;


public class ViewController implements Initializable {
	//Is the ID for both the board of Tic Tac Toe and Reversi
	@FXML GridPane board;

	//Is the name the user is currently using
	@FXML public TextField field;

	//Gives whose turn it is to move
	@FXML public Text beurt;

	//retuns the number of disc each player has in Reversi
	@FXML private Text numWhiteDisc, numBlackDisc;

	//returns which error has been made while logging in
	@FXML private Label loginError;

	//Checks whether the player wants to play online
	@FXML private CheckBox online;

	//Returns the values needed for the player to play online
	@FXML private TextField IP, port;

	//The only buttons that have a use now are Sp_T_Button and Sp_R_Button the rest is not being used
	@FXML private Button Sp_T_Button, Sp_R_Button;

	//Initialises the networkController
	private NetworkController networkController;

	//Returns the players that are currently online
	//Only functions when online is selected
	private List<String> onlinePlayers;

	//Holds the name of the player
	public String playerName;

	//Holds which game is currently being played
	//default = null
	private Game game;

	//Used to update the online players
	ObservableList<String> onlineList = FXCollections.observableArrayList();

	//TODO ik kan er niet uitkomen waar deze variabele voor is
	@FXML private ListView<String> onlineListView;


	/**
	 * Refreshes the current online list
	 */
	@FXML
	public void refreshOnlineList() {
		onlineList.clear();
		networkController.getPlayerList();
	}

	/**
	 * Returns the current onlineListView
	 * @return the current onlineListView
	 */
	public ListView<String> getOnlineListView() {
		return onlineListView;
	}

	/**
	 * When you accept a challenge to Tic Tac Toe this method will
	 * start a game between you and the challenger
	 * @param event the current event
	 * @throws IOException
	 */
	@FXML
	public void onlineInviteTButton(ActionEvent event) throws IOException {
		String selectedPlayer=onlineListView.getSelectionModel().getSelectedItem();
		networkController.challenge(selectedPlayer,"Tic-tac-toe");
//		Stage stage = (Stage) Sp_T_Button.getScene().getWindow();
//		changeView(stage,"/View/NewTTView.fxml");
	}

	/**
	 * When you accept a challenge this method starts a game
	 * between you and the challenger
	 * @param event the current event
	 * @throws IOException
	 */
	@FXML
	public void onlineInviteRButton(ActionEvent event) throws IOException {
		String selectedPlayer=onlineListView.getSelectionModel().getSelectedItem();
		networkController.challenge(selectedPlayer, "Reversi");
//		Stage stage = (Stage) Sp_R_Button.getScene().getWindow();
//		changeView(stage,"../View/NewOthelloView.fxml");
	}

	/**
	 * When you click the button to start a game of Tic Tac Toe this method starts the view and
	 * initialses the board for Tic Tac Toe
	 * @param event the current event
	 * @throws IOException
	 */
	@FXML
	void handleButtonTTT_SP(ActionEvent event) throws IOException {
		Stage stage;
		stage = (Stage) Sp_T_Button.getScene().getWindow();
		changeView(stage,"/View/NewTTView.fxml");
		if(!online.isSelected()) {
			new TicTacToe(3, 3, playerName, this, false);
			game.printGameState();
		}
	}

	/**
	 * Sets the game to the current game being played
	 * @param game the game which is being played
	 */
	public void setGame(Game game) {
		this.game = game;
	}

	/**
	 * initializes the game for both Reversi and Tic Tac Toe
	 * @param gameType the game that is being played
	 * @param playerOne the playerOne of this game
	 * @param playerTwo the playerTwo of this game
	 * @throws IOException Checks wheter the game is being intitialized properly
	 */
	void initializeGame(String gameType, String playerOne, String playerTwo) throws IOException {
		if(gameType.equals("Reversi")){
			System.out.println("Stage 2 online initialization..");
			game = new Reversi(8,8, playerOne,playerTwo, this, true);
		} else if (gameType.equals("Tic-tac-toe")){
			System.out.println("Stage 2 online initialization..");
			game = new TicTacToe(3,3, playerOne,playerTwo, this, true);
		}
	}

	/**
	 * Returns the game
	 * @return the current game type
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Returns wheter the players wants to play online or not
	 * @return if the player wants to play online
	 */
	public CheckBox getOnline() {
		return online;
	}

	/**
	 * Shows whose turn it is to make a move
	 * @param beurt whose turn it is
	 */
	public void setBeurt(String beurt) {
		this.beurt.setText(beurt);
	}

	/**
	 * Starts the menuscreen and checks wheter the login is viable
	 * @param event the current event
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@FXML
	void gotomenuscreen(ActionEvent event) throws IOException, InterruptedException {
		Stage stage;
		playerName = field.getText();
		stage = (Stage)((Node) event.getSource()).getScene().getWindow();
			//(Stage) menu.getScene().getWindow();
		//Checks whether the login name is empty
		//https://stackoverflow.com/questions/32866937/how-to-check-if-textfield-is-empty
		if(playerName.trim().isEmpty()){
			System.out.println("Dont allow login");
			loginError.setText("Voer alstublieft een gebruikersnaam in alvorens in te loggen.");
			loginError.setVisible(true);
		} else {
			System.out.println("Allow login for : " + playerName);
			//Check whether online is selected
			if(online.isSelected()) {
				String ip_address = IP.getText();
				String port_number = port.getText();
				//Checks wheter IP and/or Port are left empty
				if(ip_address.trim().isEmpty() || port_number.trim().isEmpty()) {
					System.out.println("Dont allow login, network error");
					loginError.setText("Voer alstublieft correcte netwerkgegevens in alvorens in te loggen.");
					loginError.setVisible(true);
				} else {
					changeView(stage,"/View/NewMenuWindowView.fxml");
					//initializes the networkController ig there isn't one already
					if(networkController == null) {
						networkController = new NetworkController(this, field.getText(), ip_address, Integer.parseInt(port_number));
						networkController.start();
					}
					//updateOnlinePlayers();
				}
			}
			loginError.setVisible(false);
			System.out.println(field.getText());
			//If the player does not want to play online starts the offline menuView
			if(!online.isSelected()) {
				changeView(stage,"/View/NewMenuWindowViewOffline.fxml");
			}
		}
	}


	/**
	 * Updates the list for online players
	 * @param onlinePlayers The list of players that is currently online
	 * @throws IOException checks whether retrieving data went correct
	 */
    public void updateOnlinePlayers(List<String> onlinePlayers) throws IOException {
		getOnlineListView().getItems().addAll(onlinePlayers);
    }

	/**
	 * Changes the view to the selected game
	 * @param gameType the game currently being played
	 * @throws IOException checks whether the game is being loaded correctly
	 */
    public void changeView(String gameType) throws IOException {
		if(gameType.equals("Reversi")){
			Stage stage = (Stage) Sp_R_Button.getScene().getWindow();
			changeView(stage,"/View/NewOthelloView.fxml");
		} else if (gameType.equals("Tic-tac-toe")){
			Stage stage = (Stage) Sp_T_Button.getScene().getWindow();
			changeView(stage,"/View/NewTTView.fxml");
		}
	}

	/**
	 * Changes the view to the given view
	 * @param stage the current stage
	 * @param path the path of the view that should be loaded
	 * @throws IOException Checks whether the stage is being loaded correctly
	 */
	private void changeView(Stage stage, String path) throws IOException {
		Parent root;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(path));
		loader.setController(this);
		root = loader.load();
		Scene scene = new Scene(root, 830, 600);
		stage.setScene(scene);
		stage.setMinHeight(600);
		stage.setMinWidth(830);
		stage.show();
	}

	/**
	 * Sets the list for current online players
	 * @param onlinePlayers the list of players currently online
	 */
	public void setOnlinePlayers(List<String> onlinePlayers) {
		this.onlinePlayers = onlinePlayers;
	}

	/**
	 * Shows the IP and Port textField if the online checkBox is selected
	 * @param event the current event
	 * @throws IOException checks whether the event being done correctly
	 */
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

	/**
	 * Checks what grid is clicked and returns the row and collum of the clicked grid
	 * source: https://stackoverflow.com/questions/50012463/how-can-i-click-a-gridpane-cell-and-have-it-perform-an-action
	 * @param event the current event
	 */
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
			System.out.println("Clicked: " + collIndex + ", " + rowIndex);
			//System.out.println((rowIndex*8 + collIndex));
		}
		//Checks whether the current game is Reversi
		if(game instanceof Reversi) {
			if (clickedNode instanceof Circle) {
				Circle clickedNodes = (Circle) clickedNode;
				clickedNodes.setOpacity(1);
				game.makeMove(translateTileToInt(clickedNode));
			}
		}
		//Checks whether the current game is Tic-Tac-Toe
		if(game instanceof TicTacToe) {
			if(clickedNode instanceof javafx.scene.shape.Rectangle) {
				javafx.scene.shape.Rectangle rekt = (Rectangle) clickedNode;
				game.makeMove(translateTileToInt(clickedNode));
			}
		}
	}

	/**
	 * Updates the grids to show the move that has been made
	 * @param move The value of the move that has been made
	 * @param color the color of the current player
	 */
	public void updateGrid(int move, String color){
		//Checks wheter the current game is Tic-Tac-Toe
		if(game instanceof TicTacToe){
			int row = move / 3;
			int column = move % 3;
			Node tile = getTile(row, column);
			if(tile instanceof Rectangle){
				System.out.println(color);
				if(color.equals("X")){
					Image image = null;
					image = new Image(getClass().getResourceAsStream("/Img/cross.png"));
					((Rectangle) tile).setOpacity(1);
					((Rectangle) tile).setFill(new ImagePattern(image));
				} else {
					Image image = null;
					image = new Image(getClass().getResourceAsStream("/Img/circle.png"));
					((Rectangle) tile).setOpacity(1);
					((Rectangle) tile).setFill(new ImagePattern(image));
				}
				tile.setDisable(true);
			}
		}
		//Checks whether the current game is Reversi
		if(game instanceof Reversi) {
			int row = move / 8;
			int column = move % 8;

			Node tile = getTile(row, column);
			if (tile instanceof Circle) {
				tile.setOpacity(1);
				if (color.equals("B")) {
					((Circle) tile).setFill(BLACK);
					((Circle) tile).setStroke(BLACK);
				} else if (color.equals("W")) {
					((Circle) tile).setFill(WHITE);
					((Circle) tile).setStroke(BLACK);
				} else if (color.equals("legalMove")) {
					((Circle) tile).setFill(Color.web("#aa6fc9"));
					((Circle) tile).setStroke(BLACK);
				}
				if(online.isSelected()) {
					tile.setDisable(true);
				}
			}
		}
	}

	/**
	 * Performs an action on one or more tiles
	 * @param action specifies the action that needs to be performed, which is evaluated by an if statement.
	 */
	public void performActionOnTile(String action){
		System.out.println(board);
		for(Node node : board.getChildren()){
			if(game instanceof TicTacToe){
				if(action.equals("disableAllTiles")){
					node.setDisable(true);
				}
			}
			if(game instanceof Reversi) {
				if (node instanceof Circle) {
					if (action.equals("disableAllTiles")){
						//when playing AI vs AI, or when a player is not allowed to move
						node.setDisable(true);
					} else if (action.equals("hideLegalMoves")) {
						if (((Circle) node).getFill().equals(Color.web("#aa6fc9"))) {
							node.setOpacity(0);
							((Circle) node).setFill(RED); //reset to default
						}
					}
				}
			}
		}
	}
	/**
	 * Performs an action on one or more tiles
	 * @param action specifies the action that needs to be performed, which is evaluated by an if statement.
	 * @param color specifies the color the action needs to be performed on.
	 */
	public void performActionOnTile(String action, Color color){
		int colorCount = 0;
		LinkedHashSet<Integer> legalMoves = null;
		if(action.equals("disableIllegalMoves")) {
			if (color.equals(WHITE)) {
				legalMoves = game.getLegalMoves(game.getGameBoard(), "W");
			} else if (color.equals(BLACK)) {
				legalMoves = game.getLegalMoves(game.getGameBoard(), "B");
			}
		}
		for(Node node : board.getChildren()){
			if(game instanceof TicTacToe){

			}
			if(game instanceof Reversi) {
				if (node instanceof Circle) {
					if(action.equals("disableIllegalMoves")){
						if(legalMoves != null && !legalMoves.contains(translateTileToInt(node))){
							node.setDisable(true);
						} else {
							node.setDisable(false);
							updateGrid(translateTileToInt(node), "legalMove");
						}
					} else if (action.equals("updateTileAmounts")){
						if(((Circle) node).getFill().equals(color)){
							colorCount++;
						}
					}
				}
			}
		}
		if (action.equals("updateTileAmounts")){
			if(color.equals(BLACK)){
				numBlackDisc.setText(String.valueOf(colorCount));
			} else if(color.equals(WHITE)){
				numWhiteDisc.setText(String.valueOf(colorCount));
			}
		}
	}

	/**
	 * Returns the number of black discs as an int
	 * @return numBlackDisc
	 */
	public Integer getNumBlackDisc() {
		return Integer.parseInt(numBlackDisc.getText());
	}

	/**
	 * Returns the number of white discs as an int
	 * @return numWhiteDisc
	 */
	public Integer getNumWhiteDisc(){
		return Integer.parseInt(numWhiteDisc.getText());
	}

	/**
	 * Translate the clicked tile to an int
	 * @param node TODO ik weet niet wat hier moet
	 * @return the number of the grid which has been clicked
	 */
	public Integer translateTileToInt(Node node){
		Integer rowIndex = GridPane.getRowIndex(node);
		Integer columnIndex = GridPane.getColumnIndex(node);
		if(columnIndex == null){
			columnIndex = 0;
		}
		if(rowIndex == null){
			rowIndex = 0;
		}
		if(game instanceof Reversi) {
			return (rowIndex * 8 + columnIndex);
		} else if (game instanceof TicTacToe) {
			return (rowIndex * 3 + columnIndex);
		}
		return null;
	}

	/**
	 * Returns TODO ik weet niet wat ik hiet moet zetten
	 * @param row the row of the current move
	 * @param column the collum of the current move
	 * @return
	 */
	public Node getTile (final int row, final int column) {
		Node result = null;
		ObservableList<Node> childrens = board.getChildren();
		System.out.println(childrens.size());
		for (Node node : childrens) {
			Integer rowIndex = GridPane.getRowIndex(node);
			Integer columnIndex = GridPane.getColumnIndex(node);
			if(columnIndex == null){columnIndex = 0;}
			if(rowIndex	== null) {rowIndex = 0;}

				if (rowIndex == row && columnIndex == column) {
					result = node;
					break;
				}
		}

		return result;
	}

	/**
	 * Starts a game of Reversi
	 * @param event the current event
	 * @throws IOException checks whether the event is done correctly
	 */
	@FXML
	void handleButtonR_SP(ActionEvent event) throws IOException{
		Stage stage = (Stage) Sp_R_Button.getScene().getWindow();
		changeView(stage,"/View/NewOthelloView.fxml");
		if(!online.isSelected()) {
			new Reversi(8, 8, playerName, this, false);
			game.printGameState();
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
}