package effect.imp;

import effect.Effect;
import effect.EffectPriority;
import game.Player;
import item.IdentityDisc;
import org.jetbrains.annotations.NotNull;

/**
 * User: Dieter
 * Date: 23/05/13
 * Time: 16:09
 */
public class ForceFieldStuckEffect extends Effect {

    public ForceFieldStuckEffect(){
        setPriority(EffectPriority.MoveDeparture);
    }

    @Override
    public void execute(Player player) {
        System.out.println("Executing on Player: " + this);
//
    }

    @Override
    public void execute(IdentityDisc identityDisc) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @NotNull
    @Override
    public String toString() {
        return "ForceFieldStuckEffect";
    }
}
