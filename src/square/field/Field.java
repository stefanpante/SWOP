package square.field;

import effect.Effect;
import item.inter.Movable;
import square.MultiSquare;
import square.Square;

/**
 * A field is a collection of several squares,
 * with an effect on moveables.
 */
public abstract class Field extends MultiSquare implements Effect {
	
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
        if(active)
            bindAll();
        else
            unbindAll();
        this.active = active;
    }


    public void bindAll(){
        for(Square square : getSquares())
            square.addEffect(this);
    }

    public void unbindAll(){
        for(Square square : getSquares())
            square.removeField(this);
    }

    public void bind(Square square){
        square.addEffect(this);
    }

    public void unbind(Square square){
        square.removeField(this);
    }

    @Override
    public void affect(Movable movable) {
        movable.acceptStandOnEffect(this);
    }

}
