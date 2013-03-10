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
	public int resultOnMove(Square square) {
		if (square.getUsedInventory().hasActiveLightGrenade())
			return -4;
		else
			return -3;
	}

	/**
	 * The player loses 2 actions when stating on a square with powerfailure.
	 */
	@Override
	public int resultOnStart(Square square) {
		return -2;
	}
	
	@Override
	public boolean affectsNeighbors() {
		return true;
	}
	
}
