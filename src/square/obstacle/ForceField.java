package square.obstacle;

import item.ForceFieldGenerator;

import java.util.Observable;
import java.util.Observer;

import square.Square;

/**
 * Force field is a obstacle which covers multiple squares
 * and can be turned on and off.
 * 
 * @author Vincent
 */
public class ForceField extends MultiObstacle implements Observer{
	
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
     * A force field must be constructed when it is located between two generators.
     */
    public final ForceFieldGenerator endPointOne, endPointTwo;
	
    /**
     * If instantiated the force field is active and
     * has its remaining actions set to 2.
     */
	public ForceField(ForceFieldGenerator generatorOne, ForceFieldGenerator generatorTwo) {
		super();
		
		if(!isValidGenerators(generatorOne, generatorTwo))
			throw new IllegalArgumentException("The given generators are invalid.");
		
		this.endPointOne = generatorOne;
		this.endPointTwo = generatorTwo;
	}
	
	/**
	 * Checks if the given generators are valid.
	 * 
	 * @param generatorOne
	 * @param generatorTwo
	 * 
	 * @return	False	If the generators are equal.
	 * 			True	Otherwise
	 */
	public static boolean isValidGenerators(ForceFieldGenerator generatorOne,	ForceFieldGenerator generatorTwo) {
		return generatorOne != generatorTwo;
	}

	@Override
	public void addSquare(Square square) throws IllegalArgumentException {
		if(!isValidSquare(square))
			throw new IllegalArgumentException("Cannot add square to this ForceField: the square is invalid.");
		getSquares().add(square);
	}
	
	/**
	 * Force field cannot extend its maximum length.
	 */
	@Override
	public boolean isValidSquare(Square square) {
		if(getLength() >= MAX_LENGTH)
			return false;
		
		return true;
	}
	
	/**
	 * Checks if the ForceField is active.
	 * 
	 * @return	True	If active
	 * 			False	If inactive
	 */
	public boolean isActive() {
		return this.active;
	}
	
	@Override
	public boolean bouncesBack() {
		return false;
	}
	
	/**
	 * Turns the force field off.
	 */
	private void turnOff(){
		this.active = false;
		this.remainingActions = ACTIONS_ON;
		
		for (Square square: getSquares())
			square.setObstacle(null);
	}
	
	/**
	 * Turns the force field on.
	 */
	private void turnOn(){
		this.active = true;
		this.remainingActions = ACTIONS_OFF;
		
		for (Square square: getSquares())
			square.setObstacle(this);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		this.remainingActions--;
		
		if(isActive() && this.remainingActions <= 0) 
			this.turnOn();	
		
		if(!isActive() && this.remainingActions <= 0)
			this.turnOff();
	}
}