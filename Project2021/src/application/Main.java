/*
* Author: cy.andersen
* Date: May 7, 2021
* Description:
* Main pour le programme
*/
package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	private Stage primaryStage;
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage=primaryStage;
		Login();
	}

	//pour charger la premiere fenetre "Login"
	public void Login()
	{
		try {
			FXMLLoader loader=new FXMLLoader(Main.class.getResource("Login.fxml"));
			AnchorPane pane=loader.load();
			Scene scene = new Scene(pane);
			LoginController LoginController=loader.getController();
			LoginController.setMain(this);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Identification");
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//pour charger la deuxieme fenetre "Sample"
	public void Sample()
	{
		try {
			FXMLLoader loader=new FXMLLoader(Main.class.getResource("Sample.fxml"));
			AnchorPane pane=loader.load();
			Scene scene = new Scene(pane);
			SampleController SampleController=loader.getController();
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Identification");
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}

