package power;

import game.Player;
import item.IdentityDisc;
import item.inter.Movable;

/**
 * User: jonas
 * Date: 13/05/13
 * Time: 11:38
 */
public class PrimaryPowerFailure extends PowerFailure {

    @Override
    public void affect(Movable movable) {
    }

    @Override
    public void affect(Player player) throws IllegalStateException {
    }

    @Override
    public void affect(IdentityDisc identityDisc) throws IllegalStateException {
    }
}
