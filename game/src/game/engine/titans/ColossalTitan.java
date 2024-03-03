package temporary;

public class ColossalTitan extends Titan {
	private static final int  TITAN_CODE = 4;
	public ColossalTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase,
			int speed, int resourcesValue, int dangerLevel){
		super(dangerLevel, resourcesValue, speed, distanceFromBase, heightInMeters, baseDamage, baseHealth);
	}
	//i had to add this here because i implemented the comparable interface for the titan class.
	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}
