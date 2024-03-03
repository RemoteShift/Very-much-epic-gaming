package game.engine.titans;

public abstract class Titan implements Comparable {
	private final int baseHealth;
	private int currentHealth;
	private final int baseDamage;
	private final int heightInMeters;
	private int distanceFromBase;
	private int speed;
	private final int resourcesValue;
	private final int dangerLevel;

	// there are 8 instance variables, but the constructor only takes 7 inputs, this
	// is because the current health is intially
	// set to the base health in the constructor.
	public Titan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase, int speed,
			int resourcesValue, int dangerLevel) {
		this.baseHealth = baseHealth;
		this.currentHealth = baseHealth;
		this.baseDamage = baseDamage;
		this.heightInMeters = heightInMeters;
		this.distanceFromBase = distanceFromBase;
		this.speed = speed;
		this.resourcesValue = resourcesValue;
		this.dangerLevel = dangerLevel;
	}

	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	public void setDistanceFromBase(int distanceFromBase) {
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

	public int getDistanceFromBase() {
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

	// i didnt really understand what was asked of me here
	// public int compareTo(Titan o){
	//
	// }

}
