package square.state;

import effect.Effect;
import effect.EffectValue;
import square.Square;

/**
 * Describes the state of a square.
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 */
public abstract class SquareState implements Effect {

	/**
	 * Returns a string representation of this object.
	 */
	@Override
	public String toString() {
		return "Square State: ";
	}
	
	/**
	 * In a standard square state no special effects are required before an action.
	 */
	@Override
	public EffectValue getEffectBeforeAction() {
		return new EffectValue();
	}

	/**
	 * In a standard square state no special effects are required during an action.
	 */
	@Override
	public EffectValue getEffectDuringAction() {
		return new EffectValue();
	}

	/**
	 * In a standard square state no special effects are required after an action.
	 */
	@Override
	public EffectValue getEffectAfterAction() {
		return new EffectValue();
	}
}
