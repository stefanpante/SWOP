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
        this.teleportDestination = teleportDestination;
    }

    public Square getTeleportDestination() {
        return teleportDestination;
    }

    @Override
    public void execute(Player player) {
        System.out.println("Executing on Player: " + this);
        dropFlag(player);
        TeleportBlockEffect effect = new TeleportBlockEffect();
        getTeleportDestination().addEffect(effect);
        player.setPosition(getTeleportDestination(),true);
        getTeleportDestination().removeEffect(effect);
    }

    @Override
    public void execute(IdentityDisc identityDisc) {
        TeleportBlockEffect effect = new TeleportBlockEffect();
        getTeleportDestination().addEffect(effect);
        identityDisc.setPosition(getTeleportDestination(),true);
        getTeleportDestination().removeEffect(effect);
    }

    @Override
    public String toString() {
        return "TeleportEffect";
    }
}
