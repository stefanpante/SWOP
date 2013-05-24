package square.multi;


import square.Brick;

import java.util.ArrayList;


/**
 * Brick class
 * 
 * @pre	The bricks added to the wall must be different.
 * 
 * @invariant The minimum amount of bricks the wall covers is 2.
 * 
 * @author Dieter Castel, Jonas Devlieghere   en Stefan Pante
 */
public class Wall extends MultiGridElement<Brick> {
	
	public static int MIN_SIZE = 2;
	
	/**
	 * Initialises the wall. Two bricks must at least be given for a wall to be possible.
	 * 
	 * @param	brick
     *          First brick.
	 * @param	otherBrick
     *          Second brick.
	 * @throws  IllegalArgumentException
     *          If a duplicate brick is given an exception is thrown.
	 */
	public Wall(Brick brick, Brick otherBrick) throws IllegalArgumentException {
		super();
		this.addGridElement(brick);
		this.addGridElement(otherBrick);
	}
	
	/**
	 * Constructs a wall from an arrayList
     *
	 * @param 	sequence the Sequence of which a wall should be created
	 */
	public Wall(ArrayList<Brick> sequence) throws IllegalArgumentException {
		if(sequence.size() < 2) 
			throw new IllegalArgumentException("A wall should have at least two bricks");
		for(Brick brick: sequence){
			assert brick != null;
			brick.addWall(this);
			super.addGridElement(brick);
		}
	}

}
