package game.engine.interfaces;

public interface Mobil {
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance=distance;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed=speed;
	}
}
