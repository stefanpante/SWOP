package effect.imp;

import effect.EffectPriority;
import game.Player;
import item.IdentityDisc;
import square.Square;

/**
 * User: jonas
 * Date: 23/05/13
 * Time: 13:02
 */
public class TeleportEffect extends DropFlagEffect {

    Square teleportDestination;

    /**
     * Creates a new  DropflagEffect with a square around which the flag will be dropped.
     *
     * @param centerSquare The square of which its neighbors can contain the dropped flag.
     */
    public TeleportEffect(Square centerSquare, Square teleportDestination) {
        super(centerSquare);
        setPriority(EffectPriority.Move);
    }

    public Square getTeleportDestination() {
        return teleportDestination;
    }

    @Override
    public void execute(Player player) {
        dropFlag(player);
        player.setPosition(getTeleportDestination());
    }

    @Override
    public void execute(IdentityDisc identityDisc) {
        identityDisc.setPosition(getTeleportDestination());
    }
}
