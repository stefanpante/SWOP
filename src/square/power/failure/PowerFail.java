package square.power.failure;

import game.Player;
import item.IdentityDisc;
import move.Movable;
import square.power.Power;
import util.Rotation;

/**
 * Power Fail.
 * 
 * @author vincentreniers
 */
abstract class PowerFail extends Power {

	public PowerFail(int turns, int actions) {
		super(turns, actions);
	}
	
	public PowerFail(int turns, int actions, Rotation rotation) {
		super(turns, actions, rotation);
	}

	public boolean isFailing() {
		return true;
	}

    @Override
    public void affect(Movable movable){
        movable.getsAffectedBy(this);
    }

    @Override
    public void affect(Player player) {
        player.endTurn();
    }

    @Override
    public void affect(IdentityDisc identityDisc){
        identityDisc.decreaseRange();
    }
}
