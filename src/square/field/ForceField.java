package square.field;

import game.Player;
import item.IdentityDisc;
import item.inter.Movable;
import square.Square;
import square.multi.MultiGridElement;

/**
 * Force field is a obstacle which covers multiple squares
 * and can be turned on and off.
 * 
 * @author Dieter Castel, Jonas Devlieghere and Stefan Pante
 */
public class ForceField extends Field {
	
	/**
	 * Maximum length of a Force Field.
	 */
	public static final int MAX_LENGTH = 4;
	
	/**
	 * Amount of actions the force field is active.
	 */
    public static final int ACTIONS_ON = 2;
    
    /**
     * Amount of actions the force field is inactive.
     */
    public static final int ACTIONS_OFF = 2;
    
    /**
     * Current actions remaining.
     */
    private int remainingActions = ACTIONS_ON;

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


	/**
	 * Force field cannot extend its maximum length.
	 */
	@Override
	public boolean isValidGridElement(Square square) {
        return getLength() < MAX_LENGTH && super.isValidGridElement(square);

    }
	
	/**
	 * Turns the force field off.
	 */
	private void turnOff(){
        setActive(false);
		this.remainingActions = ACTIONS_ON;
	}
	
	/**
	 * Turns the force field on.
	 */
	private void turnOn(){
		setActive(true);
		this.remainingActions = ACTIONS_OFF;
	}
	
	/**
	 * Decreases the action of the ForceField,
	 * if there are no remaining actions the state of the field
	 * is changed to active or inactive.
	 */
	public void decreaseActions() {
		this.remainingActions--;
		
		if(isActive() && this.remainingActions <= 0) 
			this.turnOff();	
		
		if(!isActive() && this.remainingActions <= 0)
			this.turnOn();
	}

}