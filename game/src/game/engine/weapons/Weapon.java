package temporary;

abstract class Weapon {
	private int baseDamage;
	
	public Weapon(int baseDamage){
		this.baseDamage = baseDamage;
	}
	public int getBaseDamage() {
		return baseDamage;
	}
}
