/**
 * 
 */
package grid;

import item.Item;
import item.LightGrenade;
import item.launchable.IdentityDisc;

import java.util.ArrayList;
import java.util.Random;

import square.Direction;
import square.Square;
import square.obstacle.Obstacle;
import square.obstacle.Wall;
import util.Coordinate;
import be.kuleuven.cs.som.annotate.Basic;

/**
 * @author jonas
 *
 */
public class GridBuilder2 {
	
	public GridConstraint LIGHT_GRENADE_CONSTRAINT;
	public GridConstraint IDENTITY_DISK_CONSTRAINT;
	public GridConstraint WALL_CONSTRAINT;
	
	/**
	 * The grid which will be build
	 */
	private Grid grid;
	
	/**
	 * a random generator used in various creation methods
	 */
	private Random random;
	
	
	public GridBuilder2(){
		LIGHT_GRENADE_CONSTRAINT = new GridConstraint(Grid.PERCENTAGE_GRENADES);
		IDENTITY_DISK_CONSTRAINT = new GridConstraint(Grid.PERCENTAGE_GRENADES);
		WALL_CONSTRAINT = new GridConstraint(Grid.PERCENTAGE_IDENTITY_DISKS);
	}
	
	/**
	 * Creates a new Gridbuilder with parameters to create a new grid.
	 * @param 	hSize		
	 * 			The horizontal size of the grid this gridBuilder will build.
	 * @param 	vSize		
	 * 			The vertical size of the grid this gridBuilder will build.
	 */
	public GridBuilder2(int hSize, int vSize) {
		setGrid(new Grid(hSize, vSize));
		setRandom(new Random());
		setSquares();
		build(randomWallLocations(WALL_CONSTRAINT),
				randomLocations(LIGHT_GRENADE_CONSTRAINT),
				randomLocations(IDENTITY_DISK_CONSTRAINT));
	}
	
	public GridBuilder2(int hSize, int vSize, ArrayList<Wall> walls, ArrayList<Coordinate> lightGrenades, ArrayList<Coordinate> identityDisks){
		setGrid(new Grid(hSize, vSize));
		setRandom(new Random());
		setSquares();
		build(walls,lightGrenades,identityDisks);
	}
	
