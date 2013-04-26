package square.power.failure;

import square.power.Power;
import util.Rotation;

/**
 * Power Fail.
 * 
 * @author vincentreniers
 */
abstract class PowerFail extends Power{

	public PowerFail(int turns, int actions) {
		super(turns, actions);
	}
	
	public PowerFail(int turns, int actions, Rotation rotation) {
		super(turns, actions, rotation);
	}

	public boolean isFailing() {
		return true;
	}
	
}
