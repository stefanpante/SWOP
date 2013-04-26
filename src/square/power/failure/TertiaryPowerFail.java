package square.power.failure;

/**
 * Tertiary power failure lasts for  1 action
 * and doesn't have any children.
 * 
 * @author vincentreniers
 */
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
	
	public void resetCount() {
		super.reset(TURNS, ACTIONS);
	}

}
