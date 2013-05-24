package effect.imp;

import effect.Effect;
import effect.EffectPriority;
import game.Player;
import item.IdentityDisc;

/**
 * User: jonas
 * Date: 24/05/13
 * Time: 01:36
 */
public class TeleportBlockEffect extends Effect {

    public TeleportBlockEffect(){
        setPriority(EffectPriority.TeleportBlocked);
    }

    @Override
    public String toString(){
        return "TeleportBlockEffect";
    }

    @Override
    public void execute(Player player) {
        System.out.println("Executing on Player: " + this);
    }

    @Override
    public void execute(IdentityDisc identityDisc) {
    }
}
