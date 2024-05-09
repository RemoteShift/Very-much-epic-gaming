package game.gui;

import java.io.IOException;

import game.engine.Battle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainMenuController {

	private Stage stage;
	private Scene scene;
	private AnchorPane root;
	
	@FXML
	Button howToPlay;
	
	@FXML
	RadioButton easy, hard;
	
	public void HowToPlay(ActionEvent e) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("HowToPlay.fxml"));
		scene = ((Node)e.getSource()).getScene();
		stage = (Stage)scene.getWindow();
			
		scene.setRoot(root);
	}
	
	public void BackToMenu(ActionEvent e) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("Main Menu.fxml"));
		scene = ((Node)e.getSource()).getScene();
		stage = (Stage)scene.getWindow();
		
		scene.setRoot(root);
	}
	
	public void Play(ActionEvent e) throws IOException
	{
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("ChooseDiff.fxml"));
		Scene scene = new Scene(root, 600, 420);
		
		stage.initOwner(((Node)e.getSource()).getScene().getWindow());
		
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setResizable(false);
		
		stage.setScene(scene);
		stage.show();
	}
	
	public void Close(ActionEvent e)
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(((Node)e.getSource()).getScene().getWindow());
		alert.getDialogPane().getScene().getStylesheets().clear();
		alert.setTitle("Are you sure?");
		alert.setHeaderText("Are you sure that you want to close this game?");
		alert.setContentText("Your actions have consequences.");
		
		if(alert.showAndWait().get() == ButtonType.OK)
			((Stage)((Node)e.getSource()).getScene().getWindow()).close();
	}
	
	public void StartGame(ActionEvent e)
	{
		Battle battle;
		
		if(easy.isSelected())
		{
			
		}
	}
}
