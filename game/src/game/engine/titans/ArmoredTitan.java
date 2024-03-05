package game.engine.titans;

public class ArmoredTitan extends Titan {
	public static final int TITAN_CODE = 3;

	public ArmoredTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase,
			int speed, int resourcesValue, int dangerLevel) {
		super(baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel);
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
