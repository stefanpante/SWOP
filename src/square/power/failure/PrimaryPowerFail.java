package square.power.failure;

/**
 * Primary power fail lasts 3 turns.
 * This failure also has two children linked.
 * 
 * @author vincentreniers
 */
public class PrimaryPowerFail extends PowerFail {
	
	private final static int TURNS = 3;
	
	private final static int ACTIONS = 0;

	public PrimaryPowerFail() {
		super(TURNS, ACTIONS);
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
