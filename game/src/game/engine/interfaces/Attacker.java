package game.engine.interfaces;

public interface Attacker {
	default int attack(Attackee target){
		return target.takeDamage(getDamage());
		
	}int getDamage(); // gets the damage value to be applied
}
