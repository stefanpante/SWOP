package player;

import java.util.Observable;

import notnullcheckweaver.NotNull;
import notnullcheckweaver.Nullable;

import square.Square;
import square.obstacles.IObstacle;

import items.Item;
import items.PlayerInventory;

/**
 * Player class
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers en Stefan Pante
 *
 */
@NotNull
public class Player extends Observable implements IObstacle {

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
	 * The name of this player
	 */
	private String name;
	
	/**
	 * The inventory of the player
	 */
	private PlayerInventory inventory;
	
	/**
	 * The player's ID.
	 */
	private int ID = -1;
	
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
	public Player(Square startPosition, String name) throws IllegalArgumentException {
		this.setStartPosition(startPosition);
		this.setName(name);
		this.setInventory(new PlayerInventory());
		
		this.remainingActions = MAX_ALLOWED_ACTIONS;
		this.moved = false;
	}
	
	/**
	 * returns true for now
	 * @param name 	the name which has to be checked
	 * @return true if the given name is a valid name
	 * 		   otherwise false
	 */
	public static boolean isValidName(String name){
		if(name == null)
			return false;
		
		return true;
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
	public static boolean isValidMove(Square newPosition) {
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
	public boolean isValidSquare(Square square) {
		if(square == null)
			return false;
		if(square == currentPosition)
			return false;
		return true;
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
	
	/**
	 * Moves the player to another square
	 * 
	 * @param	newPosition	The new Position of the player
	 * @throws	IllegalStateException
	 * 		  	thrown if the player is unable to make this move 
	 */
	public void move(Square newPosition) throws IllegalStateException{
		if(!isValidMove(newPosition))
			throw new IllegalStateException("Cannot move to a square that is obstructed");
		
		removeSquare(this.getPosition());
		addSquare(newPosition);
		moved = true;
	}
	
	/**
	 * Increments the remaining actions by one and notifies the observers of this player.
	 */
	public void incrementActions(){
		this.remainingActions++;
		this.setChanged();
		this.notifyObservers(currentPosition);
	}
	
	/**
	 * @pre	This must be called before making an action.
	 * 		Otherwise the currentPosition is the new one.
	 * 
	 * Decrements the remaining actions by one and notifies the observers of this player.
	 */
	public void decrementActions(){
		this.remainingActions--;
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
	}
	
	/**
	 * Returns the name of the player.
	 * 
	 * @return	The name of this player. 
	 * 
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Returns the player's id.
	 * @return
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
	 * returns the current position of the player
	 */
	public Square getPosition() {
		return currentPosition;
	}
	
	public int getRemainingActions(){
		return remainingActions;
	}
	
	/**
	 * returns the startposition of this player
	 * @return Square startposition
	 */
	public Square getStartPosition(){
		return startPosition;
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
	 * sets the name for the player
	 * @param 	name	the name which is given to the player
	 * @throws 	IllegalArgumentException
	 * 			thrown if the given name is not a valid name 
	 * 			for the player
	 */
	public void setName(String name) throws IllegalArgumentException{
		if(!isValidName(name)) 
			throw new IllegalArgumentException("Not a valid name");
		
		this.name = name;
	}
	
	/**
	 * Set's the player id.
	 * 
	 * @param	id
	 * @throws	IllegalStateException	If the ID is already set it cannot be overwritten.
	 */
	public void setID(int id) throws IllegalStateException {
		if(this.ID != -1)
			throw new IllegalStateException("The player ID has already been set.");
		
		this.ID = id;
	}
	
	/**
	 * Used to set the remaining actions for this player.
	 * @param actions	the number of actions the player can perform.
	 */
	private void setRemainingActions(int actions){
		if(!isValidRemainingActions(actions)){
			throw new IllegalArgumentException("This is not a valid number for remaining actions");
		}
		this.remainingActions = actions;
	}
	
	public boolean hasMoved(){
		return moved;
	}

	public boolean hasRemainingActions(){
		return getRemainingActions() > 0;
	}
	
	/**
	 * End's the player his turn. Sets the player his remaining actions 
	 * for the new turn to the maximum allowed actions.
	 */
	public void endTurn(){
		setRemainingActions(Player.MAX_ALLOWED_ACTIONS);
		
		moved = false;
	}
	
	/**
	 * End's the player his turn and sets the new remaining actions, depending
	 * on the conditions in which the turn was ended
	 * 
	 * @param	lostActions	Number of actions lost for the next turn.
	 */
	public void endTurn(int newActions) {
		setRemainingActions(newActions);
		moved = false;
	}
	
	/**
	 * Returns whether the player covers the given square
	 * 
	 * @param 	square
	 * 			The square to check.
	 */
	public boolean contains(Square square) {
		return square.equals(currentPosition);
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
	public void addSquare(Square square) throws IllegalArgumentException {
		if(!isValidSquare(square))
			throw new IllegalArgumentException("The given " + square + " is not a valid square");
		
		currentPosition = square;
		square.setObstacle(this);
	}

	/**
	 * Removes the given square as a covered square of this obstacle.
	 */
	public void removeSquare(Square square) throws IllegalArgumentException {
		if(!square.equals(currentPosition))
			throw new IllegalArgumentException("Can't remove the"+ square +" that is not covered by this player");
		
		currentPosition = null;
		square.setObstacle(null);
	}

	
	@Override
	public String toString() {
		return "Player " + this.getName();
	}
}
