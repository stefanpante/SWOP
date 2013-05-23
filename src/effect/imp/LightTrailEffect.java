package effect.imp;

import effect.Effect;
import effect.EffectPriority;
import game.Player;
import item.IdentityDisc;
import square.obstacle.LightTrail;

/**
 * User: jonas
 * Date: 23/05/13
 * Time: 13:04
 */
public class LightTrailEffect extends Effect {

    public void LightTrail(){
          setPriority(EffectPriority.MoveBlocking);
    }

    @Override
    public void execute(Player player) {
    }

    @Override
    public void execute(IdentityDisc identityDisc) {
        //
    }
}
