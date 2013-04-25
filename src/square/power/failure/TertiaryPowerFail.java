package square.power.failure;

public class TertiaryPowerFail extends PowerFail {
	
	private final static int TURNS = 0;
	
	private final static int ACTIONS = 1;

	public TertiaryPowerFail() {
		super(TURNS, ACTIONS);
	}

	@Override
	public void decreaseTurn() {
		
	}

	@Override
	public void decreaseAction() {
		super.decreaseAction();
	}

}
