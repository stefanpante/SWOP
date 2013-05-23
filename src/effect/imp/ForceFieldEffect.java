package effect.imp;

import effect.Effect;
import effect.EffectPriority;
import game.Player;
import item.IdentityDisc;

/**
 * Created with IntelliJ IDEA.
 * User: Dieter
 * Date: 23/05/13
 * Time: 16:00
 * To change this template use File | Settings | File Templates.
 */
public class ForceFieldEffect extends Effect {

    public void ForceFieldEffect(){
        setPriority(EffectPriority.ForceField);
    }

    @Override
    public void execute(Player player) {
        player.setPosition(player.getPreviousPosition(),false);
    }

    @Override
    public void execute(IdentityDisc identityDisc) {
        identityDisc.destroy();
    }
}
