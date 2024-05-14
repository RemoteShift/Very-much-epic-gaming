package game.gui;


import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

import game.engine.Battle;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class GameController{

	public static GameController instance;
	
	public static Battle battle;
	private static Scene mainScene = MainMenuController.mainScene;
	private int numLanes;
	private static boolean done = false;
	private HashMap<FlowPane, Integer> lanesHashMap = new HashMap<FlowPane, Integer>();
	private Lane[] lanes;
	private boolean changeHeight = false;
	
	@FXML
	Label turns, phase, score, resource;
	@FXML
	GridPane gridPane;
	@FXML
	ImageView PiercingCannonShop;
	@FXML
	ImageView SniperCannonShop;
	@FXML
	ImageView VolleySpreadCannonShop;
	@FXML
	ImageView WallTrapShop;
	@FXML
	Label exception;
	@FXML
	ImageView Temporary_Titan_Storage;
	
	public GameController()
	{
		
	}
	
	public static GameController getInstance()
	{
		if(instance == null)
			instance = new GameController();
		
		return instance;
	}
	
	
	public void initialize() throws IOException {
		if(battle != null)
		{
			PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
			FadeTransition fade = new FadeTransition(Duration.seconds(1.5), exception);
			fade.setFromValue(1.0);
			fade.setToValue(0.0);
			
			BackgroundFill backGroundFill = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);
			exception.setBackground(new Background(backGroundFill));
			
			pause.setOnFinished(event -> 
			{
				fade.play();
				exception.setDisable(true);
			});
			
			gridPane.setGridLinesVisible(false);
			gridPane.getStyleClass().add("mygridStyle");
			numLanes = battle.getLanes().size();
			lanes = new Lane[numLanes];
			
			gridPane.getColumnConstraints().clear();
			
			for (int i = 0; i < 23; i++) {
	            ColumnConstraints colConst = new ColumnConstraints();
	            colConst.setPercentWidth(100.0 / 23);
	            gridPane.getColumnConstraints().add(colConst);
	        }
			
			for (int i = 0; i < numLanes ; i++) {
	            RowConstraints rowConst = new RowConstraints();
	            rowConst.setPercentHeight(100.0 / numLanes);
	            gridPane.getRowConstraints().add(rowConst);
	            lanes[i] = (Lane)battle.getLanes().toArray()[i];
	        }
			
			
			for(int row = 0; row < numLanes; row++)
			{	
				
				FlowPane flowPane = new FlowPane();
				flowPane.setBorder(Border.stroke(Color.WHITE));
				
				for(int col = 22; col >= 1; col--)
				{
					if(col == 1)
					{
						Rectangle rectangle = new Rectangle(75, 230*3/numLanes, Color.BURLYWOOD);
						gridPane.add(rectangle, 1, row);
						gridPane.add(flowPane, 1, row);
					}
					else
					{
						Rectangle rectangle = new Rectangle(75, 230*3/numLanes, Color.DIMGRAY);
						gridPane.add(rectangle, col, row);
						FlowPane flowPaner = new FlowPane();
						flowPaner.setBorder(Border.stroke(Color.WHITE));
						flowPaner.setMaxWidth(75);
						flowPaner.setMaxHeight(230*3/numLanes);
						gridPane.add(flowPaner, col, row);
					}
					
				}
				
				flowPane.setMaxWidth(75);
				flowPane.setMaxHeight(230*3/numLanes);
				double initHeight = 230*3/numLanes;
				
				lanesHashMap.put(flowPane, row);
				flowPane.setOnDragOver(event -> {
					event.acceptTransferModes(TransferMode.ANY);
					if(!done)
					{
						Dragboard db = event.getDragboard();
						flowPane.getChildren().add(new ImageView(db.getImage()));
						done = true;
					}
				});
				flowPane.setOnDragExited(event ->
				{
					flowPane.getChildren().remove(flowPane.getChildren().size()-1);
					changeHeight = false;
					done = false;
				});
				flowPane.setOnDragDropped(event -> {
					Dragboard db = event.getDragboard();
					try {
						battle.purchaseWeapon(Integer.parseInt(db.getString()), 
								lanes[lanesHashMap.get(flowPane)]);
						ImageView imageView = new ImageView(db.getImage());
						flowPane.getChildren().add(imageView);
						
						if(flowPane.getHeight() > initHeight)
						{
							changeHeight = true;
						}
						
						if(changeHeight)
						{
							ObservableList<Node> children = flowPane.getChildren();
							
							for(Node node : children)
							{
								ImageView imageViewer = (ImageView)node;
								imageViewer.setPreserveRatio(true);
								imageViewer.setFitHeight(initHeight/children.size());
							}
						}
						
						resource.setText("Resources: " + battle.getResourcesGathered());
					} catch (InsufficientResourcesException e) {
						e = new InsufficientResourcesException(battle.getResourcesGathered());
						exception.setText(e.getMessage());
						exception.setOpacity(1.0);
						pause.play();
					} catch (InvalidLaneException e)
					{
						e = new InvalidLaneException();
						exception.setText(e.getMessage());
						exception.setOpacity(1.0);
						pause.play();
					}
					event.setDropCompleted(true);
				});
				
				//set every time turn is performed
				Label wallHealth = new Label("    Hp: " + lanes[row].getLaneWall().getCurrentHealth() + 
						"\nDanger Level: " + lanes[row].getDangerLevel());
                wallHealth.setFont(new Font("Arial", (numLanes > 3)? 17 : 25));
                wallHealth.setMinWidth(Region.USE_PREF_SIZE);
                wallHealth.setMinHeight(Region.USE_PREF_SIZE);
                wallHealth.setMaxHeight((numLanes > 3)? 100 : 180);
                wallHealth.setAlignment(Pos.TOP_CENTER);
                wallHealth.setRotate(-90.0);
                wallHealth.setDisable(true);
				wallHealth.setOpacity(1);
				
				
				gridPane.add(wallHealth, 0, row);
				
				/*battle.getLanes().remove(lanes[0]);
				killLane(lanes[0]);*/
			}
			
			
			turns.setText("Turn: " + battle.getNumberOfTurns());
			phase.setText(battle.getBattlePhase().toString());
			score.setText("Score: " + battle.getScore());
			resource.setText("Resources: " + battle.getResourcesGathered());
		}
		//this is for price and damage to display
		Tooltip PiercingCannonTooltip = new Tooltip("Name: Anti-Titan Shell \n Type: Piercing Cannon \n Price: 25 \n Damage: 10 ");
		PiercingCannonTooltip.setShowDelay(Duration.millis(20));
		Tooltip SniperCannonTooltip = new Tooltip("Name: LongRangeSpear \n Type: Sniper Cannon \n Price: 25 \n Damage: 35 ");
		SniperCannonTooltip.setShowDelay(Duration.millis(20));
		Tooltip VolleySpreadCannonTooltip = new Tooltip("Name:  VolleySpread Cannon \n Type: WallSpread Cannon \n Price: 100 \n Damage: 5 ");
		VolleySpreadCannonTooltip.setShowDelay(Duration.millis(20));
		Tooltip WallTrapTooltip = new Tooltip("Name: Wall Trap \n Type: Proximity Trap \n Price: 75 \n Damage: 100 ");
		WallTrapTooltip.setShowDelay(Duration.millis(20));
		Tooltip.install(PiercingCannonShop,PiercingCannonTooltip);
		Tooltip.install(SniperCannonShop,SniperCannonTooltip);
		Tooltip.install(VolleySpreadCannonShop,VolleySpreadCannonTooltip);
		Tooltip.install(WallTrapShop,WallTrapTooltip);
		
		
		PiercingCannonShop.setOnDragDetected(event -> {
			
			Dragboard db = PiercingCannonShop.startDragAndDrop(TransferMode.ANY);
			ClipboardContent content = new ClipboardContent();
			content.putString("1");
			content.putImage(PiercingCannonShop.getImage());
			db.setContent(content);
		    ImageView cursorImage = new ImageView(PiercingCannonShop.getImage());
		    cursorImage.setFitHeight(32);
		    cursorImage.setFitWidth(32);
		    cursorImage.setOpacity(0.2); 
		    db.setDragView(cursorImage.getImage());
		  });
		
		SniperCannonShop.setOnDragDetected(event -> {
			
			Dragboard db = SniperCannonShop.startDragAndDrop(TransferMode.ANY);
			ClipboardContent content = new ClipboardContent();
			content.putString("2");
			content.putImage(SniperCannonShop.getImage());
			db.setContent(content);
		    ImageView cursorImage = new ImageView(SniperCannonShop.getImage());
		    cursorImage.setFitHeight(32);
		    cursorImage.setFitWidth(32);
		    cursorImage.setOpacity(0.2); 
		    db.setDragView(cursorImage.getImage());
		  });
		
		VolleySpreadCannonShop.setOnDragDetected(event -> {
			
			Dragboard db = VolleySpreadCannonShop.startDragAndDrop(TransferMode.ANY);
			ClipboardContent content = new ClipboardContent();
			content.putString("3");
			content.putImage(VolleySpreadCannonShop.getImage());
			db.setContent(content);
		    ImageView cursorImage = new ImageView(VolleySpreadCannonShop.getImage());
		    cursorImage.setFitHeight(32);
		    cursorImage.setFitWidth(32);
		    cursorImage.setOpacity(0.2); 
		    db.setDragView(cursorImage.getImage());
		  });
		
		WallTrapShop.setOnDragDetected(event -> {
			
			Dragboard db = WallTrapShop.startDragAndDrop(TransferMode.ANY);
			ClipboardContent content = new ClipboardContent();
			content.putString("4");
			content.putImage(WallTrapShop.getImage());
			db.setContent(content);
		    ImageView cursorImage = new ImageView(WallTrapShop.getImage());
		    cursorImage.setFitHeight(32);
		    cursorImage.setFitWidth(32);
		    cursorImage.setOpacity(0.2);
		    db.setDragView(cursorImage.getImage());
		  });
	}
	
	public void killLane(Lane lane)
	{
		int i;
		for(i = 0; i < lanes.length; i++)
		{
			if(lanes[i] == lane)
				break;
		}
		for(int j = 0; j < 23; j++)
		{
			for(Node node : getNodesFromGridPane(gridPane, j, i))
			{
				node.setOpacity(0.2);
			}
		}
	}
	
	private Stack<Node> getNodesFromGridPane(GridPane gridPane, int col, int row) {
	    Stack<Node> nodes = new Stack<Node>();
		
		for (Node node : gridPane.getChildren()) {
	        if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
	            nodes.add(node);
	        }
	    }
		if(nodes.isEmpty())
			return null;
		else
			return nodes;
	}
}




