/**
 * 
 */
package grid;

import item.Item;
import item.LightGrenade;
import item.Teleport;
import item.launchable.ChargedIdentityDisc;
import item.launchable.IdentityDisc;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Random;

import square.Direction;
import square.Square;
import square.obstacle.Wall;
import util.AStar;
import util.Coordinate;
import be.kuleuven.cs.som.annotate.Basic;

/**
 * @author Jonas Devlieghere, Dieter Castel
 *
 */
public class GridBuilder {
	
	public GridConstraint TELEPORT_CONSTRAINT;
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
	
	private ArrayList<Wall> walls;

	
	/**
	 * This creates a flat grid, with dimensions 10x10 and no
	 * obstacles. No lightGrenades or any other object. This is mainly used
	 * for testing purposes.
	 */
	public GridBuilder() {
		setGrid(new Grid(10, 10));
		setRandom(new Random());
		setSquares();
		setEmptyConstraints();
	}
	
	/**
	 * Creates a new Gridbuilder with parameters to create a new grid.
	 * 
	 * This constructor will place random Walls, LightGrenades, 
	 * identityDiscs and Teleports according to the constraints.
	 * 
	 * @param 	hSize		
	 * 			The horizontal size of the grid this gridBuilder will build.
	 * @param 	vSize		
	 * 			The vertical size of the grid this gridBuilder will build.
	 */
	public GridBuilder(int hSize, int vSize) {
		setGrid(new Grid(hSize, vSize));
		setRandom(new Random());
		setSquares();
		setConstraints();
		this.walls = new ArrayList<Wall>();
		//Walls are build explicitly first cause other randomLocations depend on the placed obstacles.
		placeWalls(randomWallLocations(WALL_CONSTRAINT), WALL_CONSTRAINT);
		build(randomLocations(LIGHT_GRENADE_CONSTRAINT),
				randomLocations(IDENTITY_DISK_CONSTRAINT),
				randomLocations(TELEPORT_CONSTRAINT));
	}
	
