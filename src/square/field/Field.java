package square.field;

import game.Player;
import item.launchable.IdentityDisc;
import move.Movable;
import move.MovableEffect;
import square.MultiSquare;
import square.Square;

import java.util.ArrayList;

/**
 * User: jonas
 * Date: 02/05/13
 * Time: 16:27
 */
public class Field extends MultiSquare implements MovableEffect {

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
