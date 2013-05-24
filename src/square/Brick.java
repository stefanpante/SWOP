package square;

import org.jetbrains.annotations.Nullable;
import square.multi.Wall;

/**
 * User: jonas
 * Date: 23/05/13
 * Time: 14:39
 */
public class Brick extends GridElement {

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
    	if(isNotValidWall(wall)){
    		throw new IllegalArgumentException("A null wall cannot be added to the Brick");
    	}
    	
    	/*
	  The wall to which this brick belongs.
	 */
        Wall wall1 = wall;
    }
    
    /**
     * Returns whether the given wall is valid for this brick.
     * @param wall		the wall to be checked
     */
    boolean isNotValidWall(@Nullable Wall wall){
    	return wall == null;
    }
    
    @Override
    public boolean isSameType(GridElement gridElement) {
        return gridElement instanceof Brick;
    }
}
