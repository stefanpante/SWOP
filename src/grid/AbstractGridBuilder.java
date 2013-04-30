package grid;

import item.Item;

import java.util.ArrayList;
import java.util.Random;

import square.Square;
import square.obstacle.Wall;
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
	private GridConstraint constraintTeleport;
	private GridConstraint constraintLightGrenade;
	private GridConstraint constraintIdentityDisk;
	private GridConstraint constraintWall;
	
	/**
	 * The start position of the first player.
	 */
	private Square startPlayer1;
	
	/**
	 * The start position of the second player.
	 */
	private Square startPlayer2;
	
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

    /**
     * Initializes objects needed by the grid builder.
     */
	public AbstractGridBuilder(){
		this.walls = new ArrayList<Wall>();
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
	 * Adds squares to the grid
	 */
	protected void setSquares() {
		Coordinate coordinate;
		for(int x = 0; x < getGrid().getHSize(); x++){
			for(int y = 0; y < getGrid().getVSize(); y++){
				coordinate = new Coordinate(x, y);
				getGrid().setSquare(coordinate, new Square());
			}
		}		
	}
	
	/**
	 * Check whether the given list of coordinates satisfies the given constraint
	 * 
	 * @param 	coordinates
	 * 			The list of coordinates to be checked
	 * @param 	constraint
	 * 			The constraint to be satisfied
	 * @return	True if and only if the given list satisfies the given constraint
	 */
	protected boolean satisfiesConstraint(ArrayList<Coordinate> coordinates, GridConstraint constraint){
		if(coordinates == null || constraint == null)
			return false;
		float percentage = (float) coordinates.size() / (float) getAmountOfSquares();
		boolean[] includes = new boolean[constraint.getIncluded().size()];
		if(percentage > constraint.getPercentage())
			return false;
		for(Coordinate coordinate : coordinates){
			if(getGrid().getSquare(coordinate).isObstructed())
				return false;
			if(constraint.getExcluded().contains(coordinate))
				return false;
			int i = 0;
			for(ArrayList<Coordinate> include : constraint.getIncluded()){
				if(include.contains(coordinate))
					includes[i] = true;
				i++;
			}
		}
		for(boolean b : includes){
			if(!b)
				return false;
		}
		return true;
	}
	
	protected int getAmountOfSquares(){
		return getGrid().getHSize()*getGrid().getVSize();
	}
	
	

	/**
	 * Place an item on the given coordinate
	 * 
	 * @param 	square
	 * 			The coordinate to place the given item on
	 * @param 	item
	 * 			The item to be placed on the given coordinate
	 */
	protected void placeItem(Square square, Item item) throws IllegalArgumentException {
		if(square.isObstructed())
			return;
			//			throw new IllegalArgumentException("Cannot place an object on a square that is obstructed.");
		square.getInventory().addItem(item);
	}

    /**
     * Sets the start position of the first player
     * @param square
     */
	protected void setPlayerOneStart(Square square){
		this.startPlayer1 = square;
	}

    /**
     * Sets the start position of the second player
     * @param square
     */
	protected void setPlayerTwoStart(Square square){
		this.startPlayer2 = square;
	}

    /**
     * returns the start position of the first player
     */
	public Square getPlayerOneStart(){
		return this.startPlayer1;
	}

    /**
     * returns the start position of the second player
     */
	public Square getPlayerTwoStart(){
		return this.startPlayer2;
	}

    /**
     * Checks whether the built grid is valid
     * @return
     */
	public abstract boolean isConsistent();

    /**
     * Returns the constraint for the teleports.
     * @return
     */
	public GridConstraint getConstraintTeleport() {
		return constraintTeleport;
	}

    /**
     * sets the constraints for the teleport
     * @param constraintTeleport
     */
	public void setConstraintTeleport(GridConstraint constraintTeleport) {
		this.constraintTeleport = constraintTeleport;
	}

    /**
     * Returns the constraint for the lightgrenades
     * @return
     */
	public GridConstraint getConstraintLightGrenade() {
		return constraintLightGrenade;
	}

    /**
     * Sets the constraints for the lightgrenades
     */
	public void setConstraintLightGrenade(GridConstraint constraintLightGrenade) {
		this.constraintLightGrenade = constraintLightGrenade;
	}

    /**
     * Returns the constraints for the identity discs
     */
	public GridConstraint getConstraintIdentityDisk() {
		return constraintIdentityDisk;
	}

    /**
     * sets the constraints for the identity discs
     */
	public void setConstraintIdentityDisk(GridConstraint constraintIdentityDisk) {
		this.constraintIdentityDisk = constraintIdentityDisk;
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
     * Builds the grids
     * @throws IllegalStateException
     */
	protected abstract void build() throws IllegalStateException;

    /**
     * Sets the maximum horizontal size of the grid ( in squares)
     * @param hSize
     */
	public void setHSize(int hSize){
		if(!isValidHSize(hSize)){
			throw new IllegalArgumentException("The specified hSize is not valid");
		}
		this.hSize = hSize;
	}

    /**
     * sets the maximum vertical size of the grid ( in squares)
     * @param vSize
     */
	public void setVSize(int vSize){
		if(!isValidVSize(vSize)){
			throw new IllegalArgumentException("The specified vSize is not valid");
		}
		
		this.vSize = vSize;
	}

    /**
     * Checks  whether the vertical size of the grid is valid
     * @param vSize2
     */
	public boolean isValidVSize(int vSize2) {
		return (vSize2 >= 0);
	}

    /**
     * Checks whether the horizontal size of the grid is valid
     * @param hSize
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


    protected int getRandomIndex(ArrayList a){
        return getRandom().nextInt(a.size());
    }

    protected ArrayList<Coordinate> randomLocations(GridConstraint constraint){
        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
        ArrayList<Coordinate> candidates = getGrid().getAllCoordinates();
        int max = (int) (constraint.getPercentage() * getAmountOfSquares());
        // Removed excluded squares from candidates
        candidates.removeAll(constraint.getExcluded());
        // Removed obstructed squares from candidates
        ArrayList<Coordinate> toBeRemoved = new ArrayList<Coordinate>();
        for(Coordinate coordinate : candidates){
            if(getGrid().getSquare(coordinate).isObstructed())
                toBeRemoved.add(coordinate);
        }
        candidates.removeAll(toBeRemoved);

        // Add one square from evey list of included coordinates
        for(ArrayList<Coordinate> includes : constraint.getIncluded()){
            Coordinate candidate = includes.get(getRandomIndex(includes));
            while(!candidates.contains(candidate)){
                candidate = includes.get(getRandomIndex(includes));
            }
            coordinates.add(candidate);
            candidates.remove(candidate);
        }

        // Keep adding coordinates from candidates until max is reached
        while(coordinates.size() < max){
            Coordinate candidate = candidates.get(getRandomIndex(candidates));
            coordinates.add(candidate);
            candidates.remove(candidate);
        }

        return coordinates;
    }

    /**
     * Place walls on the given coordinates
     *
     * @param 	walls
     * 			The coordinates where to place the walls
     * @param 	constraint
     * 			The constraint for placing walls;
     */
    protected void placeWalls(ArrayList<ArrayList<Coordinate>> walls) {
        if(!satisfiesConstraint(flatten(walls), getConstraintWall()))
            throw new IllegalArgumentException("The given coordinates do not satisfy the given constraint");
        for(ArrayList<Coordinate> sequence : walls){
            this.walls.add(new Wall(getGrid().getSquares(sequence)));
        }
    }

    private ArrayList<Coordinate> flatten(ArrayList<ArrayList<Coordinate>> list) {
        ArrayList<Coordinate> result = new ArrayList<Coordinate>();
        for(ArrayList<Coordinate> L : list){
            result.addAll(L);
        }
        return result;
    }
	
}
