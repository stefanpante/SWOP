package grid;

import item.LightGrenade;

import java.util.ArrayList;
import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;

import square.Direction;
import square.Square;
import square.obstacle.Wall;
import util.Coordinate;

/**
 * 
 * Used to seperate the complex creation and the relatively simple representation
 * of the grid.
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class GridBuilder {
	
	/**
	 * The grid which will be build
	 */
	private Grid grid;
	/**
	 * a random generator used in various creation methods
	 */
	private Random random;
	/**
	 * A list which contains the coordinates of the walls.
	 */
	private ArrayList<Coordinate> wallCoordinates;
	
	/**
	 * A list with all the created wall objects.
	 */
	private ArrayList<Wall> walls;
	
	/**
	 * Creates a new Gridbuilder with parameters to create a new grid.
	 * @param 	hSize		
	 * 			The horizontal size of the grid this gridBuilder will build.
	 * @param 	vSize		
	 * 			The vertical size of the grid this gridBuilder will build.
	 */
	public GridBuilder(int hSize, int vSize) {
		this.wallCoordinates = new ArrayList<Coordinate>();
		this.walls = new ArrayList<Wall>();
		setGrid(new Grid(hSize, vSize));
		setRandom(new Random());
	}

	
	/**
	 * Returns the grid.
	 * 
	 * This method should only be called when the gridBuilder has done all his work.
	 */
	@Basic
	protected Grid getGrid() {
		return grid;
	}
	
	/**
	 * Constructs the grid as a whole, creates the Squares, 
	 * 	walls and places the lighGrenades.
	 * 
	 * @return The grid that has been built.
	 */
	public Grid buildGrid(){
		constructSquares();
		constructWalls();
		constructLightGrenades();

		return getGrid();
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
	 * Creates a new square.
	 * 
	 * @return a new square object
	 */
	private Square getSquare(){
		return new Square();
	}	

	/**
	 * Returns the random generator.
	 * 
	 * @return the random generator.
	 */	
	public Random getRandom() {
		return random;
	}


	/**
	 * Sets the given random as the random of this GridBuilder.
	 * 
	 * @param random the random to set
	 */
	public void setRandom(Random random) {
		this.random = random;
	}


	/**
	 * Fills the grid with squares
	 */
	public void constructSquares() {
		Coordinate coordinate;
		for(int x = 0; x < getGrid().getHSize(); x++){
			for(int y = 0; y < getGrid().getVSize(); y++){
				coordinate = new Coordinate(x, y);
				getGrid().setSquare(coordinate, getSquare());
			}
		}
	}

	/**
	 * Adds walls to the grid
	 */
	protected void constructWalls() {
		ArrayList<Coordinate> candidates = getGrid().getAllCoordinates();
		int totalWallLength = Math.round(Grid.PERCENTAGE_WALLS * candidates.size());
		
		/* Exclude starting positions from candidates */
		Coordinate bottomLeft = new Coordinate(0, getGrid().getVSize()-1);
		Coordinate topRight = new Coordinate(getGrid().getHSize()-1, 0);
		candidates.remove(bottomLeft);
		candidates.remove(topRight);

		int remainingWallLength = totalWallLength;
		while(remainingWallLength  >= Grid.SMALLEST_WALL_LENGTH){
			ArrayList<Coordinate> wallSequence = getWall(candidates, remainingWallLength);
			if(wallSequence.size() >= 2){
				Wall w = new Wall(getGrid().getSquares(wallSequence));
				removePerimeter(wallSequence, candidates);
				remainingWallLength = remainingWallLength - wallSequence.size();
				this.wallCoordinates.addAll(wallSequence);
				walls.add(w);
			}
		}
	}
	
	/**
	 * Returns a list of coordinates for the walls.
	 * 
	 * @return a list of coordinates for the walls.
	 */
	public ArrayList<Coordinate> getWallCoordinates(){
		return this.wallCoordinates;
	}
	
	/**
	 * Returns a list of walls
	 * 
	 * @return a list of walls.
	 */
	public ArrayList<Wall> getWalls(){
		return this.walls;
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
	 * Places lightGrenades on the grid according to the constraints.
	 */
	protected void constructLightGrenades() {
		ArrayList<Coordinate> candidates = getGrid().getAllCoordinates();
		candidates.removeAll(wallCoordinates);
		int maxGrenades = (int) Math.ceil(getGrid().getHSize() * getGrid().getVSize() * Grid.PERCENTAGE_GRENADES);
		/* Place grenade within range of start squares */
		placeIn3x3();
		
		Coordinate bottomLeft = new Coordinate(0, getGrid().getVSize()-1);
		Coordinate topRight = new Coordinate(getGrid().getHSize()-1, 0);
		candidates.remove(bottomLeft);
		candidates.remove(topRight);
		/*  Dispense grenades */
		for(int i = 2; i < maxGrenades; i++){
			Coordinate coordinate = candidates.get(getRandom().nextInt(candidates.size()));
			try{
				setGrenade(coordinate);				
			}catch(Exception e){
				i--;
			}
			candidates.remove(coordinate);
		}
	}
	
	/**
	 * Places a lightGrenade in the 3x3 rectangle around the start positions.
	 */
	private void placeIn3x3(){
		Coordinate bottomLeft = new Coordinate(0, getGrid().getVSize()-1);
		ArrayList<Coordinate> bottomLeftList = new ArrayList<Coordinate>();
		Coordinate topRight = new Coordinate(getGrid().getHSize()-1, 0);
		ArrayList<Coordinate> topRightList = new ArrayList<Coordinate>();
		
		for(Coordinate coor = bottomLeft; coor.getY() >= getGrid().getVSize()-3; coor = coor.getNeighbor(Direction.NORTH)){
			for(Coordinate coor2 = coor; coor2.getX() <= 2  ; coor2 = coor2.getNeighbor(Direction.EAST)){
				if(!getGrid().getSquare(coor2).isObstructed())
					bottomLeftList.add(coor2);
			}
		}
		bottomLeftList.remove(bottomLeft);
		for(Coordinate coor = topRight; coor.getY() <= 2; coor = coor.getNeighbor(Direction.SOUTH)){
			for(Coordinate coor2 = coor; coor2.getX() >= getGrid().getHSize()-3  ; coor2 = coor2.getNeighbor(Direction.WEST)){
				if(!getGrid().getSquare(coor2).isObstructed())
					topRightList.add(coor2);
			}
		}
		topRightList.remove(topRight);
		assert bottomLeftList.size() > 0;
		assert topRightList.size() > 0;
		setGrenade(pickRandomly(bottomLeftList));
		setGrenade(pickRandomly(topRightList));
		
	}
	
	/**
	 * Picks a random coordinate out of the list of coordinates.
	 * 
	 * @param 	arrayList
	 * 			The list of which you want to get a random coordinate.	
	 */
	private Coordinate pickRandomly(ArrayList<Coordinate> arrayList) {
		return arrayList.get(getRandom().nextInt(arrayList.size()));
	}
	
	/**
	 * Places a grenade at the given coordinate.
	 * 
	 * @param 	coordinate
	 * 			The coordinate of the square on which you want to place a lightGrenade.
	 * @throws 	IllegalStateException
	 * 			When the square at the given coordinate already has a lightGrenade.
	 */
	private void setGrenade(Coordinate coordinate) throws IllegalStateException {
		Square square = getGrid().getSquare(coordinate);
		if(square.getInventory().hasLightGrenade())
			throw new IllegalStateException();
		square.getInventory().addItem(new LightGrenade());
	}
}
