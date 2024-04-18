package game.engine.weapons;

import java.util.PriorityQueue;

import game.engine.titans.Titan;

public class PiercingCannon extends Weapon {
	public static final int WEAPON_CODE = 1;

	public PiercingCannon(int baseDamage) {
		super(baseDamage);
	}

	@Override
	public int turnAttack(PriorityQueue<Titan> laneTitans) {
		int resorces = 0;
		Titan[] tempTitans = new Titan[5];
		if (laneTitans.size() >= 5) {
			for (int i = 0; i < 5; i++) {
				resorces += super.attack(laneTitans.peek());
				tempTitans[i] = laneTitans.poll();
			}

			for (int i = 0; i < 5; i++) {
				if (tempTitans[i].getCurrentHealth() > 0)
					laneTitans.add(tempTitans[i]);
			}
		}
		return resorces;
	}

}
