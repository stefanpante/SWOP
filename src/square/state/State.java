package square.state;

import square.Square;

/**
 * Describes the state of a square.
 * 
 * @author vincentreniers
 */
public abstract class State {
	
	public abstract void nextTurn(Square square);
	
	public StateResult resultOnMove() {
		return new StateResult(0, false);
	}
	
	public StateResult resultOnMoveLG() {
		return new StateResult(0, true);
	}
	
	public StateResult resultOnStart() {
		return new StateResult(0, false);
	}
	
	public boolean affectsNeighbors() {
		return false;
	}
}
