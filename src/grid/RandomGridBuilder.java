package grid;

import item.ChargedIdentityDisc;
import item.ForceFieldGenerator;
import item.IdentityDisc;
import item.LightGrenade;

import java.util.ArrayList;
import java.util.Random;
import square.Direction;
import square.Square;
import square.obstacle.Wall;
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
		setGrid(new Grid(10, 10));
		setRandom(new Random());
		setSquares();
		setEmptyConstraints();
		for(Coordinate coor: getStartPositions()){
			Square s = getGrid().getSquare(coor);
			getGrid().addStartPosition(s);
		}
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
		setHSize(hSize);
        setVSize(vSize);
		setGrid(new Grid(hSize, vSize));
		setRandom(new Random());
		this.walls = new ArrayList<Wall>();	
		
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
	protected RandomGridBuilder(int hSize, int vSize, ArrayList<ArrayList<Coordinate>> walls, 
			ArrayList<Coordinate> lightGrenades, 
			ArrayList<Coordinate> identityDisks, 
			ArrayList<Coordinate> teleports,
			ArrayList<Coordinate> forceFieldGenerators,
			Coordinate chargedIdentityDisk){
		setHSize(hSize);
	    setVSize(vSize);
		
		setGrid(new Grid(hSize, vSize));
		setRandom(new Random());
		setSquares();
		setEmptyConstraints();
		this.walls = new ArrayList<Wall>();
		//Walls are build explicitly first cause other randomLocations depend on the placed obstacles.
		placeWalls(walls);
		build(lightGrenades, identityDisks, teleports,forceFieldGenerators, chargedIdentityDisk);
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
		for(Coordinate coor: getStartPositions()){
			Square s = getGrid().getSquare(coor);
			getGrid().addStartPosition(s);
		}
		setNeighbors();
		setConstraints();
		placeWalls(randomWallLocations(getConstraintWall()));
		placeItems(new LightGrenade(), randomLocations(getConstraintLightGrenade()));
		placeItems(new IdentityDisc(), randomLocations(getConstraintIdentityDisk()));
		placeItems(new ForceFieldGenerator(), randomLocations(getConstraintForceFieldGenerator()));
		//placeLightGrenade(randomLocations(getConstraintLightGrenade()));
		//placeIdentityDisk(randomLocations(getConstraintIdentityDisk()));
		placeTeleports(randomLocations(getConstraintTeleport()));
		//placeForceFieldGenerators(randomLocations(getConstraintForceFieldGenerator()));
		Coordinate coor = getChargedIdentityDiskLocation();
		placeItem( getGrid().getSquare(coor), new ChargedIdentityDisc());
		
		
		
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
	protected void build(ArrayList<Coordinate> lightGrenades, ArrayList<Coordinate> identityDisks, ArrayList<Coordinate> teleports, 
			ArrayList<Coordinate> fFgen, Coordinate chargedIdentityDisk)
	throws IllegalStateException{
		//	
//		placeLightGrenade(lightGrenades);
//		placeIdentityDisk(identityDisks);
//		placeTeleports(teleports);
//		placeForceFieldGenerators(fFgen);
		//placeChargedIdentityDisk(chargedIdentityDisk);
		
		for(Coordinate coor: getStartPositions()){
			Square s = getGrid().getSquare(coor);
			getGrid().addStartPosition(s);
		}
		
	}
	

	private void setConstraints(){
		ArrayList<Coordinate> excluded = new ArrayList<Coordinate>();
		ArrayList<ArrayList<Coordinate>> grenadesIncluded = new ArrayList<ArrayList<Coordinate>>();
		for(Coordinate coor: getStartPositions()){
			excluded.add(coor);
			grenadesIncluded.add(getSquaredLocation(coor, 3));
		}
		
		
		setConstraintWall(new GridConstraint(Grid.PERCENTAGE_WALLS, excluded));
		setConstraintLightGrenade(new GridConstraint(Grid.PERCENTAGE_GRENADES, excluded, grenadesIncluded));
		setConstraintIdentityDisk(new GridConstraint(Grid.PERCENTAGE_IDENTITY_DISKS, excluded));
		setConstraintTeleport(new GridConstraint(Grid.PERCENTAGE_TELEPORTS, excluded));
		setConstraintForceFieldGenerator(new GridConstraint(Grid.PERCENTAGE_FORCEFIELDGENERATORS,excluded));
	}
	
	private void setEmptyConstraints(){		
		setConstraintWall(new GridConstraint());
		setConstraintLightGrenade(new GridConstraint());
		setConstraintIdentityDisk(new GridConstraint());
		setConstraintTeleport(new GridConstraint());
		setConstraintForceFieldGenerator(new GridConstraint());
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
	
	/**
	 * Returns the coordinates of the given walls on the grid of this GridBuilder.
	 * 
	 * @param 	walls
	 * 			The walls of which the coordinates are wanted.
	 * @return	The coordinates covered by the walls.
	 */
	public ArrayList<Coordinate> getCoordinatesOfWalls(ArrayList<Wall> walls) {
		ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
		for(Wall wall : walls){
			for(Square square : wall.getSquares()){
				coordinates.add(getGrid().getCoordinate(square));
			}
		}
		return coordinates;
	}

    /**
     * Returns a list with all the coordinates forming a size X size
     * rectangle with start as center.
     * @param   start
     *          The center of the square that will be returned.
     * @param   size
     *          The amount of squares surrounding the starting coordinate.
     * @return
     */
	private ArrayList<Coordinate> getSquaredLocation(Coordinate start, int size){
		ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
        int centerX = start.getX();
        int centerY = start.getY();
        int startX = centerX - size/2;
        int startY = centerY - size/2;
        int endX = centerX + size/2;
        int endY = centerY + size/2;
        for (int x =startX; x<=endX; x++){
            for (int y =startY; y<=endY; y++){
                    Coordinate coor =new Coordinate(x,y);
                    if(getGrid().getSquare(coor).isObstructed()){
                        coordinates.add(coor);
                    }
            }
        }
        coordinates.remove(start);
        return coordinates;
	}
    
    @Override
    public ArrayList<Coordinate> getStartPositions(){
    	ArrayList<Coordinate> startPositions = new ArrayList<Coordinate>();
    	
    	startPositions.add(new Coordinate(0,0));
    	startPositions.add(new Coordinate(0, getGrid().getVSize() -1));
    	startPositions.add(new Coordinate(getGrid().getHSize() - 1, 0));
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
                getGrid().setSquare(coordinate, new Square());
            }
        }
    }

}
