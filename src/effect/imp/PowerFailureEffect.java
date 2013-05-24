package effect.imp;

import effect.Effect;
import effect.EffectPriority;
import game.Player;
import item.IdentityDisc;

/**
 * User: jonas
 * Date: 23/05/13
 * Time: 21:54
 */
public class PowerFailureEffect extends Effect {

    private static final int LOST_ACTIONS = 1;

    public PowerFailureEffect(){
        //TODO back to square
        setPriority(EffectPriority.Square);
    }

    @Override
    public void execute(Player player) {
        System.out.println("Executing on Player: " + this);
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
