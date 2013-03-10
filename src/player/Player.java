package player;

import java.util.Observable;

import square.Square;
import square.obstacles.LightTrail;

import items.Inventory;
import items.Item;
import items.PlayerInventory;

/**
 * Player class
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers en Stefan Pante
 *
 */
public class Player extends Observable {

	/**
	 * The start position of this player
	 */
	private Square startPosition;
	
	/**
	 * the current position of the player
	 */
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
	 * keeps track of the number of actions this user has done;
	 */
	private int actions;
	
	/**
	 * The number of action when the last turn ended
	 */
	private int previousactions;
	
	/**
	 * true if the user has moved during his turn
	 */
	private boolean moved;
	
	/**
	 * The amount of action a player has during one move
	 */
	public static int MAX_ALLOWED_ACTIONS = 3;
	
	private LightTrail lightTrail;
	
	/**
	 * creates a new player with a given name and start position
	 * 
	 * @param startPosition the startposition for the player
	 * @param name	the name for the player
	 * @effect setStartPosition(startPosition)
	 * @effect setName(name)
	 */
	public Player(Square startPosition, String name) {
		this.setStartPosition(startPosition);
		this.setName(name);
		
		this.inventory = new PlayerInventory();
		this.actions = 0;
		this.previousactions = 0;
		this.moved = false;
		this.lightTrail = new LightTrail();
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
	 * sets the name for the player
	 * @param 	name	the name which is given to the player
	 * @throws 	IllegalArgumentException
	 * 			thrown if the given name is not a valid name 
	 * 			for the player
	 */
	public void setName(String name) throws IllegalArgumentException{
		if(!isValidName(name)) throw new IllegalArgumentException("Not a valid name");
		this.name = name;
	}
	
	/**
	 * returns true for now
	 * @param name 	the name which has to be checked
	 * @return true if the given name is a valid name
	 * 		   otherwise false
	 */
	public boolean isValidName(String name){
		return true;
	}
	
	/**
	 * Sets the start position for the player
	 * 
	 * @param pos the position to be used as the start position for the player
	 * @throws 	IllegalArgumentException
	 * 			If the given position is not a valid startposition
	 */
	private void setStartPosition(Square pos){
		this.startPosition = pos;
		this.currentPosition = startPosition;
	}
	
	/**
	 * returns the startposition of this player
	 * @return Square startposition
	 */
	public Square getStartPosition(){
		return startPosition;
	}
	
	/**
	 * 
	 */
	
	/**
	 * Moves the player to another square
	 * @param newPosition	The new Position of the player
	 * @throws IllegalStateException
	 * 		   thrown if the player is unable to make this move 
	 */
	//TODO: Needs to throw an IllegalStateException when the newPosition is invalid.
	public void move(Square newPosition) throws IllegalStateException{
		if(!currentPosition.canMoveTo(newPosition))
			throw new IllegalStateException("Cannot move to the given square");
		currentPosition = newPosition;
		moved = true;
	}
	
	public void incrementActions(){
		this.actions++;
		this.getLightTrail().update(this, currentPosition);
	}

	/**
	 * Adds the item to the player's inventory.
	 * 
	 * @param	item
	 * @throws	IllegalArgumentException
	 * 			Thrown when adding the item would exceed the size of the inventory
	 */
	public void pickUp(Item item) throws IllegalArgumentException {
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
		getPosition().getUsedInventory().addItem(itemToUse);
	}
	
	/**
	 * Returns the player's inventory.
	 * @return the inventory of this player
	 */
	public PlayerInventory getInventory(){
		return inventory;
	}
	
	/**
	 * Sets an inventory for the player
	 * @param 	inventory 
	 * 			The new inventory for the player.
	 * @throws 	IllegalArgumentException
	 * 			Thrown if the given inventory is not valid for the player.
	 */
	public void setInventory(PlayerInventory inventory) throws IllegalArgumentException{
		this.inventory = inventory;
	}
	
	/**
	 * returns the current position of the player
	 */
	public Square getPosition() {
		return currentPosition;
	}
	
	public boolean hasMoved(){
		return moved;
	}
	
	public int getRemainingActions(){
		return (this.previousactions + this.MAX_ALLOWED_ACTIONS) - this.actions;
	} 

	public boolean hasRemainingActions(){
		return getRemainingActions() > 0;
	}
	
	/**
	 * End's the player his turn.
	 */
	public void endTurn() {
		this.previousactions += MAX_ALLOWED_ACTIONS;
		actions = previousactions;
		moved = false;
	}
	
	public LightTrail getLightTrail(){
		return lightTrail;
	}
	
	@Override
	public String toString() {
		return "Player " + this.getName();
	}

}
