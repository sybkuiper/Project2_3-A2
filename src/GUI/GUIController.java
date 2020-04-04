package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	    @FXML private Label label;
	    @FXML private Button nextbutton;
	    @FXML private Label counterlabel;


	    // assign text field text to label on button click
	    public void handleButton() {
	        String text = field.getText();
	        label.setText(text);
	        field.clear();
	    }
	    
	    
	    @FXML
	    void handleButtonTTT_SP(ActionEvent event) throws IOException {
		   	BorderPane pane = FXMLLoader.load(getClass().getResource("SpTicTacView.fxml"));
	       	rPane.getChildren().setAll(pane);
	    }	
	    
	    @FXML
	    void gotomenuscreen(ActionEvent event) throws IOException {
	    	BorderPane pane = FXMLLoader.load(getClass().getResource("MenuWindowView.fxml"));
	    	rootPane.getChildren().setAll(pane);
	    	this.rPane = pane;
	    }
	    

		@FXML
		void showPlayer(ActionEvent event) throws IOException {
	    	Image image;
	    	char player = 'X';
			//TODO make sure we can check who plays with which symbol
//			if(player == 'X'){
//				image = new Image(getClass().getResourceAsStream("/img/cross.png"));
//			}else{
//				image = new Image(getClass().getResourceAsStream("/img/circle.png"));
//			}
			Button button = (Button) event.getSource();
			button.setDisable(true);
			button.setOpacity(1.0);
//			button.setGraphic(new ImageView(image));
			counter++;
			System.out.print(counter);

		}


	    @FXML
	    void handleButton(ActionEvent event) {
	        String text = field.getText();
	        label.setText(text);
	        field.clear();
	    }
	    
	    @FXML
	    void handleButtonR_AI(ActionEvent event) {

	    }

	    @FXML
	    void handleButtonR_PVP(ActionEvent event) {

	    }

	    @FXML
	    void handleButtonR_SP(ActionEvent event) {

	    }

	    @FXML
	    void handleButtonTTT_AI(ActionEvent event) {

	    }

	    @FXML
	    void handleButtonTTT_PVP(ActionEvent event) {

	    }
	    
    	
	    	

	    


	    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

    
}

