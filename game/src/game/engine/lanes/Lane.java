package game.engine.lanes;

import java.util.*;

import game.engine.base.Wall;
import game.engine.titans.Titan;
import game.engine.weapons.Weapon;

public class Lane implements Comparable<Object> {
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

	@Override
	public int compareTo(Object o) {
		Lane o1 = (Lane) o;

		if (this.dangerLevel < o1.dangerLevel)
			return 1;
		else if (this.dangerLevel == o1.dangerLevel)
			return 0;
		else
			return -1;
	}

}
