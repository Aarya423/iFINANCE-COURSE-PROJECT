package se2203b.assignments.ifinance;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Abdelkader
 */
public class AlertController implements Initializable {

    
    
    @FXML public Label error;
   
    public void setAlertText(String text) {
        // set text from another class
        error.setText(text);
    } 

    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }    
    
}
