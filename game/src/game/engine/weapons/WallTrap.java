package game.engine.weapons;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import game.engine.lanes.Lane;
import game.engine.titans.Titan;

public class WallTrap extends Weapon {
	public static final int WEAPON_CODE = 4;
	private Queue<Titan> reachedTitans = new LinkedList<Titan>();

	public WallTrap(int baseDamage) {
		super(baseDamage);
	}

	@Override
	public int turnAttack(PriorityQueue<Titan> laneTitans, Lane lane) {
		int resources = 0;
		Titan[] tempTitans = new Titan[laneTitans.size()];
		int size = laneTitans.size();

		if (laneTitans.size() > 0) {
			for (int i = 0; i < size; i++) {
				if ((tempTitans[i] = laneTitans.poll()).hasReachedTarget()) {
					if (!reachedTitans.contains(tempTitans[i])) {
						reachedTitans.add(tempTitans[i]);
					}
				}
			}

			if (!reachedTitans.isEmpty()) {
				resources += super.attack(reachedTitans.peek());
				if (reachedTitans.peek().isDefeated())
					reachedTitans.poll();
			}

			for (int i = 0; i < tempTitans.length; i++) {
				if (tempTitans[i] != null && tempTitans[i].getCurrentHealth() > 0)
					laneTitans.add(tempTitans[i]);
				else
					NotifyController(tempTitans[i], lane);
			}
		}

		return resources;
	}

}
