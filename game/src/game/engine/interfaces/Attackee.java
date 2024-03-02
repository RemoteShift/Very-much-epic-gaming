package game.engine.interfaces;

public interface Attackee {
	public int getCurrentHealth() {
		return currentHealth;
	}
	public void setCurrentHealth(int health) {
		currentHealth=health;
	}
	public int getResourcesValue() {
		return resourcesValue;
	}
}
