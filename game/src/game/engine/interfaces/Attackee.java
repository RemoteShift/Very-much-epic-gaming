package game.engine.interfaces;

public interface Attackee {
	default boolean isDefeated(){
		if(getCurrentHealth()<=0)
			return true;
		else
			return false;
	}
	default int takeDamage(int damage){
		setCurrentHealth(getCurrentHealth()-damage);
		if(isDefeated())
			return getResourcesValue();
		else
			return 0;
	}

	int getCurrentHealth();

	void setCurrentHealth(int health);

	int getResourcesValue();

}
