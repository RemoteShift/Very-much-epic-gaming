package game.engine.titans;

public class TitanRegistry {
	final int code;
	final int baseHealth;
	final int baseDamage;
	final int heightInMeters;
	final int speed;
	final int resourcesValue;
	final int dangerLevel;

	public int getCode() {
		return this.code;
	}

	public int getBaseHealth() {
		return this.baseHealth;
	}

	public int getHeightInMeters() {
		return this.heightInMeters;
	}

	public int getBaseDamage() {
		return this.baseDamage;
	}

	public int getSpeed() {
		return this.speed;
	}

	public int getResourcesValue() {
		return this.resourcesValue;
	}

	public int getDangerLevel() {
		return this.dangerLevel;
	}

	public TitanRegistry(int code, int baseHealth, int baseDamage, int heightInMeters, int speed, int resourcesValue,
			int dangerLevel) {
		this.code = code;
		this.baseHealth = baseHealth;
		this.baseDamage = baseDamage;
		this.heightInMeters = heightInMeters;
		this.speed = speed;
		this.resourcesValue = resourcesValue;
		this.dangerLevel = dangerLevel;
	}

}