package square.state;

import effect.EffectValue;
import square.Square;

/**
 * This state indicates that a square is experiencing a power failure.
 * Implemented as a singleton.
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 */
public class PowerFailureState extends SquareState{

	// eager singleton implementation
	public static int TURNS_ACTIVE = 3;
	private static final PowerFailureState POWERFAILURE = new PowerFailureState();
	private PowerFailureState(){
		
	}
	

	/**
	 * Returns whether the current state causes a penalty
	 */
	public boolean hasEffect() {
		return true;
	}
	/**
	 * Returns the single instance of this PowerFailure
	 * @return a singleton representing a powerfailure
	 */
	public static PowerFailureState getInstance(){
		return POWERFAILURE;
	}
	
	
	/**
	 * returns a string representation of this object.
	 */
	@Override
	public String toString() {
		return super.toString() + "Power Failed";
	}
	
	@Override
	public EffectValue getEffectBeforeAction() {
		return new EffectValue(0, 1);
	}
	
	@Override
	public EffectValue getEffectDuringAction() {
		return new EffectValue(1, 0);
	}
	
	@Override
	public EffectValue getEffectAfterAction() {
		return new EffectValue(0, 0);
	}
	
}
