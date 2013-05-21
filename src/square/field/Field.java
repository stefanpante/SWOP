package square.field;

import square.MultiSquare;
import square.Square;

/**
 * A field is a collection of several squares,
 * with an effect on moveables.
 */
public abstract class Field extends MultiSquare {
	
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
            if (active)
                square.addField(this);
            else
                square.removeField(this);
        }
        this.active = active;
    }

}
