package game.engine.interfaces;

public interface Mobil{
	default boolean hasReachedTarget(){
		if(getDistance()==0)
			return true;
		else
			return false;
	}
	default boolean move(){
		int locoloco = getDistance()-getSpeed();
		if(locoloco<=0){
			setDistance(0);
			return true;}
		else{
			setDistance(locoloco);
			return false;
		}
	}

	int getDistance();

	void setDistance(int distance);

	int getSpeed();

	void setSpeed(int speed);

}
