package game.engine.weapons;

import java.util.PriorityQueue;
import java.util.Stack;

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

		int size = laneTitans.size();

		for (int i = 0; i < size; i++) {
			if (i >= 5)
				break;
			resorces += super.attack(laneTitans.peek());
			tempTitans[i] = laneTitans.poll();
		}

		for (int i = 0; i < tempTitans.length; i++) {
			if (tempTitans[i] != null && tempTitans[i].getCurrentHealth() > 0)
				laneTitans.add(tempTitans[i]);
		}
		return resorces;
	}

}
