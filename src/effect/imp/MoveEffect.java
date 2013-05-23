package effect.imp;

import effect.Effect;
import effect.EffectPriority;
import game.Player;
import item.IdentityDisc;
import square.Square;

/**
 * Created with IntelliJ IDEA.
 * User: Dieter
 * Date: 23/05/13
 * Time: 17:23
 * To change this template use File | Settings | File Templates.
 */
public class MoveEffect extends Effect {

    Square destinationSquare;

    /**
     * Creates a new  DropflagEffect with a square around which the flag will be dropped.
     *
     * @param destinationSquare The square of which its neighbors can contain the dropped flag.
     */
    public MoveEffect(Square destinationSquare) {
        this.destinationSquare = destinationSquare;
        setPriority(EffectPriority.Move);
    }

    public Square getDestination() {
        return destinationSquare;
    }

    @Override
    public void execute(Player player) {
        player.setPosition(getDestination());
    }

    @Override
    public void execute(IdentityDisc identityDisc) {
        identityDisc.setPosition(getDestination());
    }
}
