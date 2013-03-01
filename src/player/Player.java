package player;

import java.util.Observable;

import square.Square;

import items.Inventory;
import items.Item;

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
	private Inventory items;
	
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
	 * The number of items a player can carry
	 */
	private static int INVENTORY_SIZE = 6;
	
	/**
	 * The amount of action a player has during one move
	 */
	private static int ACTIONS = 3;
	
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
		
		this.items = new Inventory(INVENTORY_SIZE);
		this.actions = 0;
		this.previousactions = 0;
		this.moved = false;
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
	
	//TODO: when is a move valid?
	public boolean isValidMove(Square pos){
		return false;
	}
	
	/**
	 * Moves the player to another square
	 * @param newPosition	The new Position of the player
	 * @throws IllegalStateException
	 * 		   thrown if the player is unable to make this move 
	 */
	public void move(Square newPosition) throws IllegalStateException{
		if(isValidMove(newPosition)) throw new IllegalStateException("Not a valid move");
		else{
			currentPosition = newPosition;
			currentPosition.activateUsedItems();
			moved = true;
			//TODO check and add lighttrail.
		}
		
	}
	
	public void incrementActions(){
		this.actions++;
	}

	public void pickUp(Item item) {
		// TODO Auto-generated method stub
		// check own inventory limits
		
	}

	/**
	 * Method to select the item which the player is going to use
	 */
	public void useItem(Item itemToUse) throws IllegalArgumentException {
		if(!canUseItem(itemToUse)){
			throw new IllegalArgumentException();
		}
		items.take(itemToUse);
		getPosition().addUsedItem(itemToUse);
	}
	
	public Inventory getInventory(){
		return items;
	}
	/**
	 * Sets an inventory for the player
	 * @param 	inventory 
	 * 			The new inventory for the player.
	 * @throws 	IllegalArgumentException
	 * 			Thrown if the given inventory is not valid for the player.
	 */
	public void setInventory(Inventory inventory) throws IllegalArgumentException{
		if(!isValidInventory(inventory)) 
			throw new IllegalArgumentException("This inventory is invalid for this player");
		else this.items = inventory;
	}

	//TODO check inventory
	public boolean isValidInventory(Inventory inventory){
		return false;
	}
	/**
	 * returns the current position of the player
	 */
	public Square getPosition() {
		return currentPosition;
	}


	
	
	public int getRemainingActions(){
		return ACTIONS - this.actions;
	}

	public boolean hasRemainingActions(){
		return getRemainingActions() > 0;
	}
	
	public void endTurn() {
		//TODO: add update for lighttrail
		
		// activates all items used on the current square
		currentPosition.activateUsedItems();
		// needed to check for 3 actions and the actions itself is needed for the lighttrail.
		this.previousactions = actions;
		
	}
	
	private boolean canUseItem(Item item){
		if(!hasRemainingActions())
			return false;
		if(!getPosition().canBeUsedHere(item))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Player " + this.getName();
	}

}
