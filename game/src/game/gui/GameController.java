package game.gui;


import game.engine.Battle;

import game.engine.lanes.Lane;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
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


//public void BeginDrag() {
//	PiercingCannonShop.setOnDragDetected(event -> {
//		  // Existing drag setup logic (data, transfer mode, etc.)
//		  
//		  // Create a new ImageView for the custom cursor
//		  Image cursorImage = PiercingCannonShop.getImage();
//		// cursorImage. // Adjust size as needed
//		//  cursorImage.setFitHeight(32); // Adjust size as needed
//	//	  cursorImage.setOpacity(.5);
//
//		  // Set the scene cursor to the custom image view
//		  Scene scene = PiercingCannonShop.getScene(); 
//		  scene.setCursor(new ImageCursor(cursorImage));
//		  
//		  event.consume();
//		});
//}
//public void handleDrop() {
//		  Scene scene = PiercingCannonShop.getScene(); 
//		  scene.setCursor(Cursor.DEFAULT);
//  
//	}
//
//}
	public void BeginDrag() {
		  PiercingCannonShop.setOnDragDetected(event -> {
		    // Existing drag setup logic (data, transfer mode, etc.)

		    // Create a new ImageView for the custom cursor
		    Image cursorImage = PiercingCannonShop.getImage(); // Assuming PiercingCannonShop has getImage() to get the image

		    // Set cursor size (optional)
		    //cursorImage.setFitHeight(32); // Adjust size as needed
		    //cursorImage.setFitWidth(32);  // Adjust size as needed

		    // Set cursor opacity (optional)
		    //cursorImage.setOpacity(0.5); // Adjust opacity as needed

		    // Get the scene (assuming scene is accessible)
		    Scene scene = PiercingCannonShop.getScene(); 

		    // Store the original cursor;

		    // Set the scene cursor to the custom image view
		    scene.setCursor(new ImageCursor(cursorImage));
		  });

		  // Drag exited handler to reset cursor
		  PiercingCannonShop.setOnDragDropped(event2 -> {
		     Scene scene = PiercingCannonShop.getScene();
			scene.setCursor(originalCursor);
		    }
		  );
		}
	
	public void initiateDragWithImageCursor() {
		  // Get the scene (assuming scene is accessible)
		  Scene scene = PiercingCannonShop.getScene();

		  // Store the original cursor (assuming scene is accessible)
		  Cursor originalCursor = scene.getCursor();

		  // Create a new ImageView for the custom cursor
		  Image cursorImage = PiercingCannonShop.getImage(); // Assuming PiercingCannonShop has getImage() to get the image

		  // Set cursor size (optional)
		  //cursorImage.setFitHeight(32); // Adjust size as needed
		  //cursorImage.setFitWidth(32);  // Adjust size as needed

		  // Set cursor opacity (optional)
		  //cursorImage.setOpacity(0.5); // Adjust opacity as needed

		  // Create a temporary final variable to hold the cursor (alternative approach)
		  final Cursor temporaryCursor = new ImageCursor(cursorImage);

		  // Set event filters to change cursor on drag detected and reset on drag dropped
		  PiercingCannonShop.addEventFilter(DragEvent.ANY, event -> {
		    scene.setCursor(temporaryCursor);
		  });

		  PiercingCannonShop.addEventFilter(DragEvent.DRAG_DROPPED, event -> {
		    scene.setCursor(originalCursor);
		  });

		  // Optionally remove event filters after drag operation completes (cleaner approach)
		  PiercingCannonShop.setOnDragDropped(droppedEvent -> {
//		    PiercingCannonShop.removeremoveEventFilter(DragEvent.ANY);
	//	    PiercingCannonShop.removeEventFilter(DragEvent.DRAG_DROPPED);
		  });
		}
		public void handleDrop(DragEvent event) {
		  Scene scene = PiercingCannonShop.getScene(); 
		  scene.setCursor(Cursor.DEFAULT); // Reset to default cursor (optional)
		}}




