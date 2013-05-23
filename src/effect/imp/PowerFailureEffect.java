package effect.imp;

import effect.Effect;
import game.Player;
import item.IdentityDisc;

/**
 * User: jonas
 * Date: 23/05/13
 * Time: 21:54
 */
public class PowerFailureEffect extends Effect {

    private static final int LOST_ACTIONS = 1;

    @Override
    public void execute(Player player) {
        player.loseActions(LOST_ACTIONS);
    }

    @Override
    public void execute(IdentityDisc identityDisc) {
        identityDisc.decreaseRange();
    }

    @Override
    public String toString() {
        return "PowerFailureEffect";
    }
}
