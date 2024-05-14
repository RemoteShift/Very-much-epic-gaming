package game.engine.weapons;

import java.util.PriorityQueue;

import game.engine.interfaces.Attackee;
import game.engine.interfaces.Attacker;
import game.engine.lanes.Lane;
import game.engine.titans.Titan;
import game.gui.GameController;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

public abstract class Weapon implements Attacker {
	private final int baseDamage;
	private GameController gameController;

	public Weapon(int baseDamage) {
		super();
		this.baseDamage = baseDamage;
		gameController = GameController.instance;
	}

	@Override
	public int getDamage() {
		return this.baseDamage;
	}

	@Override
	public int attack(Attackee Titan) {
		return Titan.takeDamage(getDamage());
	}

	public abstract int turnAttack(PriorityQueue<Titan> laneTitans, Lane lane);
	
	public void NotifyController(Titan titan, Lane lane) {
		gameController.removeTitan(titan, lane);
		gameController.TitanImages.remove(titan);
	}

}
