package game.engine.weapons;

public class WeaponRegistry {
	final int code;
	final int price;
	final int damage;
	final int minRange;
	final int maxRange;
	final String name;

	public int getCode() {
		return this.code;
	}

	public int getprice() {
		return this.price;
	}

	public int getdamage() {
		return this.damage;
	}

	public int getminRange() {
		return this.minRange;
	}

	public int getmaxRange() {
		return this.maxRange;
	}

	public String getName() {
		return this.name;
	}

	public WeaponRegistry(int code, int price) {
		this.code = code;
		this.price = price;
		this.damage = 0;
		this.name = "";
		this.maxRange = 0;
		this.minRange = 0;
	}

	public WeaponRegistry(int code, int price, int damage, String name) {
		this.code = code;
		this.price = price;
		this.damage = damage;
		this.name = name;
		this.maxRange = 0;
		this.minRange = 0;
	}

	public WeaponRegistry(int code, int price, int damage, String name, int minRange, int maxRange) {
		this.code = code;
		this.price = price;
		this.damage = damage;
		this.minRange = minRange;
		this.maxRange = maxRange;
		this.name = name;
	}

}