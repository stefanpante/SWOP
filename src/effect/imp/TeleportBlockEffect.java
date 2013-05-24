package effect.imp;

import effect.Effect;
import effect.EffectPriority;
import game.Player;
import item.IdentityDisc;
import org.jetbrains.annotations.NotNull;

/**
 * User: jonas
 * Date: 24/05/13
 * Time: 01:36
 */
public class TeleportBlockEffect extends Effect {

    public TeleportBlockEffect(){
        setPriority(EffectPriority.TeleportBlocked);
    }

    @NotNull
    @Override
    public String toString(){
        return "TeleportBlockEffect";
    }

    @Override
    public void execute(Player player) {
    }

    @Override
    public void execute(IdentityDisc identityDisc) {
    }
}
