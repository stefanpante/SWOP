package square.power;

import java.util.ArrayList;

import move.MovableEffect;
import util.Direction;
import square.power.failure.PrimaryPowerFail;
import util.Rotation;

/**
 * Power class.
 * 
 * @author vincent
 */
public abstract class Power implements MovableEffect {

	/**
	 * Remaining turns the power will last.
	 */
	private int remainingTurns;
	
	/**
	 * Remaining actions the power will last.
	 */
	private int remainingActions;
	
	/**
	 * A power may have a child power, they are in some way related.
	 */
	private Power child;
	
	/**
	 * Parent power.
	 */
	private Power parent;
	
	/**
	 * The power may rotate in a certain direction.
	 */
	private final Rotation rotation;
	
	/**
	 * The direction from the parent in which the child is facing.
	 */
	private Direction direction;
	
	/**
	 * Constructs a power.
	 * 
	 * @param	turns	Amount of turns the power will last.
	 * @param	actions	Amount of acitons the power will last.
	 * @throws	IllegalArgumentException
	 * 			Thrown when given turns,actions are invalid.
	 */
	public Power(int turns, int actions) throws IllegalArgumentException {
		if(!isValidTurns(turns))
			throw new IllegalArgumentException("Given turns are invalid.");
		
		if(!isValidActions(actions))
			throw new IllegalArgumentException("Given actions are invalid.");
		
		this.remainingTurns = turns;
		this.remainingActions = actions;
		this.rotation = null;
	}
	
	/**
	 * Constructs a power.
	 * 
	 * @param	rotation	Rotation of the power.
	 * @param	turns		Amount of turns the power will last.
	 * @param	actions		Amount of acitons the power will last.
	 * @throws	IllegalArgumentException
	 * 			Thrown when given turns,actions are invalid.
	 */
	public Power(int turns, int actions, Rotation rotation) throws IllegalArgumentException {
		if(!isValidTurns(turns))
			throw new IllegalArgumentException("Given turns are invalid.");
		
		if(!isValidActions(actions))
			throw new IllegalArgumentException("Given actions are invalid.");
		
		this.remainingTurns = turns;
		this.remainingActions = actions;
		
		this.rotation = rotation;
	}
	
	/**
	 * Compares another power and checks if its lifespan is longer.
	 * 
	 * @param	power
	 * @return	True if the given power has longer lifespan.
	 * 			False if the given power has a shorter lifespan.
	 */
	public boolean isLifeSpanLonger(Power power) {
		if(power.getRemainingTurns() > this.remainingTurns)
			return true;
		
		if(power.getRemainingTurns() < this.remainingTurns)
			return false;
			
		if(power.getRemainingActions() > this.remainingActions)
			return true;
		
		return false;
	}
	
	/**
	 * Checks if the given turns are valid.
	 * 
	 * @param	turns
	 * @return	True	If the turns are >= 0
	 * 			False	If the turns given are < 0
	 */
	public static boolean isValidTurns(int turns) {
		return turns >= 0;
	}
	
	/**
	 * Checks if the given actions are valid.
	 * 
	 * @param	actions
	 * @return	True	If the given actions are >= 0
	 * 			False	If the actions are < 0.
	 */
	public static boolean isValidActions(int actions) {
		return actions >= 0;
	}
	
	/**
	 * Returns a new power failure. This failure has two children with
	 * different lifespans.
	 * 
	 * @return	Power	A primary power failure.
	 */
	public static Power getPowerFailure() {
		return new PrimaryPowerFail();
	}
	
	/**
	 * Returns a new regular power which lasts infinite life span.
	 */
	public static Power getRegularPower() {
		return new RegularPower();
	}
	
	/**
	 * Returns the remaining turns.
	 */
	public int getRemainingTurns() {
		return this.remainingTurns;
	}
	
	/**
	 * Returns the remaining actions.
	 */
	public int getRemainingActions() {
		return this.remainingActions;
	}
	
	/**
	 * Checks if the power is still active.
	 * 
	 * @return	True	If the remaining actions or turns are > 0
	 * 			False	If the remaining actions and turns are <= 0	
	 */
	public boolean isActive() {
		return this.remainingActions > 0 || this.remainingTurns > 0;
	}
	
