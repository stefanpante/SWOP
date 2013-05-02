package square.field;

import game.Player;
import item.IdentityDisc;
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
public abstract class Field extends MultiSquare implements MovableEffect {


    /**
     * Current state of the ForceField.
     */
    private boolean active = true;

    /**
     * Checks if the ForceField is active.
     *
     * @return	True	If active
     * 			False	If inactive
     */
    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active){
        for (Square square: getSquares()){
            if(active){
                square.addField(this);
            }else{
                square.removeField(this);
            }
        }
        this.active = active;


    }

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
