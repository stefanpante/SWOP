package effect.imp;

import effect.Effect;
import effect.EffectPriority;
import game.Player;
import item.IdentityDisc;
import org.jetbrains.annotations.NotNull;

/**
 * User: Dieter
 * Date: 23/05/13
 * Time: 16:00
 */
public class ForceFieldEffect extends Effect {

    public ForceFieldEffect(){
        setPriority(EffectPriority.ForceField);
    }

    @Override
    public void execute(@NotNull Player player) {
        player.resetPosition(player.getPreviousPosition());
    }

    @Override
    public void execute(@NotNull IdentityDisc identityDisc) {
        identityDisc.destroy();
    }

    @NotNull
    @Override
    public String toString() {
        return "ForceFieldEffect";
    }
}
