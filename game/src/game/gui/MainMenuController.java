package game.gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainMenuController {

	private Stage stage;
	private Scene scene;
	private Parent root;
	
	
	@FXML
	Label title;
	
	@FXML
	Button howToPlay;
	
	public void HowToPlay(ActionEvent e) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("HowToPlay.fxml"));
		scene = new Scene(root);
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	public void BackToMenu(ActionEvent e) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("Main Menu.fxml"));
		scene = new Scene(root);
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	public void Play(ActionEvent e)
	{
		
	}
	
	public void Close(ActionEvent e)
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Are you sure?");
		alert.setHeaderText("Are you sure that you want to close this game?");
		alert.setContentText("Your actions have consequences.");
		
		if(alert.showAndWait().get() == ButtonType.OK)
			((Stage)((Node)e.getSource()).getScene().getWindow()).close();
	}
	
}
