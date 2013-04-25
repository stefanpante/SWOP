package square.power.failure;

public class PrimaryPowerFail extends PowerFail {
	
	private final static int TURNS = 3;
	
	private final static int ACTIONS = 0;
	
	private PowerFail secondary;

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
	
	public PowerFail getSecondary() {
		return this.secondary;
	}

}