	/**
	 * Decreases the remaining turns by 1.
	 * 
	 * @throws	IllegalStateException
	 * 			If the turns reaches 0 or less an exception is thrown,
	 * 			indicating the power must be removed or renewed.
	 */
	public void decreaseTurn() throws IllegalStateException {
		this.remainingTurns--;
		
		if(this.remainingTurns <= 0)
			throw new IllegalStateException("PowerFail has no more turns.");
	}
	
	/**
	 * Decreases the remaining actions by 1.
	 * 
	 * @throws	IllegalStateException
	 * 			If the actions reaches 0 or less, an exception is thrown,
	 * 			indicating the power must be removed or renewed.
	 */
	public void decreaseAction() throws IllegalStateException {
		this.remainingActions--;
		
		if(this.remainingActions <= 0)
			throw new IllegalStateException("PowerFail has no more actions.");
	}
	
	/**
	 * Resets the power's remaining actions and turns.
	 * This is used when the power has ended and must be renewed.
	 * 
	 * @param	turns the number of turns
	 * @param	actions the number of actions
	 * @throws	IllegalArgumentException
	 * 			Thrown when given turns,actions are invalid.

	 */
	protected void reset(int turns, int actions) throws IllegalArgumentException {
		if(!isValidTurns(turns))
			throw new IllegalArgumentException("Given turns are invalid.");
		
		if(!isValidActions(actions))
			throw new IllegalArgumentException("Given actions are invalid.");
			
		this.remainingTurns = turns;
		this.remainingActions = actions;
	}
	
	/**
	 * Sets the child of a power.
	 * 
	 * @param power  the power
	 */
	protected void setChild(Power power) {
		this.child = power;
	}
	
	/**
	 * Sets the parent of the power.
	 */
	protected void setParent(Power power) {
		this.parent = power;
	}
	
	/**
	 * Returns the power itself and its nodes.
	 */
	public ArrayList<Power> getChildren() {
		ArrayList<Power> children = new ArrayList<Power>();
		
		Power lastChild = this;
		
		while(lastChild.hasChild()) {
			children.add(lastChild.getChild());
			lastChild = lastChild.getChild();
		}
		
		return children;
	}
	
	/**
	 * Checks if the power has a child power.
	 * Child powers are related to the current power.
	 * 
	 * @return	True	If there is a child.
	 * 			False	If there is no child.
	 */
	public boolean hasChild() {
		return this.child != null; 
	}
	
	/**
	 * Checks if the power has a parent power.
	 * 
	 * @return	True	If there is a parent.
	 * 			False	If there is no parent.
	 */
	public boolean hasParent() {
		return this.parent != null;
	}
	
	/**
	 * Returns the child if there is one.
	 * 
	 * @return	Null	If no child.
	 * 			Power	If the power has a child.
	 */
	public Power getChild() {
		return this.child;
	}
	
	/**
	 * Returns the power's parent.
	 * 
	 * @return	Null	If the power has no parent.
	 * 			Power	If the power has a parent.
	 */
	public Power getParent() {
		return this.parent;
	}
	
	/**
	 * Returns the rotation in which the power is rotating.
	 * Not all powers rotate so this may be null.
	 * 
	 * @return	Null
	 * 			Rotation
	 */
	public Rotation getRotation() {
		return this.rotation;
	}
	
	/**
	 * Sets the direction the power is facing from the parent.
	 * 
	 * @param	direction     the direction of the power
	 * @throws	IllegalStateException
	 * 			If the direction is not appropriate for this power. (no parent)
	 */
	public void setDirection(Direction direction) throws IllegalStateException {
		if(!hasParent()) 
			throw new IllegalStateException("The power should not have a direction since it has no parent.");
		
		this.direction = direction;
	}
	
	/**
	 * Returns the direction the power is facing from the parent.
	 * 
	 * @return	Null	If the power has no parent.
	 * 			Direction
	 */
	public Direction getDirection() {
		return this.direction;
	}
	
	/**
	 * Checks if the power has power or if it is experiencing a power failure.
	 * 
	 * @return	True	If power failure.
	 * 			False	If regular power state.
	 */
	public abstract boolean isFailing();
	
	/**
	 * Resets the turns and actions.
	 */
	public abstract void resetCount();
}
