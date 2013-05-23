package grid;

import item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import util.Direction;
import square.GridElement;
import square.Square;
import square.multi.Wall;
import util.Coordinate;

import be.kuleuven.cs.som.annotate.Basic;

/**
 * Super class for implementations of grid builder.
 * Grid builder is responsible for constructing all the
 * squares of which the grid is composed. It also places walls,
 * teleports and all items.
 *
 */
public abstract class AbstractGridBuilder {


    /**
     * The widest point of the grid ( in squares )
     */
	private int hSize;

    /**
     * The maximum height of the grid ( in squares )
     */
	private int vSize;

	/**
	 * the constraints for the grid.
	 */
	private GridConstraint constraintWall;
	
	/**
	 * The grid which has been/will be  built.
	 */
	private Grid grid;
	
	/**
	 * a random generator used in various creation methods
	 */
	private Random random;
	
	/**
	 * The walls which are placed on the grid.
	 */
	protected ArrayList<Wall> walls;

	
	protected HashMap<Coordinate, GridElement> gridElements;
	

	
    /**
     * Initializes objects needed by the grid builder.
     */
	public AbstractGridBuilder(){
		this.walls = new ArrayList<Wall>();
		this.gridElements = new HashMap<Coordinate, GridElement>();
	}
	
	/**
	 * Returns the grid.
	 * 
	 * This method should only be called when the gridBuilder has done all his work.
	 */
	@Basic
	public Grid getGrid() {
		return grid;
	}
	
	/**
	 * Sets the given grid as the grid of this gridBuilder.
	 * 
	 * @param grid the grid to set
	 */
	protected void setGrid(Grid grid) {
		this.grid = grid;
	}
	
	/**
	 * Returns the random generator.
	 * 
	 * @return the random generator.
	 */	
	protected Random getRandom() {
		return random;
	}
	
	/**
	 * Sets the given random as the random of this GridBuilder.
	 * 
	 * @param random the random to set
	 */
	protected void setRandom(Random random) {
		this.random = random;
	}

    /**
     * Returns the constraints for the walls
     */
	public GridConstraint getConstraintWall() {
		return constraintWall;
	}

    /**
     * Returns the constraints for the walls
     */
	public void setConstraintWall(GridConstraint constraintWall) {
		this.constraintWall = constraintWall;
	}

    /**
     * Sets the maximum horizontal size of the grid ( in squares)
     * @param hSize the horizontal size of the grid.
     */
	public void setHSize(int hSize){
		if(!isValidHSize(hSize)){
			throw new IllegalArgumentException("The specified hSize is not valid");
		}
		this.hSize = hSize;
	}

    /**
     * sets the maximum vertical size of the grid ( in squares)
     * @param vSize the vertical size of the grid.
     */
	public void setVSize(int vSize){
		if(!isValidVSize(vSize)){
			throw new IllegalArgumentException("The specified vSize is not valid");
		}
		
		this.vSize = vSize;
	}

    /**
     * Checks  whether the vertical size of the grid is valid
     * @param vSize2  the vertical size of the grid.
     */
	public boolean isValidVSize(int vSize2) {
		return (vSize2 >= 0);
	}

    /**
     * Checks whether the horizontal size of the grid is valid
     * @param hSize the horizontal size of the grid.
     */
	public boolean isValidHSize(int hSize){
		return (hSize >= 0);
	}

    /**
     * Returns the maximum horizontal size of the grid ( in squares)
     */
	public int getHSize(){
		return this.hSize;
	}

    /**
     * returns the maximum vertical size of the grid ( in squares)
     */
	public int getVSize(){
		return this.vSize;
	}


    /**
     * Returns the walls which have been constructed.
     */
	protected ArrayList<Wall> getWalls() {
		return this.walls;
	}

    /**
     * Place walls on the given coordinates
     *
     * @param 	walls
     * 			The coordinates where to place the walls
     */
    protected void placeWalls(ArrayList<ArrayList<Coordinate>> walls) {
        if(!getConstraintWall().satisfiesConstraint(flatten(walls), getGrid()))
            throw new IllegalArgumentException("The given coordinates do not satisfy the given constraint");
        for(ArrayList<Coordinate> sequence : walls){
        	ArrayList<Square> toRemove = getGrid().getGridElements(sequence);
            this.walls.add(new Wall());
        }
    }

	/**
	 * Utility method that flattens a two dimensional Arraylist. 
	 * 
	 * @return  a flattened version of the list
	 */
    private ArrayList<Coordinate> flatten(ArrayList<ArrayList<Coordinate>> list) {
        ArrayList<Coordinate> result = new ArrayList<Coordinate>();
        for(ArrayList<Coordinate> L : list){
            result.addAll(L);
        }
        return result;
    }

    
    /**
     * Sets the neighbors of each square.
     */
    protected void setNeighbors(){
    	for(Coordinate coordinate: this.gridElements.keySet()){
    		GridElement element = gridElements.get(coordinate);
    		HashMap<Direction, GridElement> neighbors = new HashMap<Direction,GridElement>();
    		HashMap<Direction, Coordinate> neighborCoordinates = coordinate.getAllNeighbors();
    		for(Direction direction: neighborCoordinates.keySet())
    			this.gridElements.get(direction)
    	}
    }
    
    /**
     * Adds the squares to the grid.
     */
    protected abstract void setSquares();

    /**
     * Builds the grids
     * @throws IllegalStateException
     */
	protected abstract void build() throws IllegalStateException;

	/**
	 * Returns every possible start position of each player.
	 */
	public abstract ArrayList<Coordinate> getStartPositions();
	
}
