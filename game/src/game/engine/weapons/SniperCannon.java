package game.engine.weapons;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import game.engine.titans.Titan;

public class SniperCannon extends Weapon {
	public static final int WEAPON_CODE = 2;
	private Queue<Titan> firstTitan = new LinkedList<Titan>();

	public SniperCannon(int baseDamage) {
		super(baseDamage);
	}

	@Override
	public int turnAttack(PriorityQueue<Titan> laneTitans) {
		Stack<Titan> tempTitan = new Stack<>();
		int resources = 0;

		int size = laneTitans.size();

		if (size > 0) {
			int distanceFirstTitan = laneTitans.peek().getDistance();

			for (int i = 0; i < size; i++) {
				if (laneTitans.peek().getDistance() == distanceFirstTitan)
					if (!firstTitan.contains(laneTitans.peek()))
						firstTitan.add(laneTitans.poll());
					else
						tempTitan.add(laneTitans.poll());
				else
					break;
			}

			int size2 = firstTitan.size();

			for (int i = 0; i < size2; i++) {
				tempTitan.add(firstTitan.peek());
				resources += super.attack(firstTitan.poll());

				if (tempTitan.peek().isDefeated())
					tempTitan.pop();
			}

			for (Titan titan : tempTitan) {
				laneTitans.add(titan);
			}
		}
		return resources;
	}
}
