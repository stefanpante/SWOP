package grid;

import java.util.ArrayList;
import java.util.Random;
import util.Direction;
import square.Square;
import util.Coordinate;

/**
 * @author Jonas Devlieghere, Dieter Castel
 *
 */
public class RandomGridBuilder extends AbstractGridBuilder{
	
	public static int MIN_HSIZE = 10;
	public static int MIN_VSIZE = 10;
	
	/**
	 * This creates a flat grid, with dimensions 10x10 and no
	 * obstacles. No lightGrenades or any other object. This is mainly used
	 * for testing purposes.
	 */
	public RandomGridBuilder() {
		setHSize(10);
        setVSize(10);
		setRandom(new Random());
		setSquares();
		setNeighbors();
		setGrid(new Grid(getHSize(),getVSize(), this.gridElements));
		setStartPositions();
		setEmptyConstraints();
	}
	
	/**
	 * Creates a new GridBuilder with parameters to create a new grid.
	 * 
	 * This constructor will place random Walls, LightGrenades, 
	 * identityDiscs and Teleports according to the constraints.
	 * 
	 * @param 	hSize		
	 * 			The horizontal size of the grid this gridBuilder will build.
	 * @param 	vSize		
	 * 			The vertical size of the grid this gridBuilder will build.
	 */
	public RandomGridBuilder(int hSize, int vSize) {
		super();
		setHSize(hSize);
        setVSize(vSize);
		setRandom(new Random());
		build();
	}

    /**
	 *  Creates a new GridBuilder with parameters to create a new grid.
	 * 	Currently the given coordinates will be checked on the usual constraints!
	 * 	Keep this in mind when building test grids.
	 * 
	 * @param 	hSize		
	 * 			The horizontal size of the grid this gridBuilder will build.
	 * @param 	vSize		
	 * 			The vertical size of the grid this gridBuilder will build.
	 * @param	walls
	 * 			A list of sequences of coordinates where walls should be placed.
	 * @param	lightGrenades
	 * 			A list of coordinates that should contain a LightGrenade.
	 * @param	identityDisks
	 * 			A list of coordinates that should contain a identityDisk	 
	 * @param	teleports
	 * 			A list of coordinates that should contain a teleport.
	 */
	protected RandomGridBuilder(int hSize, int vSize, ArrayList<ArrayList<Coordinate>> walls){
		super();
		setHSize(hSize);
	    setVSize(vSize);
		setRandom(new Random());
		setSquares();
		setEmptyConstraints();
		//Walls are build explicitly first cause other randomLocations depend on the placed obstacles.
		build(walls);
		
	}

	/**
	 * Checks if the number of horizontal Squares is valid to create a grid with.
	 */
	@Override
    public boolean isValidHSize(int hSize){
        return ( hSize >= MIN_HSIZE);
    }

	/**
	 * Checks if the number of vertical Squares is valid to create a grid with.
	 */
    @Override
    public boolean isValidVSize(int vSize){
        return (vSize >= MIN_VSIZE);
    }
	
    /**
     * Builds the actual grid.
     */
	@Override
	protected void build() throws IllegalStateException {
		setSquares();
		setNeighbors();
		setConstraints();
		placeWalls(randomWallLocations(getConstraintWall()));
		setGrid(new Grid(getHSize(),getVSize(), this.gridElements));
		setStartPositions();
	}

	/**
	 * Utility build method used for testing purposes.
	 * @param lightGrenades  The lightGrenades to set on the grid.
	 * @param identityDisks  The lightGrenades to set on the grid.
	 * @param teleports      The lightGrenades to set on the grid.
	 * @param fFgen            The lightGrenades to set on the grid.
	 * @param chargedIdentityDisk  the location of the chargedIdentityDisk
	 * @throws IllegalStateException
	 */
	protected void build(ArrayList<ArrayList<Coordinate>> walls)
	throws IllegalStateException{
		setSquares();
		setNeighbors();
		placeWalls(walls);
		setGrid(new Grid(getHSize(),getVSize(), this.gridElements));
		setStartPositions();
		
	}
	

	private void setConstraints(){
		ArrayList<Coordinate> excluded = new ArrayList<Coordinate>();
		for(Coordinate coor: getStartCoordinates()){
			excluded.add(coor);
		}
		setConstraintWall(new GridConstraint(Grid.PERCENTAGE_WALLS, excluded));
	}
	
