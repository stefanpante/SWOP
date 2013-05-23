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

    public void LightTrail(){
          setPriority(EffectPriority.MoveBlock);
    }

    @Override
    public void execute(Player player) {
    }

    @Override
    public void execute(IdentityDisc identityDisc) {
        //
    }
}
