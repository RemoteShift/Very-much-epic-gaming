package game.engine.titans;

public class ColossalTitan extends Titan {
	public static final int TITAN_CODE = 4;

	public ColossalTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase,
			int speed, int resourcesValue, int dangerLevel) {
		super(dangerLevel, resourcesValue, speed, distanceFromBase, heightInMeters, baseDamage, baseHealth);
	}

	@Override
	public int getDistance() {
		// TODO Auto-generated method stub
		return getDistance();
	}

	@Override
	public void setDistance(int distance) {
		// TODO Auto-generated method stub
		setDistance(distance);
	}

	@Override
	public int getDamage() {
		// TODO Auto-generated method stub
		return getBaseDamage();
	}
}
