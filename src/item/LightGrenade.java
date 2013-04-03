package item;

import effect.Effect;
import effect.EffectValue;

/**
 * This class extends Item and represents a LightGrenade object.
 *  
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class LightGrenade extends Item implements Effect{

	/**
	 * returns if this object has a penalty.
	 */
	public boolean hasEffect() {
		return true;
	}
	
	/**
	 * Returns the string representation of this LightGrenade
	 */
	@Override
	public String toString() {
		return super.toString() + " LightGrenade";
	}

	/**
	 * No special effect is required before reaching a LightGrenade.
	 */
	@Override
	public EffectValue getEffectBeforeAction() {
		return new EffectValue();
	}

	/**
	 * Once the action is completed, the effect is already inflicted.
	 */
	@Override
	public EffectValue getEffectAfterAction() {
		return new EffectValue(0, -3);
	}
}
