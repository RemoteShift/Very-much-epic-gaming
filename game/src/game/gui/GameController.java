package game.gui;


import java.awt.TextField;

import game.engine.Battle;

import game.engine.lanes.Lane;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;

public class GameController{

	public static GameController instance;
	
	public static Battle battle;
	private static Scene mainScene = MainMenuController.mainScene;
	private int numLanes;
	
	@FXML
	Label turns, phase, score, resource;
	@FXML
	GridPane gridPane;
	@FXML
	ImageView PiercingCannonShop;
	
	private Cursor originalCursor = Cursor.DEFAULT;
	
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
		//this is for price and damage to display
		Tooltip PiercingCannonTooltip = new Tooltip("Name: Piercing Cannon//nCost:idk"+"//n"+"  g");
		PiercingCannonTooltip.setShowDelay(Duration.millis(20));
		Tooltip.install(PiercingCannonShop,PiercingCannonTooltip); 
		
		
		PiercingCannonShop.setOnDragDetected(event -> {
		    ImageView cursorImage = new ImageView(PiercingCannonShop.getImage());
		    
		    cursorImage.setFitHeight(32);
		    cursorImage.setFitWidth(32);
		    cursorImage.setOpacity(0.5); 
		    
		    Scene scene = PiercingCannonShop.getScene(); 

		    scene.setCursor(new ImageCursor(cursorImage.getImage()));
		    PiercingCannonShop.requestFocus();
		  });
		PiercingCannonShop.setOnMouseReleased(event -> {
			PiercingCannonShop.getScene().setCursor(Cursor.DEFAULT);
		});

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
	public void buy() {
	getNodeFromGridPane(gridPane,1,1).setOnDragDropped(event2 -> {
		Node focusOwner = mainScene.getFocusOwner();
		if(focusOwner.equals(PiercingCannonShop))
			System.out.println("fuck yeah");
	});}
}




