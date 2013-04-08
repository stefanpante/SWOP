package player;

import java.util.Observable;


import be.kuleuven.cs.som.annotate.Basic;

import notnullcheckweaver.NotNull;
import notnullcheckweaver.Nullable;

import square.Square;
import square.obstacle.Obstacle;

import item.Item;
import item.inventory.PlayerInventory;

/**
 * Player class
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers en Stefan Pante
 *
 */
@NotNull
public class Player extends Observable implements Obstacle {

	/**
	 * The start position of this player
	 */
	private Square startPosition;
	
	/**
	 * the current position of the player
	 */
	@Nullable
	private Square currentPosition;
	
	/**
	 * The inventory of the player
	 */
	private PlayerInventory inventory;
	
	/**
	 * The player's ID.
	 */
	private final int ID;
	
	/**
	 * The number of remaining actions the player has left
	 */
	private int remainingActions;
	
	/**
	 * true if the user has moved during his turn
	 */
	private boolean moved;
	
	/**
	 * The amount of action a player has during one move
	 */
	public static final int MAX_ALLOWED_ACTIONS = 3;
		
	/**
	 * creates a new player with a given name and start position
	 * 
	 * @param	startPosition	
	 * 			The startposition for the player.
	 * @param	name			
	 *			The name for the player.
	 * @effect	setStartPosition(startPosition)
	 * @effect	setName(name)
	 * @throws	IllegalArgumentException	
	 * 			If the startPosition is null.
	 * @effect	setInventory(new PlayerInventory())
	 * @throws	IllegalArgumentException	If the startPosition is null.
	 * @throws	IllegalArgumentException	If the given name is not valid.
	 */
	public Player(Square startPosition, int id) throws IllegalArgumentException {
		this.setStartPosition(startPosition);
		this.setInventory(new PlayerInventory());
		
		this.remainingActions = MAX_ALLOWED_ACTIONS;
		this.moved = false;
		this.ID = id;
	}
	
	/**
	 * In order for a square to be a valid starting position it cannot
	 * be null. The square can't be obstructed.
	 * 
	 * @param	square
	 * @returns	True	If the square is not null and not obstructed.
	 * 			False	If the square is null or obstructed.
	 */
	public static boolean isValidStartPosition(Square square) {
		if(square == null)
			return false;
		
		if(square.isObstructed())
			return false;
		
		return true;
	}
	
	/**
	 * A move is valid when the destination square is not null
	 * and when it is not obstructed.
	 * 
	 * @param	newPosition
	 * @return	True	If square is not null and not obstructed.
	 * 			False	If square is null or obstructed.
	 */
	public static boolean isValidPosition(Square newPosition) {
		if(newPosition == null)
			return false;
		if(newPosition.isObstructed())
			return false;
		return true;
	}
	
	/**
	 * An inventory is considered valid when the inventory is not null.
	 * 
	 * @param	inventory
	 * @return	True	If inventory is not null
	 * 			False	If inventory is null.
	 */
	public static boolean isValidInventory(PlayerInventory inventory) {
		if(inventory == null)
			return false;
		
		return true;
	}
	
	/**
	 * Returns if the number of actions is valid for the player
	 * @param actions	the number of actions to be checked
	 * @return	true	if the number of actions equals or is smaller than 
	 * 					the number of allowed actions
	 * 			false 	otherwise
	 */
	public static boolean isValidRemainingActions(int actions){
		return actions <= Player.MAX_ALLOWED_ACTIONS;
	}
	
	/**
	 * An item is valid if the item is not null and if the inventory can
	 * have the item.
	 * 
	 * @param	item
	 * @return	True	If item is not null and if the inventory can have the item.
	 * 					The square must also hold the item.
	 * 			False	If the item is null or if the inventory cannot have the item.
	 * 					Or if the square does not have the item.
	 */
	public boolean isValidPickUp(Item item) {
		if(item == null)
			return false;
		if(!inventory.canHaveAsItem(item))
			return false;
		return true;
	}
	
	/**
	 * A new position square is valid when it is not null and
	 * when it does not equal the current square.
	 * 
	 * @returns	True	Square is not null and does not equal current position.
	 * @returns	False	Square is null or equals current position.
	 */
	@Override
	public boolean isValidSquare(Square square) {
		if(square == null)
			return false;
		if(square == currentPosition)
			return false;
		return true;
	}
	
