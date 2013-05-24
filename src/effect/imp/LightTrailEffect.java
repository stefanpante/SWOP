package effect.imp;

import effect.Effect;
import effect.EffectPriority;
import game.Player;
import item.IdentityDisc;

/**
 * User: jonas
 * Date: 23/05/13
 * Time: 13:04
 */
public class LightTrailEffect extends Effect {

    public LightTrailEffect(){
          setPriority(EffectPriority.MoveBlock);
    }

    @Override
    public void execute(Player player) {
        player.setPosition(player.getPreviousPosition(),false);
    }

    @Override
    public void execute(IdentityDisc identityDisc) {
        //
    }

    @Override
    public String toString() {
        return "LightTrailEffect";
    }

}
