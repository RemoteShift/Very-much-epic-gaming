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

public class Battle
{
	private static final int[][] PHASES_APPROACHING_TITANS =
	{
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
			int initialResourcesPerLane) throws IOException
	{
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

	

	public int getNumberOfTurns()
	{
		return numberOfTurns;
	}

	public void setNumberOfTurns(int numberOfTurns)
	{
		this.numberOfTurns = numberOfTurns;
	}

	public int getResourcesGathered()
	{
		return resourcesGathered;
	}

	public void setResourcesGathered(int resourcesGathered)
	{
		this.resourcesGathered = resourcesGathered;
	}

	public BattlePhase getBattlePhase()
	{
		return battlePhase;
	}

	public void setBattlePhase(BattlePhase battlePhase)
	{
		this.battlePhase = battlePhase;
	}

	public int getNumberOfTitansPerTurn()
	{
		return numberOfTitansPerTurn;
	}

	public void setNumberOfTitansPerTurn(int numberOfTitansPerTurn)
	{
		this.numberOfTitansPerTurn = numberOfTitansPerTurn;
	}

	public int getScore()
	{
		return score;
	}

	public void setScore(int score)
	{
		this.score = score;
	}

	public int getTitanSpawnDistance()
	{
		return titanSpawnDistance;
	}

	public void setTitanSpawnDistance(int titanSpawnDistance)
	{
		this.titanSpawnDistance = titanSpawnDistance;
	}

	public WeaponFactory getWeaponFactory()
	{
		return weaponFactory;
	}

	public HashMap<Integer, TitanRegistry> getTitansArchives()
	{
		return titansArchives;
	}

	public ArrayList<Titan> getApproachingTitans()
	{
		return approachingTitans;
	}

	public PriorityQueue<Lane> getLanes()
	{
		return lanes;
	}

	public ArrayList<Lane> getOriginalLanes()
	{
		return originalLanes;
	}

	private void initializeLanes(int numOfLanes)
	{
		for (int i = 0; i < numOfLanes; i++)
		{
			Wall w = new Wall(WALL_BASE_HEALTH);
			Lane l = new Lane(w);

			this.getOriginalLanes().add(l);
			this.getLanes().add(l);
		}
	}

//Milestone 2 game setup methods:

private void refillApproachingTitans(){
	int[][] temp = PHASES_APPROACHING_TITANS;
	int j;
	switch(getBattlePhase()){
		case EARLY: j=0;
		case INTENSE:j=1;
		case GRUMBLING:j=2;
		default: j=0;
		//based on what battlephase it is, i am setting the j variable, which represents which row we will be taking from the PHASES_APPROACHING_TITANS 2D array. so if its EARLY,j=0 and we will use the first row to fill the ApproachingTitans ArrayList. 
	}
		for(int i=0;i<temp.length;i++){
			switch(temp[i][j]){
				case 1:getApproachingTitans().add( new PureTitan(i, i, i, i, i, i, i)); //idk what to put for the parameters
				case 2:getApproachingTitans().add( new AbnormalTitan(i, i, i, i, i, i, i)); //idk what to put for the parameters
				case 3:getApproachingTitans().add( new ArmoredTitan(i, i, i, i, i, i, i)); //idk what to put for the parameters
				case 4:getApproachingTitans().add( new ColossalTitan(i, i, i, i, i, i, i)); //idk what to put for the parameters
				//the PHASES_APPROACHING_TITANS is a 2D array of the titans' ID numbers, so what i did here was map every titan ID to its designated titan using a switch case and stored it in the ArrayList ApproachingTitans. i keep doing this using a for loop.			
			}
		}
		//this method is slightly hardcoded, tell me if u think i should change anything.
}
void purchaseWeapon(int weaponCode, Lane lane) throws InsufficientResourcesException, InvalidLaneException{
	if(isLaneLost() == true){ //isLaneLost() was not implemented yet at the time of me writing this code
	throw new InvalidLaneException ("Cannot Place here; lane is lost");
	break;
}

	switch(weaponCode){
		case 1: Lane.addWeapon(new PiercingCannon(10));//idk what to put for parameter
		buyWeapon(resourcesGathered,1); throw new InsufficientResourcesException (weaponCode);
		case 2: Lane.addWeapon(new SniperCannon(10));//idk what to put for parameter
		buyWeapon(resourcesGathered,2); throw new InsufficientResourcesException (weaponCode);
		case 3: Lane.addWeapon(new VolleySpreadCannon(10, weaponCode, weaponCode));//idk what to put for parameters
		buyWeapon(resourcesGathered,3); throw new InsufficientResourcesException (weaponCode);
		case 4: Lane.addWeapon(new WallTrap(10));//idk what to put for parameter
		buyWeapon(resourcesGathered,4); throw new InsufficientResourcesException (weaponCode);
		 //buyWeapon was not implemented yet at the time of me writing this code
	}
//this code might have a few issues i will come back to later
}
void passTurn(){
	return;
	// :3
}
private void addTurnTitansToLane(){
		for (int i=0;i<numberOfTitansPerTurn;i++){
			if (this.approachingTitans==null)
				refillApproachingTitans();
		Titan Titan_To_Be_Added = approachingTitans.remove(i);
		((getLanes().peek()).getTitans()).add(Titan_To_Be_Added);
		//updateLanesDangerLevels(); this hasnt been implemented yet at the time of me coding. 
//getlanes() gets a priority queue of the current active lanes, so when we peek, it returns the lane with the least danger level. then the getTitans gets us the arraylist of the titans on that lane, which we then add to it the first element in the approachingTitans arraylist.
		}

}
private void moveTitans(){
	PriorityQueue<Lane> TempLanes = new PriorityQueue<>();
	PriorityQueue<Titan> Titans_On_Lane = new PriorityQueue<>();
	//PriorityQueue<Titan> TempTitans = new PriorityQueue<>();
	PriorityQueue<Lane> Lanes = getLanes(); 
	Titan Current_Titan;
	for(int i=0;i<Lanes.size();i++){
		Titans_On_Lane=(Lanes.peek()).getTitans();
		//looping through the PriorityQueue of Lanes, getting the priorityQueue of titans on each of the lanes.
		TempLanes.add(Lanes.poll());
		//poll is a method that is the same as peek, but it removes the element afterwards. Since this is a PriorityQueue and you can only get the highest priority object in the queue, i poll every element after getting the Titans_On_Lane so that i can iterate through the PriorityQueue.
		for (int j=0;j<Titans_On_Lane.size();j++){
			Current_Titan=(Titans_On_Lane.poll()); 
			Current_Titan.setDistance(Current_Titan.getDistance()-Current_Titan.getSpeed());
			if (Current_Titan instanceof ColossalTitan)
			Current_Titan.setSpeed(Current_Titan.getSpeed()+1);
			// now that i have the PriorityQueue of all the titans on the lane, i get each titan individually and set their distance according to their speed.
	}
}
}
private int performWeaponsAttacks(){
	PriorityQueue<Lane> TempLanes = new PriorityQueue<>();
	ArrayList<Weapon> Weapons_On_Lane = new ArrayList<>();
	PriorityQueue<Titan> Titans_On_Lane = new PriorityQueue<>();
	PriorityQueue<Lane> Lanes = getLanes(); 
	Weapon Current_Weapon;
	Titan Current_Titan;
	for(int i=0;i<Lanes.size();i++){
		Weapons_On_Lane=((Lanes.peek())).getWeapons(); //looping through the PriorityQueue of Lanes, getting the ArrayList of weapons on each of the lanes.
		Titans_On_Lane=(Lanes.peek()).getTitans(); //looping through the PriorityQueue of Lanes, getting the priorityQueue of titans on each of the lanes.
		Lanes.poll();
		for(int j=0;j<Weapons_On_Lane.size();j++){
			Current_Weapon=(Weapons_On_Lane.get(j));
			for(int k=0;k<Titans_On_Lane.size();k++){
			Current_Titan=(Titans_On_Lane.poll());
			Current_Titan.setCurrentHealth(Current_Titan.getCurrentHealth()-Current_Weapon.getDamage());
		}
//for each lane(first loop), we take the jth weapon in the arrayList (2nd loop), and subtract all the titans' health by the damage of that weapon(3rd loop). we keep doing this for all the TIANS, from all the WEAPONS, in all the LANES.
	}
//i am not proud of this code
}}}
