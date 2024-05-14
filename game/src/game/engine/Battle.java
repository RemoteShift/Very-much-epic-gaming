package game.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;

import game.engine.base.Wall;
import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.Titan;
import game.engine.titans.TitanRegistry;
import game.engine.weapons.factory.FactoryResponse;
import game.engine.weapons.factory.WeaponFactory;
import game.gui.GameController;

public class Battle {
	private static final int[][] PHASES_APPROACHING_TITANS = {
			{ 1, 1, 1, 2, 1, 3, 4 },
			{ 2, 2, 2, 1, 3, 3, 4 },
			{ 4, 4, 4, 4, 4, 4, 4 }
	};
	private static final int WALL_BASE_HEALTH = 10000;

	private int numberOfTurns;
	private int resourcesGathered;
	private BattlePhase battlePhase;
	private int numberOfTitansPerTurn; // initially equals to 1
	private int score; // Number of Enemies Killed
	private int titanSpawnDistance;
	private final WeaponFactory weaponFactory;
	private final HashMap<Integer, TitanRegistry> titansArchives;
	private final ArrayList<Titan> approachingTitans; // treated as a Queue
	private final PriorityQueue<Lane> lanes;
	private final ArrayList<Lane> originalLanes;
	
	private GameController gameController;
	
	public Battle(int numberOfTurns, int score, int titanSpawnDistance, int initialNumOfLanes,
			int initialResourcesPerLane) throws IOException {
		super();
		this.numberOfTurns = numberOfTurns;
		this.battlePhase = BattlePhase.EARLY;
		this.numberOfTitansPerTurn = 1;
		this.score = score;
		this.titanSpawnDistance = titanSpawnDistance;
		this.resourcesGathered = initialResourcesPerLane * initialNumOfLanes;
		this.weaponFactory = new WeaponFactory();
		this.titansArchives = DataLoader.readTitanRegistry();
		this.approachingTitans = new ArrayList<Titan>();
		this.lanes = new PriorityQueue<>();
		this.originalLanes = new ArrayList<>();
		this.initializeLanes(initialNumOfLanes);
		this.gameController = GameController.instance;
	}

	public int getNumberOfTurns() {
		return numberOfTurns;
	}

	public void setNumberOfTurns(int numberOfTurns) {
		this.numberOfTurns = numberOfTurns;
	}

	public int getResourcesGathered() {
		return resourcesGathered;
	}

	public void setResourcesGathered(int resourcesGathered) {
		this.resourcesGathered = resourcesGathered;
	}

	public BattlePhase getBattlePhase() {
		return battlePhase;
	}

	public void setBattlePhase(BattlePhase battlePhase) {
		this.battlePhase = battlePhase;
	}

	public int getNumberOfTitansPerTurn() {
		return numberOfTitansPerTurn;
	}

