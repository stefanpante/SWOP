package square.state;

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
