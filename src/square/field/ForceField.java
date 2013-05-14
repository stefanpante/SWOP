package square.field;

import effect.Effect;
import effect.ForceFieldEffect;
import item.Affectable;
import square.Square;

/**
 * Force field is a obstacle which covers multiple squares
 * and can be turned on and off.
 * 
 * @author Vincent
 */
public class ForceField extends Field implements Affectable {
	
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


    public void bind(){
        for(Square square : getSquares()){
            square.addField(this);
        }
    }

	/**
	 * Force field cannot extend its maximum length.
	 */
	@Override
	public boolean isValidSquare(Square square) {
		if(getLength() >= MAX_LENGTH)
			return false;
		
		return super.isValidSquare(square);
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


    @Override
    public Effect getEffect() {
        return new ForceFieldEffect(this);
    }
}