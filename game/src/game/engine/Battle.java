package game.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import game.engine.base.Wall;
import game.engine.lanes.*;
import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.AbnormalTitan;
import game.engine.titans.ArmoredTitan;
import game.engine.titans.ColossalTitan;
import game.engine.titans.PureTitan;
import game.engine.titans.Titan;
import game.engine.titans.TitanRegistry;
import game.engine.weapons.PiercingCannon;
import game.engine.weapons.SniperCannon;
import game.engine.weapons.VolleySpreadCannon;
import game.engine.weapons.WallTrap;
import game.engine.weapons.Weapon;
import game.engine.weapons.factory.WeaponFactory;

public class Battle {
	private static final int[][] PHASES_APPROACHING_TITANS = {
			{ 1, 1, 1, 2, 1, 3, 4 },
			{ 2, 2, 2, 1, 3, 3, 4 },
			{ 4, 4, 4, 4, 4, 4, 4 }
	}; // order of the types of titans (codes) during each phase
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

	private void refillApproachingTitans() throws IOException {
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

	void purchaseWeapon(int weaponCode, Lane lane)
			throws InsufficientResourcesException, InvalidLaneException, IOException {
		if (lane.isLaneLost() == true) {
			throw new InvalidLaneException("Cannot Place here; lane is lost");
		}

		switch (weaponCode) {
			case 1:
				weaponFactory.buyWeapon(resourcesGathered, 1);
				lane.addWeapon(weaponFactory.getWeaponShop().get(1).buildWeapon());
			case 2:
				weaponFactory.buyWeapon(resourcesGathered, 2);
				lane.addWeapon(weaponFactory.getWeaponShop().get(2).buildWeapon());
			case 3:
				weaponFactory.buyWeapon(resourcesGathered, 3);
				lane.addWeapon(weaponFactory.getWeaponShop().get(3).buildWeapon());
			case 4:
				weaponFactory.buyWeapon(resourcesGathered, 4);
				lane.addWeapon(weaponFactory.getWeaponShop().get(4).buildWeapon());
		}
	}

	void passTurn() {
		return; // idk i'll return later
	}

	private void addTurnTitansToLane() throws IOException {
		for (int i = 0; i < numberOfTitansPerTurn; i++) {
			if (this.approachingTitans.isEmpty())
				refillApproachingTitans();
			Titan Titan_To_Be_Added = approachingTitans.remove(0);
			Lane tempLane = getLanes().peek();
			tempLane.addTitan(Titan_To_Be_Added);
			tempLane.updateLaneDangerLevel();
		}
	}

	private void moveTitans() {
		for (Lane lane : lanes) {
			if (lane.isLaneLost())
				continue;

			for (Titan titan : lane.getTitans())
				titan.move();
		}
	}

	private int performWeaponsAttacks() {
		int resources = 0;

		for (Lane lane : lanes) {
			if (lane.isLaneLost())
				continue;

			for (Weapon weapon : lane.getWeapons()) {
				resources += weapon.turnAttack(lane.getTitans());
			}
		}

		return resources;
	}

	private int performTitansAttacks(){
	int resources = 0;
	for (Lane lane: getLanes()){
		if(lane.isLaneLost())
			continue;
		for(Titan titan : lane.getTitans()){
			if(titan.hasReachedTarget())
			resources+=titan.attack(lane.getLaneWall());
		}
	}
	return resources;
	}

	private void updateLanesDangerLevels(){
		for(Lane lane: getLanes()){
			if(!lane.isLaneLost())
			lane.updateLaneDangerLevel();
		}
	}
	private void finalizeTurns(){
		numberOfTurns++;
	if(getNumberOfTurns()<15)
	setBattlePhase(BattlePhase.EARLY);
	else if(getNumberOfTurns()<30)
	setBattlePhase(BattlePhase.INTENSE);
	else if(getNumberOfTurns()%5==0){
	if(getNumberOfTurns()>30){
	setBattlePhase(BattlePhase.GRUMBLING);
	numberOfTitansPerTurn*=2;
	}
	else
		setBattlePhase(BattlePhase.GRUMBLING);
		}
	}
	private void performTurn(){

		if(){
			for( Lane lane : )
		}
	}

}

