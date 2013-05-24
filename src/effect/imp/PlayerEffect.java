package effect.imp;

import effect.Effect;
import effect.EffectPriority;
import game.Player;
import item.IdentityDisc;

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
    public void execute(Player player) {
        System.out.println("Executing on Player: " + this);
        player.setPosition(player.getPreviousPosition(), false);
    }

    @Override
    public void execute(IdentityDisc identityDisc) {
        //
    }

    @Override
    public String toString() {
        return "PlayerEffect";
    }
}
