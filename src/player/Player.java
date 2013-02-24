package player;

import grid.Square;
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
	 * Sets the startposition for the player
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
	public void move(Square newPosition) throws IllegalStateException{
		if(isValidMove(newPosition)) throw new IllegalStateException("Not a valid move");
		else currentPosition = newPosition;
		
	}

	public void pickUp(Item item) {
		// TODO Auto-generated method stub
		
	}

	public void useItem(Item item) {
		// TODO Auto-generated method stub
		
	}

	public void endTurn() {
		// TODO Auto-generated method stub
		
	}

	public Square getPosition() {
		return position;
	}

}
