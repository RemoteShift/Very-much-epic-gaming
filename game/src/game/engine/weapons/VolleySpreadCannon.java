package game.engine.weapons;

import java.util.PriorityQueue;

import game.engine.lanes.Lane;
import game.engine.titans.Titan;

public class VolleySpreadCannon extends Weapon {
	public static final int WEAPON_CODE = 3;

	private final int minRange;
	private final int maxRange;

	public VolleySpreadCannon(int baseDamage, int minRange, int maxRange) {
		super(baseDamage);
		this.minRange = minRange;
		this.maxRange = maxRange;
	}

	public int getMinRange() {
		return minRange;
	}

	public int getMaxRange() {
		return maxRange;
	}

	@Override
	public int turnAttack(PriorityQueue<Titan> laneTitans, Lane lane) {
		Titan[] tempTitans = new Titan[laneTitans.size()];
		int resources = 0;
		if (laneTitans.size() >= 0) {
			for (int i = 0; i < tempTitans.length; i++) {
				tempTitans[i] = laneTitans.poll();
				if (tempTitans[i].getDistance() >= getMinRange() && tempTitans[i].getDistance() <= getMaxRange())
					resources += super.attack(tempTitans[i]);
			}

			for (int i = 0; i < tempTitans.length; i++) {
				if (tempTitans[i].getCurrentHealth() > 0)
					laneTitans.add(tempTitans[i]);
				else
					NotifyController(tempTitans[i], lane);
			}
		}
		return resources;
	}

}
