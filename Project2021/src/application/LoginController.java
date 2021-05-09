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
  
    
    
    
    public void Login(ActionEvent event) throws Exception
    {
    	if(txtusername.getText().equals("user") && txtpassword.getText().equals("pass"))
    	{ main.Sample();
    	} else { 
    		lblstatus.setText("login fail");}
    	}
}
