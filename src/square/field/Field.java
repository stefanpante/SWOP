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
	
	/**
     * Current state of the ForceField.
     */
    private boolean active = true;
    
    /**
     * Checks if the ForceField is active.
     *
     * @return  True if and only if the square is active
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Activate of deactivate the given ForceField
     *
     * @param   active
     *          Whether the ForceField should be active
     */
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
