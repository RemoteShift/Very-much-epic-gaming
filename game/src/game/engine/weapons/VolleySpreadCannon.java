package game.engine.weapons;

public class VolleySpreadCannon extends Weapon {
	public static final int WEAPON_CODE = 3;
	private final int minRange;
	private final int maxRange;

	public VolleySpreadCannon(int baseDamage, int minRange, int maxRange) {
		super(baseDamage);
		this.minRange = minRange;
		this.maxRange = maxRange;
	}

	@Override
	public int getDamage() {
		return getBaseDamage();
	}
	
	public int getMaxRange()
	{
		return maxRange;
	}
	
	public int getMinRange()
	{
		return minRange;
	}
}
