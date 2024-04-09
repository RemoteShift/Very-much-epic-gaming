package game.engine.weapons;

import game.engine.interfaces.Attacker;

public abstract class Weapon implements Attacker
{
	private final int baseDamage;

	public Weapon(int baseDamage)
	{
		super();
		this.baseDamage = baseDamage;
	}

	@Override
	public int getDamage()
	{
		return this.baseDamage;
	}

}
