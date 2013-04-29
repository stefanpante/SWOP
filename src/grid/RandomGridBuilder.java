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
import java.util.Random;
import java.util.Map.Entry;

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
public class RandomGridBuilder extends AbstractGridBuilder{
		
	
	
	/**
	 * the number of horizontal squares in the grid.
	 */
	private int hSize;
	
	/**
	 * the number of vertical squares in the grid
	 */
	private int vSize;
	
	public static int MIN_HSIZE = 10;
	
	public static int MIN_VSIZE = 10;
	
	
	ArrayList<Wall> walls;

	
	/**
	 * This creates a flat grid, with dimensions 10x10 and no
	 * obstacles. No lightGrenades or any other object. This is mainly used
	 * for testing purposes.
	 */
	protected RandomGridBuilder() {
		this.hSize = 10;
		this.vSize = 10;
		setGrid(new Grid(10, 10));
		setRandom(new Random());
		setSquares();
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
		this.hSize = hSize;
		this.vSize = vSize;
		setGrid(new Grid(hSize, vSize));
		setRandom(new Random());
		setSquares();
		setConstraints();
		this.walls = new ArrayList<Wall>();
		//Walls are build explicitly first cause other randomLocations depend on the placed obstacles.
		placeWalls(randomWallLocations(getConstraintWall()), getConstraintWall());
		build(randomLocations(getConstraingtLightGrenade()),
				randomLocations(getConstraintIdentityDisk()),
				randomLocations(getConstraintTeleport()), 
				chargedIdentityDiskLocation());
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
			Coordinate chargedIdentityDisk){
		this.hSize = hSize;
		this.vSize = vSize;
		
		setGrid(new Grid(hSize, vSize));
		setRandom(new Random());
		setSquares();
		setEmptyConstraints();
		this.walls = new ArrayList<Wall>();
		//Walls are build explicitly first cause other randomLocations depend on the placed obstacles.
		placeWalls(walls, getConstraintWall());
		build(lightGrenades, identityDisks, teleports, chargedIdentityDisk);
	}
	
	protected void build(ArrayList<Coordinate> lightGrenades, ArrayList<Coordinate> identityDisks, ArrayList<Coordinate> teleports, Coordinate chargedIdentityDisk)
	throws IllegalStateException{
		placeLightGrenade(lightGrenades, getConstraingtLightGrenade());
		placeIdentityDisk(identityDisks, getConstraintIdentityDisk());
		placeTeleports(teleports, getConstraintTeleport());
		placeChargedIdentityDisk(chargedIdentityDisk);
		if(!isConsistent()){
			throw new IllegalStateException("The built grid is not valid");
		}
	}
	

	private void setConstraints(){
		ArrayList<Coordinate> excluded = new ArrayList<Coordinate>();
		excluded.add(getPlayerOneStart());
		excluded.add(getPlayerTwoStart());	
		
		ArrayList<ArrayList<Coordinate>> grenadesIncluded = new ArrayList<ArrayList<Coordinate>>();
		grenadesIncluded.add(getSquaredLocation(getPlayerOneStart(), Direction.NORTH, 3));
		grenadesIncluded.add(getSquaredLocation(getPlayerTwoStart(), Direction.EAST, 3));
		
		setConstraintWall(new GridConstraint(Grid.PERCENTAGE_WALLS, excluded));
		setConstraingtLightGrenade(new GridConstraint(Grid.PERCENTAGE_GRENADES, excluded, grenadesIncluded));
		setConstraintIdentityDisk(new GridConstraint(Grid.PERCENTAGE_IDENTITY_DISKS, excluded));
		setConstraintTeleport(new GridConstraint(Grid.PRECENTAGE_TELEPORTS, excluded));
	}
	
	private void setEmptyConstraints(){		
		setConstraintWall(new GridConstraint());
		setConstraingtLightGrenade(new GridConstraint());
		setConstraintIdentityDisk(new GridConstraint());
		setConstraintTeleport(new GridConstraint());
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
	 * @param 	wall	
	 * 			the wall of which the perimeter will be removed.
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
	
	
	
	
	
	
	// TODO: voor wat dient dit?
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

	@Override
	public Coordinate getPlayerOneStart(){
		return new Coordinate(0, getGrid().getVSize()-1);
	}
	
	@Override
	public Coordinate getPlayerTwoStart(){
		return new Coordinate(getGrid().getHSize()-1, 0);
	}
	
	@Override
	public boolean isConsistent() {
		// TODO Auto-generated method stub
		return true;
		
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
	 * Place walls on the given coordinates
	 * 
	 * @param 	coordinates
	 * 			The coordinates where to place the walls
	 * @param 	constraint
	 * 			The constraint for placing walls;
	 */
	protected void placeWalls(ArrayList<ArrayList<Coordinate>> walls, GridConstraint constraint) {
		if(!satisfiesConstraint(flatten(walls), constraint))
			throw new IllegalArgumentException("The given coordinates do not satisfy the given constraint");
		for(ArrayList<Coordinate> sequence : walls){
			this.walls.add(new Wall(getGrid().getSquares(sequence)));
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

	@Override
	protected void build() throws IllegalStateException {
		// TODO Auto-generated method stub
		
	}
	

	/**
	 * Suggest a coordinate for the Charged Disk Location
	 * 
	 * @return A coordinate equally far away from each player
	 */
	protected Coordinate chargedIdentityDiskLocation() {
		Square player1Square = getGrid().getSquare(getPlayerOneStart());
		Square player2Square = getGrid().getSquare(getPlayerTwoStart());
		Entry<Coordinate,Integer> shortest = new AbstractMap.SimpleEntry<Coordinate,Integer>(null,Integer.MAX_VALUE);
		for(Square square : getGrid().getAllSquares()){
			if(!square.isObstructed()){
				Coordinate thisCoordinate = getGrid().getCoordinate(square);
				try{
					AStar aStar = new AStar(getGrid());
					int player1Length = aStar.shortestPath(player1Square, square).size();
					AStar aStar2 = new AStar(getGrid());
					int player2Length = aStar2.shortestPath(player2Square, square).size();
					if(Math.abs(player2Length - player1Length) <= 2){
						int longest = Math.max(player1Length, player2Length);
						if(longest < shortest.getValue()){
							shortest = new AbstractMap.SimpleEntry<Coordinate,Integer>(thisCoordinate, longest);
						}
					}
				}catch(Exception e){
					System.err.println(e.getMessage());
				}
			}
		}
		return shortest.getKey();
	}

	

	protected Coordinate randomChargedIdentityDiskLocation() {
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
	protected void placeLightGrenade(ArrayList<Coordinate> coordinates, GridConstraint constraint) {
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
	protected void placeIdentityDisk(ArrayList<Coordinate> coordinates, GridConstraint constraint) {
		if(!satisfiesConstraint(coordinates, constraint))
			throw new IllegalArgumentException("The given coordinates do not satisfy the given constraint");
		for(Coordinate coordinate : coordinates)
			placeItem(getGrid().getSquare(coordinate), new IdentityDisc());
	}

	protected void placeChargedIdentityDisk(Coordinate coordinate) {
		if(coordinate == null)
			return;
		Square diskSquare = getGrid().getSquare(coordinate);
		placeItem(diskSquare, new ChargedIdentityDisc());
	}



	/**
	 * 
	 * @param 	teleports
	 * 			The coordinates of the locations to place.
	 */
	protected void placeTeleports(ArrayList<Coordinate> coordinates, GridConstraint constraint) {
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
	
	int getRandomIndex(ArrayList a){
		return getRandom().nextInt(a.size());
	}

	
}
