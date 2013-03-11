package square.state;

import square.Square;

public class RegularState extends State{

	/**
	 * No actions required on a next turn when the state is regular.
	 */
	@Override
	public void nextTurn(Square square) {
	}
	
	
	public StateResult resultOnMove(){
		return new StateResult(0, false);
	}
	
	public StateResult resultOnMoveLG(){
		return new StateResult(-3, false);
	}

}
