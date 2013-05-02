package square.field;

import game.Player;
import item.IdentityDisc;
import move.Movable;
import move.MovableEffect;
import square.MultiSquare;
import square.Square;

import java.util.ArrayList;

/**
 * A field is a collection of several squares,
 * with an effect on moveables.
 */
public abstract class Field extends MultiSquare implements MovableEffect {

    @Override
    public void affect(Movable movable) {
        movable.getsAffectedBy(this);
    }

    @Override
    public void affect(Player player) {
        // A player cannot move onto a field!
    }

    @Override
    public void affect(IdentityDisc identityDisc) {
        identityDisc.destroy();
    }
}
