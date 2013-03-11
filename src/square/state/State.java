package square.state;

import square.Square;

/**
 * Describes the state of a square.
 * 
 * @author vincentreniers
 */
public abstract class State {
	
	public abstract void nextTurn(Square square);
	
	public StateResult resultOnMove(Square square) {
		return new StateResult(0, false);
	}
	
	public StateResult resultOnStart(Square square) {
		return new StateResult(0, false);
	}
	
	public boolean affectsNeighbors() {
		return false;
	}
	
}
