package player;

import grid.Square;
import items.Inventory;
import items.Item;

/**
 * Player class
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers en Stefan Pante
 *
 */
public class Player implements IPlayer {

	
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
	 * checks if the given position is a valid start position for the player
	 * @param pos
	 * @return  true if the position is a valid startposition
	 * 			false otherwise
	 */
	//TODO: implement this, depends on the decision to implement neighbours in square
	public boolean isValidStartPosition(Square pos){
		return false;
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
	private void setStartPosition(Square pos) throws IllegalArgumentException{
		if(!isValidStartPosition(pos))
			throw new IllegalArgumentException("Square is not a valid startposition!");
		else {
			this.startPosition = pos;
			this.currentPosition = startPosition;
		}
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

	public void pickUp(Item item) {
		// TODO Auto-generated method stub
		// check own inventory limits
		// TODO: check the lighttrails
		
	}

	/**
	 * Method to select the item which the player is going to use
	 */
	public void useItem(int index) {
		Item item = items.getItem(index);
		currentPosition.addUsedItem(item);
		
	}
	
	public Inventory getInventory(){
		return items;
	}
	/**
	 * sets an inventory for the player
	 * @param inventory the new inventory for the player
	 * @throws IllegalArgumentException
	 * 			thrown if the given inventory is not valid for the player
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


	/**
	 * the Player picks up an item from the playing field
	 * @pre getActions < 3
	 * @pre if(!moved) actions < 3-1 if the user hasn't moved, he must have more than one action left
	 */
	// TODO: Violates the GRASP principles?
	public void pickUp(int index){
		//TODO: add update for lighttrail
		Item item = currentPosition.getItems().getItem(index);
		items.addItem(item);
		
		
	}
	/**
	 * 
	 */
	@Override
	public void endTurn() {
		//TODO: add update for lighttrail
		
		// activates all items used on the current square
		currentPosition.activateUsedItems();
		// needed to check for 3 actions and the actions itself is needed for the lighttrail.
		this.previousactions = actions;
		
	}

}
