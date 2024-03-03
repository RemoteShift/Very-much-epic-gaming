package game.engine.base;

public class Wall {
	private int baseHealth;
	private int currentHealth;

	public Wall(int baseHealth) {
		this.baseHealth = baseHealth;
		this.currentHealth = baseHealth;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	public int getBaseHealth() {
		return baseHealth;
	}

}
