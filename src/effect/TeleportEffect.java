package effect;

import game.Player;
import item.IdentityDisc;
import item.Item;
import item.Teleport;

/**
 * User: jonas
 * Date: 14/05/13
 * Time: 11:07
 */
public class TeleportEffect extends Effect<Teleport> implements PlayerEffect, ItemEffect {

    public TeleportEffect(Teleport affectable) {
        super(affectable);
    }

    @Override
    public void affect(Player player) {
        if(!player.justTeleported()){
            System.out.println("affect3");
            if(getAffectable().canTeleport()){
                player.setJustTeleported(true);
                player.move(getAffectable().getDestination());
            } else {
                throw new IllegalStateException("Cant move to to the teleport destination.");
            }
        }
    }


    @Override
    public void affect(Item item) {

    }

    @Override
    public void affect(IdentityDisc identityDisc) {
        if(!identityDisc.justTeleported()){
            identityDisc.setJustTeleported(true);
            identityDisc.decreaseRange();
            identityDisc.move(getAffectable().getDestination());
        }
    }
}