	/**
	 * Checks whether a given number of actions is a valid number of actions to lose.
	 * @return 		true if the number is larger than or equal to zero.
	 */
	public boolean isValidActionsLost(int actions){
		return (actions >= 0);
	}

	/**
	 * Checks whether turns is a valid number of turns lost for a player.
	 * @param turns
	 * @return		true if the number is larger than or equal to zero.
	 */
	public boolean isValidTurnsLost(int turns){
		return (turns >= 0);
	}

	/**
	 * Returns whether the current player has already moved 
	 * 	since the last call of endTurn()
	 * @return
	 */
	public boolean hasMoved(){
		return moved;
	}

	/**
	 * A player doesn't bounce back a launchable item when he is hit by it.
	 */
	@Override
	public boolean bouncesBack() {
		return false;
	}

	/**
	 * Returns whether this player still has remaning actions.
	 * 
	 * @return	True	If this player has more then 0 remaining actions.
	 * 			False	otherwise.
	 * 			| getRemainingActions() > 0
	 */
	public boolean hasRemainingActions(){
		return getRemainingActions() > 0;
	}

	/**
	 * Returns whether the player covers the given square
	 * 
	 * @param 	square
	 * 			The square to check.
	 */
	@Override
	public boolean contains(Square square) {
		return square.equals(currentPosition);
	}

	/**
	 * Sets the start position for the player
	 * 
	 * @param	pos	the position to be used as the start position for the player
	 * @throws 	IllegalArgumentException
	 * 			If the given position is not a valid startposition
	 */
	private void setStartPosition(Square pos) throws IllegalArgumentException {
		if(!isValidStartPosition(pos))
			throw new IllegalArgumentException("The startposition of a player should not be obstructed");
		
		this.startPosition = pos;
		addSquare(startPosition);
	}
	
	public void setPosition(Square position){
		if(!isValidPosition(position))
			throw new IllegalStateException("Cannot set the player's position to a square that is obstructed.");		
		alertObservers();
		removeSquare(this.getPosition());
		addSquare(position);
	}

	/**
	 * Sets an inventory for the player
	 * @param 	inventory 
	 * 			The new inventory for the player.
	 * @throws 	IllegalArgumentException
	 * 			Thrown if the given inventory is not valid for the player.
	 */
	public void setInventory(PlayerInventory inventory) throws IllegalArgumentException{
		if(!isValidInventory(inventory))
			throw new IllegalArgumentException("The given inventory is not valid for the player.");
		
		this.inventory = inventory;
	}

	/**
	 * Returns the name of the player.
	 * 
	 * @return	The name of this player. 
	 * 
	 */
	public String getName(){
		return "Player " + getID();
	}

	/**
	 * Returns the player's id.
	 */
	public int getID() {
		return this.ID;
	}

	/**
	 * Returns the player's inventory.
	 * @return the inventory of this player
	 */
	public PlayerInventory getInventory(){
		return inventory;
	}

	/**
	 * Returns the current position of the player.
	 */
	@Basic
	public Square getPosition() {
		return currentPosition;
	}

	/**
	 * Returns the current remaining actions of the player.
	 */
	@Basic
	public int getRemainingActions(){
		return remainingActions;
	}

	/**
	 * Returns the start position of this player.
	 */
	public Square getStartPosition(){
		return startPosition;
	}

	/**
	 * Moves the player to another square
	 * 
	 * @param	newPosition	The new Position of the player
	 * @throws	IllegalStateException
	 * 		  	thrown if the player is unable to make this move 
	 */
	public void move(Square position) throws IllegalStateException{
		setPosition(position);
		moved = true;
		decrementActions();
	}
	
	/**
	 * @Pre	This must be called before making an action.
	 * 		Otherwise the currentPosition is the new one.
	 * 
	 * Decrements the remaining actions by one and notifies the observers of this player.
	 */
	public void decrementActions(){
		this.remainingActions--;
	}
	

