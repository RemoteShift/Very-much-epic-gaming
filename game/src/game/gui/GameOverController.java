package game.gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GameOverController {

	private MainMenuController mainMenuController = MainMenuController.instance;
	
	@FXML
	Label score, turns;
	
	public void initializeGameOver(int score, int turns)
	{
		this.score.setText("Score: " + score);
		this.turns.setText("Turns: " + turns);
	}
	
	public void BackToMenu() throws IOException
	{
		mainMenuController.BackToMenu(null);
	}
}
