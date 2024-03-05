package game.engine.titans;

import game.engine.interfaces.Attackee;
import game.engine.interfaces.Attacker;
import game.engine.interfaces.Mobil;

public abstract class Titan implements Comparable<Titan>, Mobil, Attacker, Attackee {
	private final int baseHealth;
	private int currentHealth;
	private final int baseDamage;
	private final int heightInMeters;
	private int distanceFromBase;
	private int speed;
	private final int resourcesValue;
	private final int dangerLevel;

	public Titan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase, int speed,
			int resourcesValue, int dangerLevel) {
		this.baseHealth = Math.max(0, baseHealth);
		this.currentHealth = this.baseHealth;
		this.baseDamage = baseDamage;
		this.heightInMeters = heightInMeters;
		this.distanceFromBase = distanceFromBase;
		this.speed = speed;
		this.resourcesValue = resourcesValue;
		this.dangerLevel = dangerLevel;
	}

	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = Math.max(0, currentHealth);
	}

	public void setDistance(int distanceFromBase) {
		this.distanceFromBase = distanceFromBase;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getBaseHealth() {
		return baseHealth;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public int getBaseDamage() {
		return baseDamage;
	}

	public int getHeightInMeters() {
		return heightInMeters;
	}

	public int getDistance() {
		return distanceFromBase;
	}

	public int getSpeed() {
		return speed;
	}

	public int getResourcesValue() {
		return resourcesValue;
	}

	public int getDangerLevel() {
		return dangerLevel;
	}

	@Override
	public int compareTo(Titan o) {
		if (this.distanceFromBase > o.distanceFromBase)
			return 1;
		else if (this.distanceFromBase == o.distanceFromBase)
			return 0;
		else
			return -1;
	}
}
