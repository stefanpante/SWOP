package square.power.failure;

import util.Rotation;

public class SecondaryPowerFail extends PowerFail {
	
	private final static int TURNS = 0;
	
	private final static int ACTIONS = 2;
	
	private final Rotation rotation;
	
	private PowerFail tertiary;

	public SecondaryPowerFail() {
		super(TURNS, ACTIONS);
		
		this.tertiary = new TertiaryPowerFail();
		this.rotation = Rotation.random();
	}

	@Override
	public void decreaseTurn() {
		
	}

	@Override
	public void decreaseAction() {
		super.decreaseAction();
	}
	
	public PowerFail getTertiary() {
		return this.tertiary;
	}

}
