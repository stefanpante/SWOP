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
        System.out.println("Executing on Player: " + this);
        player.resetPosition(player.getPreviousPosition());
        throw new IllegalArgumentException("You've been thrown back by " + this);
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
