package game.engine.lanes;

import java.util.*;

import game.engine.base.Wall;
import game.engine.titans.Titan;
import game.engine.weapons.Weapon;

public class Lane {
	private Wall laneWall;
	private int dangerLevel;
	private PriorityQueue<Titan> titans;
	private ArrayList<Weapon> weapons;

	public Lane(Wall laneWall) {
		this.laneWall = laneWall;
		this.dangerLevel = 0;

	}

	public Wall getLaneWall() {
		return laneWall;
	}

	public PriorityQueue<Titan> getTitans() {
		return titans;
	}

	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}

	public int getDangerLevel() {
		return dangerLevel;
	}

	public void setDangerLevel(int dangerLevel) {
		this.dangerLevel = dangerLevel;
	}
}
