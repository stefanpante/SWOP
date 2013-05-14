package effect;

import game.Player;
import item.*;

/**
 * User: jonas
 * Date: 14/05/13
 * Time: 10:50
 */
public class LightGrenadeEffect extends Effect<LightGrenade> implements PlayerEffect, ItemEffect {

    public LightGrenadeEffect(LightGrenade affectable) {
        super(affectable);
    }

    @Override
    public void affect(Player player) {
        if(getAffectable().isActive())
            player.loseActions(2);
    }

    @Override
    public void affect(Item item) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void affect(IdentityDisc identityDisc) {
        // No specific effect.
    }

    @Override
    public void affect(Flag flag) {
    }
}
