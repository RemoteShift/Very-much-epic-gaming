package game.engine.weapons;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import game.engine.titans.Titan;

public class PiercingCannon extends Weapon {
	public static final int WEAPON_CODE = 1;
	private Queue<Titan> titansToAttack = new LinkedList<Titan>();

	public PiercingCannon(int baseDamage) {
		super(baseDamage);
	}

	@Override
	public int turnAttack(PriorityQueue<Titan> laneTitans) {
		int resorces = 0;
		Stack<Titan> tempTitans = new Stack<>();

		int size = laneTitans.size();

		if (size > 0) {
			for (int i = 0; i < size; i++) {
				if (i >= 5)
					break;
				if (!titansToAttack.contains(laneTitans.peek()))
					titansToAttack.add(laneTitans.poll());
				else
					tempTitans.add(laneTitans.poll());
			}

			int size2 = titansToAttack.size();

			for (int i = 0; i < size2; i++) {
				tempTitans.add(titansToAttack.peek());
				resorces += super.attack(titansToAttack.poll());
				if (tempTitans.peek().isDefeated())
					tempTitans.pop();
			}

			for (Titan titan : tempTitans) {
				laneTitans.add(titan);
			}
		}

		return resorces;
	}

}
