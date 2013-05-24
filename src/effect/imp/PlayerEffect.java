package effect.imp;

import effect.Effect;
import effect.EffectPriority;
import game.Player;
import item.IdentityDisc;
import org.jetbrains.annotations.NotNull;

/**
 * User: jonas
 * Date: 24/05/13
 * Time: 09:26
 */
public class PlayerEffect extends Effect {

    public PlayerEffect(){
        setPriority(EffectPriority.Player);
    }

    @Override
    public void execute(@NotNull Player player) {
        System.out.println("Executing on Player: " + this);
        player.resetPosition(player.getPreviousPosition());
        throw new IllegalArgumentException("You've been thrown back by " + this);
    }

    @Override
    public void execute(IdentityDisc identityDisc) {
        //
    }

    @NotNull
    @Override
    public String toString() {
        return "PlayerEffect";
    }
}
