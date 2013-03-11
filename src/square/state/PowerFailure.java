package square.state;

import square.Square;

/**
 * This state indicates that a square is experiencing a power failure.
 * 
 * @author vincentreniers
 */
public class PowerFailure extends State{
	
	private int turnsRemaining = 3;
	
	public void nextTurn(Square square) {
		turnsRemaining--;
		
		if(turnsRemaining == 0)
			square.setState(new RegularState());
	}

	/**
	 * The player loses 3 to 4 actions upon moving on a square with powerfailure.
	 */
	@Override
	public StateResult resultOnMove() {
		return new StateResult(0, true);
	}

	@Override
	public StateResult resultOnMoveLG(){
		return new StateResult(-4, true);
	}
	/**
	 * The player loses 1 action when starting on a square with powerfailure.
	 */
	@Override
	public StateResult resultOnStart(Square square) {
		return new StateResult(-1,false);
	}
	
	@Override
	public boolean affectsNeighbors() {
		return true;
	}
	
}
