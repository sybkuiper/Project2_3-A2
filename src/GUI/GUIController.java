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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;



public class GUIController implements Initializable {

        @FXML private BorderPane rootPane;
	    @FXML private TextField field;
	    @FXML private Label label;
	    @FXML private Button nextbutton;
	    @FXML private Label counterlabel;

	    private GUI main;

	    // connect main class to controller
	    public void setMain(GUI main) {
	        this.main = main; 
	    }

	    // assign text field text to label on button click
	    public void handleButton() {
	        String text = field.getText();
	        label.setText(text);
	        field.clear();
	    }
	    
	    
	    @FXML
	    void handleButtonTTT_SP(ActionEvent event) throws IOException {
		   BorderPane pane = FXMLLoader.load(getClass().getResource("SpTicTacView.fxml"));
	       rootPane.getChildren().setAll(pane);
	    }	
	    
	    @FXML
	    void gotomenuscreen(ActionEvent event) throws IOException {
	    	BorderPane pane = FXMLLoader.load(getClass().getResource("MenuWindowView.fxml"));
	    	rootPane.getChildren().setAll(pane);
	    }
	    

		@FXML
		void showPlayer(ActionEvent event) throws IOException {
	    	
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

