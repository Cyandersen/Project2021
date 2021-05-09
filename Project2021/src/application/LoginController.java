/*
* Author: cy.andersen
* Date: May 7, 2021
* Description:
* Contrôleur pour programme de login
*/
package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class LoginController {

    @FXML
    private TextField txtpassword;

    @FXML
    private Button bttnlogin;

    @FXML
    private Label lblstatus;

    @FXML
    private TextField txtusername;

    private Main main;
    
    public void setMain(Main main)
    {
    	this.main=main;
    }
    
    public void Login(ActionEvent event) throws Exception
    {
    	if(txtusername.getText().equals("Toronto") && txtpassword.getText().equals("1700"))
    	{ main.Sample();
    	} else { 
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Mauvais identifiant");
			alert.setHeaderText("Mauvaise connexion utilisée");
			alert.setContentText("Indice: Capitale de l'Ontario et 200 + 600 + 900 \n");
			alert.showAndWait();
    		
    	}
}
}
