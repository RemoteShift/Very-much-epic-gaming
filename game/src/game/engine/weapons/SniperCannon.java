package game.engine.weapons;

import java.util.PriorityQueue;

import game.engine.titans.Titan;

public class SniperCannon extends Weapon {
	public static final int WEAPON_CODE = 2;

	public SniperCannon(int baseDamage) {
		super(baseDamage);
	}

	@Override
	public int turnAttack(PriorityQueue<Titan> laneTitans) {
		Titan tempTitan;
		int resources = 0;
		if (laneTitans.size() >= 1) {
			resources += super.attack(laneTitans.peek());
			if ((tempTitan = laneTitans.poll()).getCurrentHealth() > 0)
				laneTitans.add(tempTitan);
		}
		return resources;
	}

}
