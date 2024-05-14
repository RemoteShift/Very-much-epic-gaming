package game.engine.lanes;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

import game.engine.base.Wall;
import game.engine.titans.Titan;
import game.engine.weapons.Weapon;

public class Lane implements Comparable<Lane> {
	private final Wall laneWall;
	private int dangerLevel;
	private final PriorityQueue<Titan> titans;
	private final ArrayList<Weapon> weapons;

	public Lane(Wall laneWall) {
		super();
		this.laneWall = laneWall;
		this.dangerLevel = 0;
		this.titans = new PriorityQueue<>();
		this.weapons = new ArrayList<>();
	}

	public Wall getLaneWall() {
		return this.laneWall;
	}

	public int getDangerLevel() {
		return this.dangerLevel;
	}

	public void setDangerLevel(int dangerLevel) {
		this.dangerLevel = dangerLevel;
	}

	public PriorityQueue<Titan> getTitans() {
		return this.titans;
	}

	public ArrayList<Weapon> getWeapons() {
		return this.weapons;
	}

	@Override
	public int compareTo(Lane o) {
		return this.dangerLevel - o.dangerLevel;
	}

	// Milestone 2:

	public void addTitan(Titan titan) {
		titans.add(titan);
	}

	public void addWeapon(Weapon weapon) {
		weapons.add(weapon);
	}

	public void moveLaneTitans() {
		Stack<Titan> tempTitan = new Stack<>();
		int size = titans.size();
		for (int i = 0; i < size; i++) {
			tempTitan.add(titans.poll());
			if (!tempTitan.peek().hasReachedTarget())
				tempTitan.peek().move();
		}

		titans.addAll(tempTitan);
	}

	public int performLaneTitansAttacks() {
		int resources = 0;
		for (Titan titan : titans) {
			if (titan.hasReachedTarget())
				resources += titan.attack(laneWall);
		}
		return resources;
	}

	public int performLaneWeaponsAttacks() {
		int resources = 0;
		for (Weapon weapon : weapons) {
			resources += weapon.turnAttack(titans, this);
		}
		return resources;
	}

	public boolean isLaneLost() {
		return laneWall.getCurrentHealth() <= 0;
	}

	public void updateLaneDangerLevel() {
		int dangerLevels = 0;

		for (Titan titan : titans) {
			dangerLevels += titan.getDangerLevel();
		}

		setDangerLevel(dangerLevels);
	}
}
