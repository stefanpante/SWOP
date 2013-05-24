package effect.imp;

import effect.Effect;
import effect.EffectPriority;
import game.Player;
import item.IdentityDisc;

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
    public void execute(Player player) {
        System.out.println("Executing on Player: " + this);
        player.resetPosition(player.getPreviousPosition());
        throw new IllegalArgumentException("You've been thrown back by " + this);
    }

    @Override
    public void execute(IdentityDisc identityDisc) {
        identityDisc.destroy();
    }

    @Override
    public String toString() {
        return "ForceFieldEffect";
    }
}