	/**
	 * Creates a new Gridbuilder with parameters to create a new grid.
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
	protected GridBuilder(int hSize, int vSize, ArrayList<ArrayList<Coordinate>> walls, 
			ArrayList<Coordinate> lightGrenades, 
			ArrayList<Coordinate> identityDisks, 
			ArrayList<Coordinate> teleports){
		setGrid(new Grid(hSize, vSize));
		setRandom(new Random());
		setSquares();
		setEmptyConstraints();
		this.walls = new ArrayList<Wall>();
		//Walls are build explicitly first cause other randomLocations depend on the placed obstacles.
		placeWalls(walls, WALL_CONSTRAINT);
		build(lightGrenades, identityDisks, teleports);
	}
	
	private void build(ArrayList<Coordinate> lightGrenades, ArrayList<Coordinate> identityDisks, ArrayList<Coordinate> teleports){
		placeLightGrenade(lightGrenades, LIGHT_GRENADE_CONSTRAINT);
		placeIdentityDisk(identityDisks, IDENTITY_DISK_CONSTRAINT);
		placeTeleports(teleports, TELEPORT_CONSTRAINT);
	}
	

	private void setConstraints(){
		ArrayList<Coordinate> excluded = new ArrayList<Coordinate>();
		excluded.add(getBottomLeft());
		excluded.add(getTopRight());	
		
		ArrayList<ArrayList<Coordinate>> grenadesIncluded = new ArrayList<ArrayList<Coordinate>>();
		grenadesIncluded.add(getSquaredLocation(getBottomLeft(), Direction.NORTH, 3));
		grenadesIncluded.add(getSquaredLocation(getTopRight(), Direction.EAST, 3));
		
		WALL_CONSTRAINT = new GridConstraint(Grid.PERCENTAGE_WALLS, excluded);
		LIGHT_GRENADE_CONSTRAINT = new GridConstraint(Grid.PERCENTAGE_GRENADES, excluded, grenadesIncluded);
		IDENTITY_DISK_CONSTRAINT = new GridConstraint(Grid.PERCENTAGE_IDENTITY_DISKS, excluded);
		TELEPORT_CONSTRAINT = new GridConstraint(Grid.PRECENTAGE_TELEPORTS, excluded);
	}
	
	private void setEmptyConstraints(){		
		WALL_CONSTRAINT = new GridConstraint();
		LIGHT_GRENADE_CONSTRAINT = new GridConstraint();
		IDENTITY_DISK_CONSTRAINT = new GridConstraint();
		TELEPORT_CONSTRAINT = new GridConstraint();
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
	
	protected Coordinate ChargedIdentityDiskLocation(){
		Square player1Square = getGrid().getSquare(getBottomLeft());
		Square player2Square = getGrid().getSquare(getTopRight());
		Entry<Coordinate,Integer> shortest = new AbstractMap.SimpleEntry<Coordinate,Integer>(null,Integer.MAX_VALUE);
		for(Square square : getGrid().getAllSquares()){
			if(!square.isObstructed()){
				Coordinate thisCoordinate = getGrid().getCoordinate(square);
				AStar aStar = new AStar(getGrid());
				int player1Length = aStar.shortestPath(player1Square, square).size();
				int player2Length = aStar.shortestPath(player2Square, square).size();
				if(Math.abs(player2Length - player1Length) <= 2){
					int longest = Math.max(player1Length, player2Length);
					if(longest < shortest.getValue()){
						shortest = new AbstractMap.SimpleEntry<Coordinate,Integer>(thisCoordinate, longest);
					}
				}
			}
		}
		return shortest.getKey();
	}
	
	/**
	 * Finds a random list of sequences of coordinates that follow the given constraint.
	 * 
	 * @param 	constraint
	 * 			The constraint which is taken into account.
	 * @return	A list of sequences of coordinates.
	 */
	protected ArrayList<ArrayList<Coordinate>> randomWallLocations(GridConstraint constraint){
		ArrayList<Coordinate> candidates = getGrid().getAllCoordinates();
		ArrayList<ArrayList<Coordinate>> result = new ArrayList<ArrayList<Coordinate>>();
		
		// Removed excluded squares from candidates
		candidates.removeAll(constraint.getExcluded());
		
		int totalWallLength = Math.round(constraint.getPercentage() * candidates.size());
		int remainingWallLength = totalWallLength;
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
	
	protected void placeChargedIdentityDisk(Coordinate coordinate){
		AStar aStar = new AStar(getGrid());
		Square diskSquare = getGrid().getSquare(coordinate);
		Square player1Square = getGrid().getSquare(getBottomLeft());
		Square player2Square = getGrid().getSquare(getTopRight());
		int player1Length = aStar.shortestPath(player1Square, diskSquare).size();
		int player2Length = aStar.shortestPath(player2Square, diskSquare).size();
		if(Math.abs(player1Length - player2Length) > 2)
			throw new IllegalArgumentException("Shortest paths to Charged Identity Disk differ more than two");
		placeItem(diskSquare, new ChargedIdentityDisc());
	}
	
	/**
	 * Place walls on the given coordinates
	 * 
	 * @param 	coordinates
	 * 			The coordinates where to place the walls
	 * @param 	constraint
	 * 			The constraint for placing walls;
	 */
	protected void placeWalls(ArrayList<ArrayList<Coordinate>> walls, GridConstraint constraint){
		if(!satisfiesConstraint(flatten(walls), constraint))
			throw new IllegalArgumentException("The given coordinates do not satisfy the given constraint");
		for(ArrayList<Coordinate> sequence : walls){
			this.walls.add(new Wall(getGrid().getSquares(sequence)));
		}
		
	}
	
	/**
	 * 
	 * @param 	teleports
	 * 			The coordinates of the locations to place.
	 */
	private void placeTeleports(ArrayList<Coordinate> coordinates, GridConstraint constraint) {
		if(!satisfiesConstraint(coordinates, constraint))
			throw new IllegalArgumentException("The given coordinates do not satisfy the given constraint");
		ArrayList<Teleport> teleports = new ArrayList<Teleport>();
		Teleport teleport;
		for(Coordinate coordinate : coordinates){
			teleport = new Teleport();
			placeItem(getGrid().getSquare(coordinate), teleport);
			teleports.add(teleport);
		}
		linkTeleports(teleports, true);		
	}

	/**
	 * Links a list of teleports according to the given boolean value.
	 * 
	 * @param 	teleports
	 * 			The list of teleports to link.
	 * @param 	linkRandomly
	 * 			Boolean that hould be true if the linking should happen randomly.
	 * 			Otherwise each teleport will be linked to its next neighbor in the list.
	 */
	private void linkTeleports(ArrayList<Teleport> teleports, boolean linkRandomly) {
		if(linkRandomly){
			for(Teleport tele : teleports){
				Teleport candidateDestination = teleports.get(getRandomIndex(teleports));
				while(candidateDestination.equals(tele)){
					candidateDestination = teleports.get(getRandomIndex(teleports));
				}
				tele.setDestination(candidateDestination);
			}
		} else {
			for(int i=0; i<teleports.size(); i++){
				teleports.get(i).setDestination(teleports.get(i%teleports.size()));
			}
		}
	}

	/**
	 * Utility method that flattens a two dimensional Arraylist. 
	 * 
	 * @param 	list
	 * @return
	 */
	private ArrayList<Coordinate> flatten(ArrayList<ArrayList<Coordinate>> list) {
		ArrayList<Coordinate> result = new ArrayList<Coordinate>();
		for(ArrayList<Coordinate> L : list){
			result.addAll(L);
		}
		return result;
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
			return;
			//			throw new IllegalArgumentException("Cannot place an object on a square that is obstructed.");
		square.getInventory().addItem(item);
	}
	
	private int getAmountOfSquares(){
		return getGrid().getHSize()*getGrid().getVSize();
	}
	
	@SuppressWarnings("rawtypes")
	private int getRandomIndex(ArrayList a){
		return getRandom().nextInt(a.size());
	}
	
	private ArrayList<Coordinate> getSquaredLocation(Coordinate start, Direction direction, int size){
		ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
		switch (direction) {
		case NORTH:
			for(Coordinate coor = start; coor.getY() >= getGrid().getVSize()-size; coor = coor.getNeighbor(direction)){
				for(Coordinate coor2 = coor; coor2.getX() <= size-1  ; coor2 = coor2.getNeighbor(Direction.EAST)){
					if(!getGrid().getSquare(coor2).isObstructed())
						coordinates.add(coor2);
				}
			}
			break;
		case EAST:
			for(Coordinate coor = start; coor.getY() <= size - 1; coor = coor.getNeighbor(Direction.SOUTH)){
				for(Coordinate coor2 = coor; coor2.getX() >= getGrid().getHSize()-size  ; coor2 = coor2.getNeighbor(Direction.WEST)){
					if(!getGrid().getSquare(coor2).isObstructed())
						coordinates.add(coor2);
				}
			}
			break;
		default:
			throw new IllegalArgumentException("Cannot get squared locations in that direction");
		}
		return coordinates;
	}

	private Coordinate getBottomLeft(){
		return new Coordinate(0, getGrid().getVSize()-1);
	}
	
	private Coordinate getTopRight(){
		return new Coordinate(getGrid().getHSize()-1, 0);
	}
	
	protected ArrayList<Wall> getWalls(){
		return this.walls;
	}
	
	private Coordinate getFairCoordinate(){
		
		
		
		return null;
	}
	
}
