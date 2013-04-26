package square.obstacle;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import square.Square;

public class ForceField extends MultiObstacle {
	
	/**
	 * Maximum length of a Force Field.
	 */
	public static final int MAX_LENGTH = 4;
	
	private int counter;
	
	
	@Override
	public void addSquare(Square square) throws IllegalArgumentException {
		if(!isValidSquare(square))
			throw new IllegalArgumentException("Cannot add square to this ForceField: the square is invalid.");
		getSquares().add(square);
	}
	
	@Override
	public boolean isValidSquare(Square square) {
		if(getLength() >= MAX_LENGTH)
			return false;
		return true;
	}
	
	@Override
	public boolean bouncesBack() {
		return false;
	}
	
	private void turnOff(){
		for(Square square: getSquares()){
			square.setObstacle(null);
		}
	}
	
	private void turnOn(){
		for(Square square: getSquares()){
			square.setObstacle(this);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(++counter%2 == 0){
			turnOn();
		}else{
			turnOff();
		}
		
	}
}