package game.gui;


import game.engine.Battle;
import game.engine.lanes.Lane;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameController{

	public static GameController instance;
	
	public static Battle battle;
	private static Scene mainScene = MainMenuController.mainScene;
	private int numLanes;
	
	@FXML
	Label turns, phase, score, resource;
	@FXML
	GridPane gridPane;
	
	public GameController()
	{
		
	}
	
	public static GameController getInstance()
	{
		if(instance == null)
			instance = new GameController();
		
		return instance;
	}
	
	
	public void initialize() {
		if(battle != null)
		{
			gridPane.setGridLinesVisible(false);
			gridPane.getStyleClass().add("mygridStyle");
			numLanes = battle.getLanes().size();
			
			gridPane.getColumnConstraints().clear();
			
			for (int i = 0; i < 21; i++) {
	            ColumnConstraints colConst = new ColumnConstraints();
	            colConst.setPercentWidth(100.0 / 21);
	            gridPane.getColumnConstraints().add(colConst);
	        }
			
			for (int i = 0; i < numLanes ; i++) {
	            RowConstraints rowConst = new RowConstraints();
	            rowConst.setPercentHeight(100.0 / numLanes);
	            gridPane.getRowConstraints().add(rowConst);
	        }
			
			
			for(int row = 0; row < numLanes; row++)
			{		
				for(int col = 1; col < 21; col++)
				{
					Rectangle rectangle = new Rectangle(85, 230*3/numLanes, Color.DIMGRAY);
					gridPane.add(rectangle, col, row);
				}
				Rectangle rectangle = new Rectangle(90, 230*3/numLanes, Color.BURLYWOOD);	
				gridPane.add(rectangle, 0, row);
			}
			
			
			turns.setText("Turn: " + battle.getNumberOfTurns());
			phase.setText(battle.getBattlePhase().toString());
			score.setText("Score: " + battle.getScore());
			resource.setText("Resources: " + battle.getResourcesGathered());
		}
	}
	
	public void killLane(int i)
	{
		for(int j = 0; j < 21; j++)
		{
			Rectangle rectangle = (Rectangle)getNodeFromGridPane(gridPane, j, i);
			rectangle.setOpacity(0.2);
		}
	}
	
	private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
	    for (Node node : gridPane.getChildren()) {
	        if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
	            return node;
	        }
	    }
	    return null;
	}
	
	
	
	
}
