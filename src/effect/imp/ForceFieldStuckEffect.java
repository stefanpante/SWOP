package effect.imp;

import effect.Effect;
import effect.EffectPriority;
import game.Player;
import item.IdentityDisc;

/**
 * User: Dieter
 * Date: 23/05/13
 * Time: 16:09
 */
public class ForceFieldStuckEffect extends Effect {

    public ForceFieldStuckEffect(){
        setPriority(EffectPriority.MoveDeparture);
    }

    //FIXME: how to set this stuck effect on a ForceField that goes on and off?
    @Override
    public void execute(Player player) {
        System.out.println("Executing on Player: " + this);
//
    }

    @Override
    public void execute(IdentityDisc identityDisc) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String toString() {
        return "ForceFieldStuckEffect";
    }
}
