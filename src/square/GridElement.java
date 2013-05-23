package square;

import util.Direction;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * User: jonas
 * Date: 23/05/13
 * Time: 14:39
 */
public abstract class GridElement {

    /**
     * Contains the neighbors of this square
     */
    private HashMap<Direction, Square> neighbors;

    /**
     * Sets all the neighbors of the square instance
     *
     * @param   neighbors
     *          The neighbors to be set.
     */
    public void setNeighbors(HashMap<Direction, Square> neighbors){
        this.neighbors = neighbors;
    }

    /**
     * Returns the neighbors of the square.
     *
     * @return  The neighbors of this square.
     */
    public HashMap<Direction, Square> getNeighbors(){
        return new HashMap<Direction, Square>(neighbors);
    }

    /**
     * Returns the neighbor in the given direction
     * @param direction	The direction of the neighbor.
     * @return the neighbor in the given direction
     */
    public Square getNeighbor(Direction direction) throws NoSuchElementException {
        if(!neighbors.containsKey(direction))
            throw new NoSuchElementException("There is no neighbor in the given direction (" + direction + ")");
        return neighbors.get(direction);
    }

    public abstract boolean isObstacle();
}
