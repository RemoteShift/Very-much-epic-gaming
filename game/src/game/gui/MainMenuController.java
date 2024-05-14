package game.gui;

import java.io.IOException;

import game.engine.Battle;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainMenuController{

	public static Scene mainScene;
	private AnchorPane root;
	
	@FXML
	Button howToPlay;
	
	@FXML
	RadioButton easy, hard;
	
	@FXML
	Label exception;
	
	
	public void HowToPlay(ActionEvent e) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("HowToPlay.fxml"));
		mainScene = ((Node)e.getSource()).getScene();
			
		mainScene.setRoot(root);
	}
	
	public void BackToMenu(ActionEvent e) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("Main Menu.fxml"));
		
		mainScene.setRoot(root);
	}
	
	public void Play(ActionEvent e) throws IOException
	{
		mainScene = ((Node)e.getSource()).getScene();
		((Node)e.getSource()).setDisable(true);
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("ChooseDiff.fxml"));
		Scene scene = new Scene(root, 600, 420);
		
		stage.setOnCloseRequest(event -> ((Node)e.getSource()).setDisable(false));
		
		stage.setTitle("Choose");
		stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
		stage.initOwner(mainScene.getWindow());
		
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
	
	public void StartGame(ActionEvent e) throws IOException
	{
		
		Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		
		PauseTransition pause = new PauseTransition(Duration.seconds(2.5));
		FadeTransition fade = new FadeTransition(Duration.seconds(2.0), exception);
		fade.setFromValue(1.0);
		fade.setToValue(0.0);
		
		BackgroundFill backGroundFill = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);
		exception.setBackground(new Background(backGroundFill));
		
		pause.setOnFinished(event -> 
		{
			fade.play();
			exception.setDisable(true);
		});
		
		Battle battle;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Game.fxml"));
		Parent root = loader.load();
		GameController gameController = loader.getController();
		GameController.instance = gameController;
		
		if(easy.isSelected())
		{
			battle = new Battle(0, 0, 100, 3, 250);
			GameController.battle = battle;
			gameController.initialize();
			stage.close();
			mainScene.setRoot(root);
		}
		else if(hard.isSelected())
		{
			battle = new Battle(0, 0, 100, 5, 125);
			GameController.battle = battle;
			gameController.initialize();
			stage.close();
			mainScene.setRoot(root);
		}
		else
		{
			exception.setOpacity(1.0);
			pause.play();
		}
	}
}
