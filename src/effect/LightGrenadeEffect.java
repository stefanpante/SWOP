package effect;

import game.Player;
import item.Affectable;
import item.LightGrenade;

/**
 * User: jonas
 * Date: 14/05/13
 * Time: 10:50
 */
public class LightGrenadeEffect extends Effect<LightGrenade> implements PlayerEffect {

    public LightGrenadeEffect(LightGrenade affectable) {
        super(affectable);
    }

    @Override
    public void affect(Player player) {
        if(getAffectable().isActive())
            player.loseActions(2);
    }
}
