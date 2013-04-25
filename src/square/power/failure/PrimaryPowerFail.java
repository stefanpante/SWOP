package square.power.failure;

public class PrimaryPowerFail extends PowerFail {
	
	private final static int TURNS = 3;
	
	private final static int ACTIONS = 0;

	public PrimaryPowerFail() {
		super(TURNS, ACTIONS);
		
		this.setChild(new SecondaryPowerFail());
	}

	@Override
	public void decreaseTurn() {
		super.decreaseTurn();
	}

	@Override
	public void decreaseAction() {
		
	}
	
	public void resetCount() {
		super.reset(TURNS, ACTIONS);
	}

}
