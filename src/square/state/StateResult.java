package square.state;

/**
 * Result on certain actions concerning the square.
 * This result shows which actions need to be taken on the player.
 * Such as lost actions or ending the turn because of the square's state.
 * 
 * @author vincentreniers
 */
public class StateResult {

	private int lostActions;
	
	private boolean endTurn;
	
	public StateResult(int lostActions, boolean endTurn) {
		this.lostActions = lostActions;
		this.endTurn = endTurn;
	}
	
	public int getLostActions() {
		return this.lostActions;
	}
	
	public boolean hasToEndTurn() {
		return this.endTurn;
	}
}
