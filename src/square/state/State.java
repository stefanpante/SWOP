package square.state;

import square.Square;

/**
 * Describes the state of a square.
 * 
 * @author vincentreniers
 */
public abstract class State {
	
	public abstract void nextTurn(Square square);
	
	public int resultOnMove(Square square) {
		return 0;
	}
	
	public int resultOnStart(Square square) {
		return 0;
	}
	
	public boolean affectsNeighbors() {
		return false;
	}
	
}
