package effect.imp;

import effect.EffectPriority;
import game.Player;
import item.IdentityDisc;
import org.jetbrains.annotations.NotNull;
import square.Square;

/**
 * User: jonas
 * Date: 23/05/13
 * Time: 13:02
 */
public class TeleportEffect extends DropFlagEffect {

    private final Square teleportDestination;

    /**
     * Creates a new  DropflagEffect with a square around which the flag will be dropped.
     *
     * @param centerSquare The square of which its neighbors can contain the dropped flag.
     */
    public TeleportEffect(Square centerSquare, Square teleportDestination) {
        super(centerSquare);
        setPriority(EffectPriority.Move);
        this.teleportDestination = teleportDestination;
    }

    Square getTeleportDestination() {
        return teleportDestination;
    }

    @Override
    public void execute(@NotNull Player player) {

        dropFlag(player);
        TeleportBlockEffect effect = new TeleportBlockEffect();
        getTeleportDestination().addSquareEffect(effect);

        Square fromTeleport = player.getPosition();
        fromTeleport.addSquareEffect(player.getPlayerEffect());
        player.move(getTeleportDestination());

        getTeleportDestination().removeSquareEffect(effect);

    }

    @Override
    public void execute(IdentityDisc identityDisc) {
        TeleportBlockEffect effect = new TeleportBlockEffect();
        getTeleportDestination().addSquareEffect(effect);
        // identityDisc.setPosition(getTeleportDestination(),true);
        getTeleportDestination().removeSquareEffect(effect);
    }

    @NotNull
    @Override
    public String toString() {
        return "TeleportEffect";
    }
}
