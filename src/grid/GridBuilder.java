/**
 * 
 */
package grid;

import items.LightGrenade;

import java.util.ArrayList;
import java.util.Random;

import square.Direction;
import square.Square;
import square.obstacles.Wall;
import utils.Coordinate;

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
	
	public GridBuilder(int hSize, int vSize) {
		this.wallCoordinates = new ArrayList<Coordinate>();
		this.walls = new ArrayList<Wall>();
		setGrid(new Grid(hSize, vSize));
		setRandom(new Random());
	}

	
	/**
	 * Returns the grid.
	 * @return the grid
	 */
	protected Grid getGrid() {
		return grid;
	}
	
	/**
	 * Constructs the grid as a whole, creates the Squares, walls and places the lighGrenades.
	 * @return the grid that has been built
	 */
	public Grid buildGrid(){
		constructSquares();
		constructWalls();
		constructLightGrenades();

//		ApplicationWindow.MODEL.setGrid(getGrid());
//		ApplicationWindow.MODEL.setWalls(getWallCoordinates());
		return getGrid();
	}


	/**
	 * @param grid the grid to set
	 */
	private void setGrid(Grid grid) {
		this.grid = grid;
	}


	/**
	 * Creates a new square.
	 * @return a new square object
	 */
	private Square getSquare(){
		return new Square();
	}	

	/**
	 * returns the randomgenerator
	 * @return the random
	 */
	public Random getRandom() {
		return random;
	}


	/**
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
	 *
	 * returns the maximal permitted length of wall, which depends on the
	 * orientation of the wall
	 *
	 * @param 	orientation the orientation of the wall
	 * @return 	the maximal length which is permitted for a wall in this grid
	 * @throws 	IllegalArgumentException
	 *
	 */
	private int getMaxLengthWall(Direction direction) throws IllegalArgumentException{
		switch(direction){
		case NORTH:
			return (int) (getGrid().getHSize() * Grid.LENGTH_PERCENTAGE_WALL);
		case EAST:
			return (int) (getGrid().getVSize() * Grid.LENGTH_PERCENTAGE_WALL);
		default:	
			throw new IllegalArgumentException("This orientation is not supported:" + direction);
		}
	}

	/**
	 * Adds walls to the grid
	 */
	public void constructWalls() {
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
	 * @return a list of walls.
	 */
	public ArrayList<Wall> getWalls(){
		return this.walls;
	}
	
	/**
	 * Removes all squares around a wall of a list of candidates
	 * @param wall	the wall of which the perimeter will be removed.
	 * @param candidates the list of squares of which the perimeter of the wall will be removed.
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
	 * @param candidates
	 * @param maxWallLength
	 * @return
	 */
	private ArrayList<Coordinate> getWall(ArrayList<Coordinate> candidates, int maxWallLength){
		ArrayList<Coordinate> wall = new ArrayList<Coordinate>();
		Direction direction = Direction.getRandom();
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
	
	//TODO: i think the grenade is always positioned in the 2x2 square around startposition
	public void constructLightGrenades() {
		ArrayList<Coordinate> candidates = getGrid().getAllCoordinates();
		candidates.removeAll(wallCoordinates);
		int maxGrenades = (int) Math.ceil(getGrid().getHSize() * getGrid().getVSize() * Grid.PERCENTAGE_GRENADES);
		/* Place grenade within range of start squares */
		Coordinate bottomLeft = new Coordinate(0, getGrid().getVSize()-1);
		Coordinate tR = getRandomNeighbor(bottomLeft, candidates);
		setGrenade(tR);
		candidates.remove(tR);
		candidates.remove(bottomLeft);
		
		Coordinate topRight = new Coordinate(getGrid().getHSize()-1, 0);
		Coordinate bL = getRandomNeighbor(topRight, candidates);
		setGrenade(bL);
		candidates.remove(bL);
		candidates.remove(topRight);
		/*  Dispense grenades */
		for(int i = 2; i < maxGrenades; i++){
			Coordinate coordinate = candidates.get(getRandom().nextInt(candidates.size()));
			setGrenade(coordinate);
			candidates.remove(coordinate);
		}
	}
	
	private void setGrenade(Coordinate coordinate){
		Square square = getGrid().getSquare(coordinate);
		square.getInventory().addItem(new LightGrenade());
	}
	
	private Coordinate getRandomNeighbor(Coordinate coordinate, ArrayList<Coordinate> candidates){
		ArrayList<Coordinate> realCandidates = new ArrayList<Coordinate>();
		for(Coordinate neighbor : coordinate.getAllNeighbors()){
			if(candidates.contains(neighbor)){
				realCandidates.add(neighbor);
			}
		}
		return realCandidates.get(getRandom().nextInt(realCandidates.size()));
	}
	
	

}
