package square.obstacle;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import square.Square;

public class ForceField extends MultiObstacle implements Observer {
	
	/**
	 * Maximum length of a Force Field.
	 */
	public static final int MAX_LENGTH = 4;
	
	private int counter;

	private ArrayList<Square> squares;
	
	
	@Override
	public boolean bouncesBack() {
		return false;
	}
	
	private void turnOff(){
		
	}
	
	private void turnOn(){
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		counter++;
	}
}