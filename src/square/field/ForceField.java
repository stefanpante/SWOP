package square.field;

import java.util.Observable;
import java.util.Observer;

import item.ForceFieldGenerator;
import square.Square;

/**
 * Force field is a obstacle which covers multiple squares
 * and can be turned on and off.
 * 
 * @author Vincent
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


	@Override
	public void addSquare(Square square) throws IllegalArgumentException {
        super.addSquare(square);
        square.addField(this);
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
		
		for(Square square: getSquares())
			square.getAllFields().remove(square);
	}
	
	/**
	 * Turns the force field on.
	 */
	private void turnOn(){
		setActive(true);
		this.remainingActions = ACTIONS_OFF;

		for (Square square: getSquares())
			square.addField(this);
	}
	
	public void decreaseActions() {
		this.remainingActions--;
		
		if(isActive() && this.remainingActions <= 0) 
			this.turnOff();	
		
		if(!isActive() && this.remainingActions <= 0)
			this.turnOn();
	}


}