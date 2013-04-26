package square.power.failure;

import square.power.Power;

/**
 * Power Fail.
 * 
 * @author vincentreniers
 */
abstract class PowerFail extends Power{

	public PowerFail(int turns, int actions) {
		super(turns, actions);
	}

	public boolean isFailing() {
		return true;
	}
	
}