	public void setNumberOfTitansPerTurn(int numberOfTitansPerTurn) {
		this.numberOfTitansPerTurn = numberOfTitansPerTurn;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getTitanSpawnDistance() {
		return titanSpawnDistance;
	}

	public void setTitanSpawnDistance(int titanSpawnDistance) {
		this.titanSpawnDistance = titanSpawnDistance;
	}

	public WeaponFactory getWeaponFactory() {
		return weaponFactory;
	}

	public HashMap<Integer, TitanRegistry> getTitansArchives() {
		return titansArchives;
	}

	public ArrayList<Titan> getApproachingTitans() {
		return approachingTitans;
	}

	public PriorityQueue<Lane> getLanes() {
		return lanes;
	}

	public ArrayList<Lane> getOriginalLanes() {
		return originalLanes;
	}

	private void initializeLanes(int numOfLanes) {
		for (int i = 0; i < numOfLanes; i++) {
			Wall w = new Wall(WALL_BASE_HEALTH);
			Lane l = new Lane(w);

			this.getOriginalLanes().add(l);
			this.getLanes().add(l);
		}
	}

	// Milestone 2 game setup methods:

	public void refillApproachingTitans() {
		approachingTitans.clear();
		TitanRegistry tempTitanRegistry;
		switch (battlePhase) {
			case EARLY:
				for (int tempTitan : PHASES_APPROACHING_TITANS[0]) {
					tempTitanRegistry = titansArchives.get(tempTitan);
					approachingTitans.add(tempTitanRegistry.spawnTitan(titanSpawnDistance));
				}
				break;
			case INTENSE:
				for (int tempTitan : PHASES_APPROACHING_TITANS[1]) {
					tempTitanRegistry = titansArchives.get(tempTitan);
					approachingTitans.add(tempTitanRegistry.spawnTitan(titanSpawnDistance));
				}
				break;
			case GRUMBLING:
				for (int tempTitan : PHASES_APPROACHING_TITANS[2]) {
					tempTitanRegistry = titansArchives.get(tempTitan);
					approachingTitans.add(tempTitanRegistry.spawnTitan(titanSpawnDistance));
				}
				break;
			default:
				return;
		}
	}

	public void purchaseWeapon(int weaponCode, Lane lane)
			throws InsufficientResourcesException, InvalidLaneException{
		if (!lanes.contains(lane)) {
			throw new InvalidLaneException("Cannot Place here; lane is lost");
		}

		FactoryResponse bruh = weaponFactory.buyWeapon(resourcesGathered, weaponCode);
		lane.addWeapon(bruh.getWeapon());
		setResourcesGathered(bruh.getRemainingResources());
	}

	void passTurn(){
		moveTitans();
		performWeaponsAttacks();
		performTitansAttacks();
		addTurnTitansToLane();
		updateLanesDangerLevels();
		finalizeTurns();
	}

	private void addTurnTitansToLane(){
		for (int i = 0; i < numberOfTitansPerTurn; i++) {
			if (this.approachingTitans.isEmpty())
				refillApproachingTitans();
			Titan Titan_To_Be_Added = approachingTitans.remove(0);
			Lane tempLane = getLanes().peek();
			tempLane.addTitan(Titan_To_Be_Added);
			gameController.AddTitanToLane(tempLane, Titan_To_Be_Added);
			tempLane.updateLaneDangerLevel();
		}
	}

	private void moveTitans() {
		for (Lane lane : lanes) {
			if (lane.isLaneLost())
				continue;

			for (Titan titan : lane.getTitans())
			{
				if(titan.getDistance() > 0)
				{
					titan.move();
					gameController.moveTitans(titan, lane);
				}	
			}
				
		}
	}

	private int performWeaponsAttacks() {
		int resources = 0;

		for (Lane lane : lanes) {
			resources += lane.performLaneWeaponsAttacks();
		}
		resourcesGathered += resources;
		score += resources;
		return resources;
	}

	private int performTitansAttacks() {
		int resources = 0;
		int size = lanes.size();
		Stack<Lane> tempLanes = new Stack<>();
		for (int i = 0; i < size; i++) {
			resources += lanes.peek().performLaneTitansAttacks();
			if (!lanes.peek().getLaneWall().isDefeated())
				tempLanes.add(lanes.peek());
			else
				gameController.killLane(lanes.peek());
			lanes.poll();
		}

		lanes.addAll(tempLanes);

		return resources;
	}

	private void updateLanesDangerLevels() {
		Stack<Lane> tempLanes = new Stack<>();
		int size = lanes.size();
		for (int i = 0; i < size; i++) {
			tempLanes.add(lanes.poll());
			tempLanes.peek().updateLaneDangerLevel();
		}

		lanes.addAll(tempLanes);
	}

	private void finalizeTurns() {
		numberOfTurns++;

		if (getNumberOfTurns() < 15)
			setBattlePhase(BattlePhase.EARLY);
		else if (getNumberOfTurns() < 30)
			setBattlePhase(BattlePhase.INTENSE);
		else {
			if (getNumberOfTurns() > 30 && getNumberOfTurns() % 5 == 0)
				numberOfTitansPerTurn *= 2;
			setBattlePhase(BattlePhase.GRUMBLING);
		}
	}

	public void performTurn(){
		moveTitans();
		performWeaponsAttacks();
		performTitansAttacks();
		addTurnTitansToLane();
		updateLanesDangerLevels();
		finalizeTurns();
	}

	public boolean isGameOver() {
		int counter = 0;
		for (Lane lane : getLanes()) {
			if (lane.isLaneLost())
				counter++;
		}
		if (getLanes().size() == counter)
			return true;
		else
			return false;
	}
}
