package game.gui;
	
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.scene.control.Alert.AlertType;


public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			Pane root = FXMLLoader.load(getClass().getResource("Main Menu.fxml"));
			
			Scene scene1 = new Scene(root);
			String css1 = getClass().getResource("application.css").toExternalForm();
			
			
			stage.setResizable(false);
			scene1.getStylesheets().add(css1);
			stage.setScene(scene1);
			stage.show();
			
			stage.setOnCloseRequest(event -> 
			{
				event.consume();
				Close(stage);
			});
			
			stage.setFullScreen(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void Close(Stage stage)
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(stage);
		alert.setTitle("Are you sure?");
		alert.setHeaderText("Are you sure that you want to close this game?");
		alert.setContentText("Your actions have consequences.");
		
		if(alert.showAndWait().get() == ButtonType.OK)
			stage.close();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
