package square.power.failure;

import util.Rotation;

/**
 * Secondary power failure lasts for 2 actions.
 * The secondary power failure has one child.
 * 
 * @author vincentreniers
 */
public class SecondaryPowerFail extends PowerFail {
	
	public final static int TURNS = 0;
	
	public final static int ACTIONS = 2;

	public SecondaryPowerFail(PowerFail parent) {
		super(TURNS, ACTIONS, Rotation.random());
		
		this.setParent(parent);
		this.setChild(new TertiaryPowerFail(this));
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
