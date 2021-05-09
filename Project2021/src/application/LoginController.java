/*
* Author: cy.andersen
* Date: May 7, 2021
* Description:
* Contrôleur pour programme de login
*/
package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
  
    //pour ouvrir la fenetre de Sample avec l=informations de l'usager
    public void Login(ActionEvent event) throws Exception
    {
    	if(txtusername.getText().equals("username") && txtpassword.getText().equals("password"))
    	{ main.Sample();
    	} else { 
    		lblstatus.setText("login fail");}
    	}
}
