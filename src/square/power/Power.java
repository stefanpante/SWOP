package square.power;

public abstract class Power {

	private int remainingTurns;
	
	private int remainingActions;
	
	public Power(int turns, int actions) {
		this.remainingTurns = turns;
		this.remainingActions = actions;
	}
	
	public void decreaseTurn() throws IllegalStateException {
		this.remainingTurns--;
		
		if(this.remainingTurns <= 0)
			throw new IllegalStateException("PowerFail has no more turns.");
	}
	
	public void decreaseAction() throws IllegalStateException {
		this.remainingActions--;
		
		if(this.remainingActions <= 0)
			throw new IllegalStateException("PowerFail has no more actions.");
	}
	
	public abstract boolean isFailing();
}
