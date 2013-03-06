package board.obstacles;

import java.util.ArrayList;

import board.Square;



/**
 * Wall class
 * 
 * @pre	The squares added to the wall must be different.
 * 
 * @invariant The minimum amount of squares the wall covers is 2.
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers en Stefan Pante
 */
public class Wall{
	
	public static int MIN_SIZE = 2;
	ArrayList<Brick> bricks;
	
	public Wall(ArrayList<Brick> bricks){
		this.bricks = bricks;
	}
	
	public int length(){
		return bricks.size();
	}
}