	private void build(ArrayList<Wall> walls, ArrayList<Coordinate> lightGrenades, ArrayList<Coordinate> identityDisks){
		placeWalls(walls, WALL_CONSTRAINT);
		placeLightGrenade(lightGrenades, LIGHT_GRENADE_CONSTRAINT);
		placeIdentityDisk(identityDisks, IDENTITY_DISK_CONSTRAINT);
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
	 * Sets the given grid as the gird of this gridBuilder.
	 * 
	 * @param grid the grid to set
	 */
	private void setGrid(Grid grid) {
		this.grid = grid;
	}

	/**
	 * Returns the random generator.
	 * 
	 * @return the random generator.
	 */	
	private Random getRandom() {
		return random;
	}

	/**
	 * Sets the given random as the random of this GridBuilder.
	 * 
	 * @param random the random to set
	 */
	private void setRandom(Random random) {
		this.random = random;
	}
	
	/**
	 * Adds squares to the grid
	 */
	private void setSquares() {
		Coordinate coordinate;
		for(int x = 0; x < getGrid().getHSize(); x++){
			for(int y = 0; y < getGrid().getVSize(); y++){
				coordinate = new Coordinate(x, y);
				getGrid().setSquare(coordinate, new Square());
			}
		}		
	}
	
	protected ArrayList<Coordinate> randomLocations(GridConstraint constraint){
		ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
		ArrayList<Coordinate> candidates = getGrid().getAllCoordinates();
		int max = (int) (constraint.getPercentage() * getAmountOfSquares());
		
		// Removed excluded squares from candidates
		candidates.removeAll(constraint.getExcluded());
		
		// Removed obstructed squares from candidates
		for(Coordinate coordinate : candidates){
			if(getGrid().getSquare(coordinate).isObstructed())
				candidates.remove(coordinate);
		}
		
		// Add one square from evey list of included coordinates
		for(ArrayList<Coordinate> includes : constraint.getIncluded()){
			coordinates.add(includes.get(getRandomIndex(includes)));
		}
		
		// Keep adding coordinates from candidates untill max is reached
		while(coordinates.size() <= max){
			coordinates.add(candidates.get(getRandomIndex(candidates)));
		}
		
		return coordinates;
	}
	
	protected ArrayList<Wall> randomWallLocations(GridConstraint constraint){
		ArrayList<Coordinate> candidates = getGrid().getAllCoordinates();
		ArrayList<Wall> walls = new ArrayList<Wall>();
		
		// Removed excluded squares from candidates
		candidates.removeAll(constraint.getExcluded());
		
		int totalWallLength = Math.round(constraint.getPercentage() * candidates.size());
		int remainingWallLength = totalWallLength;
		while(remainingWallLength  >= Grid.SMALLEST_WALL_LENGTH){
			ArrayList<Coordinate> wallSequence = getWall(candidates, remainingWallLength);
			if(wallSequence.size() >= 2){
				Wall w = new Wall(getGrid().getSquares(wallSequence));
				removePerimeter(wallSequence, candidates);
				remainingWallLength = remainingWallLength - wallSequence.size();
				walls.add(w);
			}
		}
		return walls;
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
	private ArrayList<Coordinate> getWall(ArrayList<Coordinate> candidates, int maxWallLength){
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
	 * @param 	wall	
	 * 			the wall of which the perimeter will be removed.
	 * @param 	candidates 
	 * 			the list of squares of which the perimeter of the wall will be removed.
	 */
	private void removePerimeter(ArrayList<Coordinate> coordinates, ArrayList<Coordinate> candidates) {
		for(Coordinate coordinate : coordinates){
			for(Direction direction : Direction.values()){
				Coordinate neighbor = coordinate.getNeighbor(direction);
				candidates.remove(neighbor);
			}
			candidates.remove(coordinate);
		}
	}
	
	protected Coordinate randomChargedIdentityDiskLocation(){
		return null;
	}
	
	
	/**
	 * Place light grenades at the the given coordinates, in respect with the given constraint.
	 * 
	 * @param 	coordinates
	 * 			The coordinates where to place the light grenades
	 * @param 	constraint
	 * 			The constraint for placing light grenades
	 */
	protected void placeLightGrenade(ArrayList<Coordinate> coordinates, GridConstraint constraint){
		if(!satisfiesConstraint(coordinates, constraint))
			throw new IllegalArgumentException("The given coordinates do not satisfy the given constraint");
		
		for(Coordinate coordinate : coordinates)
			placeItem(getGrid().getSquare(coordinate), new LightGrenade());
	}
	
	/**
	 * Place identity disks on the given coordinates, in respect with the given constraint
	 * 
	 * @param 	coordinates
	 * 			The coordinates where to place the identity disks
	 * @param 	constraint
	 * 			The constraint for placing identity disks
	 */
	protected void placeIdentityDisk(ArrayList<Coordinate> coordinates, GridConstraint constraint){
		if(!satisfiesConstraint(coordinates, constraint))
			throw new IllegalArgumentException("The given coordinates do not satisfy the given constraint");
		for(Coordinate coordinate : coordinates)
			placeItem(getGrid().getSquare(coordinate), new IdentityDisc());
	}
	
	/**
	 * Place walls on the given coordinates
	 * 
	 * @param 	coordinates
	 * 			The coordinates where to place the walls
	 * @param 	constraint
	 * 			The constraint for placing walls;
	 */
	protected void placeWalls(ArrayList<Wall> walls, GridConstraint constraint){
		ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
		for(Wall wall : walls){
			for(Square square : wall.getSquares()){
				coordinates.add(getGrid().getCoordinate(square));
			}
		}
		if(!satisfiesConstraint(coordinates, constraint))
			throw new IllegalArgumentException("The given coordinates do not satisfy the given constraint");
		for(Wall wall : walls){
			for(Square square : wall.getSquares()){
				placeObstacle(square, wall);
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
			}
		}
		for(boolean b : includes){
			if(!b)
				return false;
		}
		return true;
	}
	
	/**
	 * Place an item on the given coordinate
	 * 
	 * @param 	coordinate
	 * 			The coordinate to place the given item on
	 * @param 	item
	 * 			The item to be placed on the given coordinate
	 */
	private void placeItem(Square square, Item item) throws IllegalArgumentException {
		if(square.isObstructed())
			throw new IllegalArgumentException("Cannot place an object on a square that is obstructed.");
		square.getInventory().addItem(item);
	}
	
	/**
	 * Place an obstacle on the given coordinate
	 * 
	 * @param 	coordinate
	 * 			The coordinate to place the given obstacle on
	 * @param 	obstacle
	 * 			The obstacle to be placed on the given coordinate
	 */
	private void placeObstacle(Square square, Obstacle obstacle){
		square.setObstacle(obstacle);
	}
	
	private int getAmountOfSquares(){
		return getGrid().getHSize()*getGrid().getVSize();
	}
	
	private int getRandomIndex(ArrayList a){
		return getRandom().nextInt(a.size());
	}
	
	private ArrayList<Coordinate> getSquare(Coordinate start, Direction direction, int size){
		ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
		switch (direction) {
		case NORTH:
			
			break;

		case WEST:
			
			break;
		}
		return coordinates;
	}

}
