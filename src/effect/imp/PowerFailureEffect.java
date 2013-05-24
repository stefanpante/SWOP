package effect.imp;

import effect.Effect;
import effect.EffectPriority;
import game.Player;
import item.IdentityDisc;
import org.jetbrains.annotations.NotNull;

/**
 * User: jonas
 * Date: 23/05/13
 * Time: 21:54
 */
public class PowerFailureEffect extends Effect {

    private static final int LOST_ACTIONS = 1;

    public PowerFailureEffect(){
        setPriority(EffectPriority.Square);
    }

    @Override
    public void execute(@NotNull Player player) {
        player.loseActions(LOST_ACTIONS);
    }

    @Override
    public void execute(@NotNull IdentityDisc identityDisc) {
        identityDisc.decreaseRange();
    }

    @NotNull
    @Override
    public String toString() {
        return "PowerFailureEffect";
    }
}
