package square.power.failure;

import square.power.Power;


public class PrimaryPowerFail extends PowerFail {
	
	private final static int TURNS = 3;
	
	private final static int ACTIONS = 0;
	
	private Power secondary;

	public PrimaryPowerFail() {
		super(TURNS, ACTIONS);
		
		this.secondary = new SecondaryPowerFail();
	}

	@Override
	public void decreaseTurn() {
		super.decreaseTurn();
	}

	@Override
	public void decreaseAction() {
		
	}
	
	public Power getSecondary() {
		return this.secondary;
	}

}
