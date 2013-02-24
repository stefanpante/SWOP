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
	private Square position;
	
	/**
	 * The name of this player
	 */
	private String name;
	
	
	public Player(Square startPosition, String name) {
		this.setStartPosition(startPosition);
		this.setName(name);
	}

	//TODO: implement this
	public boolean isValidStartPosition(Square pos){
		return false;
	}
	
	public void setName(String name){
		if(!isValidName(name)) throw new IllegalArgumentException("Not a validname");
		this.name = name;
	}
	/**
	 * returns true for now
	 * @param name
	 * @return
	 */
	public boolean isValidName(String name){
		return true;
	}
	private void setStartPosition(Square pos){
		if(!isValidStartPosition(pos))
			throw new IllegalArgumentException("Square is not a valid startposition!");
		else this.startPosition = pos;
	}
	
	//TODO: when is a move valid?
	public boolean isValidMove(Square pos){
		return false;
	}
	public void move(Square newPosition) {
		// TODO Auto-generated method stub
		
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