	/**
	 * Causes a player to lose turns.
	 * @param turns		the number of turns a player loses.
	 * @throws 	IllegalArgumentException
	 * 			Thrown when the number of turns lost is not valid.
	 */
	public void loseTurns(int turns, boolean accumulating) throws IllegalArgumentException{
		if(!isValidTurnsLost(turns)){
				throw new IllegalArgumentException("The given turns lost are not valid (Should be a positive value)");
		}
		int remActions;
		if(!accumulating){
			remActions = 0;
		}else{
			remActions = remainingActions - Player.MAX_ALLOWED_ACTIONS * turns;
		}
		this.setRemainingActions(remActions);
	}
	

	/**
	 * Causes a player to lose actions
	 * @param actions	the number of actions a player loses.
	 * @throws 	IllegalArgumentException
	 * 			thrown when the number of actions lost is not valid.
	 */
	public void loseActions(int actions) throws IllegalArgumentException{
		if(!isValidActionsLost(actions)){
			throw new IllegalArgumentException("The given actions lost are not valid");
		}
		
		int remActions = remainingActions - actions;
		this.setRemainingActions(remActions);
	}
	
	/**
	 * This notifies the observers.
	 */
	public void alertObservers() {
		this.setChanged();
		this.notifyObservers(currentPosition);
	}
	

	/**
	 * Adds the item to the player's inventory.
	 * 
	 * @param	item
	 * @throws	IllegalArgumentException
	 * 			Thrown when adding the item would exceed the size of the inventory
	 */
	public void pickUp(Item item) throws IllegalArgumentException {
		if(!isValidPickUp(item))
			throw new IllegalArgumentException("The item cannot be added to the player inventory");
		inventory.addItem(item);
		
		alertObservers();
		decrementActions();
	}

	/**
	 * Method to select the item which the player is going to use
	 * 
	 * @param	itemToUse
	 * 			The item to use.
	 * @throws	IllegalStateException
	 * 			thrown when adding the item would exceed the size of the inventory.
	 * @throws	IllegalArgumentException
	 * 			If the item cannot be used on the current square.
	 */
	public void useItem(Item itemToUse) throws IllegalStateException,IllegalArgumentException {
		inventory.take(itemToUse);
		getPosition().getInventory().addItem(itemToUse);
		
		alertObservers();
		decrementActions();
	}
	
	/**
	 * End's the player his turn. Adds Player.MAX_ALLOWED_ACTIONS to the remaining actions.
	 * 
	 */
	public void endTurn(){
		// should perform empty action for every action left
		while (remainingActions > 0){
			this.setRemainingActions(remainingActions- 1);
			alertObservers();
		}
		remainingActions += Player.MAX_ALLOWED_ACTIONS;
		moved = false;
	}	

	/**
	 * Sets the remainingActions to the given remaining actions
	 * @param remainingActions	the new value for remaining actions
	 * @throws IllegalArgumentException
	 * 			thrown when the given remainingactions is not valid.
	 */
	public void setRemainingActions(int remainingActions) throws IllegalArgumentException{
		if(!isValidRemainingActions(remainingActions)){
			throw new IllegalArgumentException("remaining actions is not valid!");
		}
		
		this.remainingActions = remainingActions;
	}
	
	/**
	 * Adds a given square as a square covered by the player obstacle.
	 * 
	 * @param 	square
	 * 			The square to add.
	 * @throws	IllegalArgumentException
	 * 			If the given square can not be added as a square.
	 * 			| !isValidSquare()
	 */
	@Override
	public void addSquare(Square square) throws IllegalArgumentException {
		if(!isValidSquare(square))
			throw new IllegalArgumentException("The given " + square + " is not a valid square");
		currentPosition = square;
		square.setObstacle(this);
	}

	/**
	 * Removes the given square as a covered square of this obstacle.
	 * 
	 * @post	getPosition() == null
	 * @throws	IllegalArgumentException
	 * 			If the given square is not the currentPosition of this player.
	 * 			| !square.equals(getPosition())
	 */
	@Override
	public void removeSquare(Square square) throws IllegalArgumentException {
		if(!square.equals(this.getPosition()))
			throw new IllegalArgumentException("Can't remove the"+ square +" that is not covered by this player");
		currentPosition = null;
	}
	
	

	
	@Override
	public String toString() {
		return "Player " + this.getName();
	}
}
