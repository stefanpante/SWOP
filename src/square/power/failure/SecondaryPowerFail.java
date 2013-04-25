package square.power.failure;

import util.Rotation;

public class SecondaryPowerFail extends PowerFail {
	
	private final static int TURNS = 0;
	
	private final static int ACTIONS = 2;

	public SecondaryPowerFail() {
		super(TURNS, ACTIONS);
		
		this.setChild(new TertiaryPowerFail());
		this.setRotation(Rotation.random());
	}

	@Override
	public void decreaseTurn() {
		
	}

	@Override
	public void decreaseAction() {
		super.decreaseAction();
	}
	
	public void resetCount() {
		super.reset(TURNS, ACTIONS);
	}

}