	private void setEmptyConstraints(){		
		setConstraintWall(new GridConstraint());
	}
	
	/**
	 * Returns a list of coordinates representing a wall.
	 * 
	 * @param 	candidates
	 * 			A list of coordinates that are candidates for having a wall.
	 * @param 	maxWallLength
	 * 			The maximum amount of squares a single wall can cover.
	 * @return	A list of coordinates that now have a wall placed on it.
	 */
	ArrayList<Coordinate> getWall(ArrayList<Coordinate> candidates, int maxWallLength){
		ArrayList<Coordinate> wall = new ArrayList<Coordinate>();
		Direction direction = Direction.getRandomOrientation();
		int maxPercentageLength;
		/* Determine length */
		if(direction == Direction.NORTH){
			maxPercentageLength = Math.round(Grid.LENGTH_PERCENTAGE_WALL * getGrid().getVSize());
		}else{
			maxPercentageLength = Math.round(Grid.LENGTH_PERCENTAGE_WALL * getGrid().getHSize());
		}
		int length = Math.min(maxWallLength, maxPercentageLength);
		/* Choose start candidate and start constructing wall */
		Coordinate start = candidates.get(getRandom().nextInt(candidates.size()));
		Coordinate next = start.getNeighbor(direction);
		/* As long as the length is within range and there is a square, continue */
		while(wall.size() < length && candidates.contains(next)){
			wall.add(next);
			next = next.getNeighbor(direction);
		}
		return wall;
	}
	
	
	/**
	 * Removes all squares around a wall of a list of candidates
	 * 
	 * @param 	coordinates
	 * 			The coordinates of which the perimeter should be removed
	 * @param 	candidates 
	 * 			the list of squares of which the perimeter of the wall will be removed.
	 */
	void removePerimeter(ArrayList<Coordinate> coordinates, ArrayList<Coordinate> candidates) {
		for(Coordinate coordinate : coordinates){
			for(Direction direction : Direction.values()){
				Coordinate neighbor = coordinate.getNeighbor(direction);
				candidates.remove(neighbor);
			}
			candidates.remove(coordinate);
		}
	} 
    
    private ArrayList<Coordinate> getStartCoordinates(){
    	ArrayList<Coordinate> startPositions = new ArrayList<Coordinate>();
    	
    	
    	startPositions.add(new Coordinate(0, getGrid().getVSize() -1));
    	startPositions.add(new Coordinate(getGrid().getHSize() - 1, 0));
    	startPositions.add(new Coordinate(0,0));
    	startPositions.add(new Coordinate(getGrid().getHSize()-1, getGrid().getVSize() -1));
    	
    	return startPositions;
    }

	/**
	 * Finds a random list of sequences of coordinates that follow the given constraint.
	 * 
	 * @param 	constraint
	 * 			The constraint which is taken into account.
	 * @return	A list of sequences of coordinates.
	 */
	protected ArrayList<ArrayList<Coordinate>> randomWallLocations(GridConstraint constraint) {
		ArrayList<Coordinate> candidates = getGrid().getAllCoordinates();
		ArrayList<ArrayList<Coordinate>> result = new ArrayList<ArrayList<Coordinate>>();
		
		// Removed excluded squares from candidates
		candidates.removeAll(constraint.getExcluded());

		int remainingWallLength = Math.round(constraint.getPercentage() * candidates.size());
		while(remainingWallLength  >= Grid.SMALLEST_WALL_LENGTH){
			ArrayList<Coordinate> wallSequence = getWall(candidates, remainingWallLength);
			if(wallSequence.size() >= 2){
				result.add(wallSequence);
				removePerimeter(wallSequence, candidates);
				remainingWallLength = remainingWallLength - wallSequence.size();
			}
		}
		return result;
	}
	
	/**
     * Adds squares to the grid, sets the neighbors of all the squares
     */
    @Override
    protected void setSquares() {
        Coordinate coordinate;
        for(int x = 0; x < getGrid().getHSize(); x++){
            for(int y = 0; y < getGrid().getVSize(); y++){
                coordinate = new Coordinate(x, y);
                Square square = new Square();
               this.gridElements.put(coordinate, square);
               if(getStartCoordinates().contains(coordinate)){
            	   this.startPositions.add(square);
               }
            }
        }
        
    }

}
