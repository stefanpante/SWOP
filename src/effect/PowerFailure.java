package effect;

import game.Player;
import item.IdentityDisc;
import item.inter.Movable;

/**
 * User: jonas
 * Date: 21/05/13
 * Time: 14:00
 */
public class PowerFailure implements Effect {

    @Override
    public void affect(Movable movable) {
        movable.acceptEffect(this);
    }

    @Override
    public void affect(Player player) throws IllegalStateException {
        player.loseActions(1);
    }

    @Override
    public void affect(IdentityDisc identityDisc) throws IllegalStateException {
        identityDisc.decreaseRange();
    }
}
