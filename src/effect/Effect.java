package effect;

/**
 * 
 * @author Dieter
 *
 */
public interface Effect {
	
	/**
	 * Returns number of actions the player loses before starting the action.
	 */
	public abstract EffectValue getEffectBeforeAction();
	
	
	/**
	 * Returns number of actions the player when the action is completed.
	 */
	public abstract EffectValue getEffectAfterAction();
	
	
	/**
	 * Returns whether there is an extra effect involved.
	 */
	public abstract boolean hasEffect();
}
