package square;

import square.multi.Wall;

/**
 * User: jonas
 * Date: 23/05/13
 * Time: 14:39
 */
public class Brick extends GridElement {

	/**
	 * The wall to which this brick belongs.
	 */
	private Wall wall;
	
	/**
	 * Constructs a new brick object.
	 */
	public Brick(){
		
	}
	
    @Override
    public boolean isObstacle() {
        return true;
    }

    
    /**
     * sets the wall to which is this bricks belongs.
     * @param   wall
     *          The wall to be added.
     *
     */
    public void addWall(Wall wall){
    	if(!isValidWall(wall)){
    		throw new IllegalArgumentException("A null wall cannot be added to the Brick");
    	}
    	
    	this.wall = wall;
    }
    
    /**
     * Returns whether the given wall is valid for this brick.
     * @param wall		the wall to be checked
     */
    public boolean isValidWall(Wall wall){
    	return wall != null;
    }
    
    public Wall getWall(){
    	return this.wall;
    }
    
    @Override
    public boolean isSameType(GridElement gridElement) {
        return gridElement instanceof Brick;
    }
}
