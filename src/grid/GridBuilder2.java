/**
 * 
 */
package grid;

import gui.ApplicationWindow;

import java.util.ArrayList;
import java.util.Random;

import square.Direction;
import square.Square;
import square.obstacles.Wall;
import utils.Coordinate2D;

/**
 * @author jonas
 *
 */
public class GridBuilder2 {
	
	private Grid grid;
	private Random random;
	private ArrayList<Coordinate2D> walls;
	
	public GridBuilder2(int hSize, int vSize) {
		this.walls = new ArrayList<Coordinate2D>();
		setGrid(new Grid(hSize, vSize));
		setRandom(new Random());
	}

	
	/**
	 * @return the grid
	 */
	private Grid getGrid() {
		return grid;
	}
	
	public Grid buildGrid(){
		constructSquares();
		constructWalls();
		ApplicationWindow.MODEL.setWalls(getWalls());
		System.out.println("Walls pushed to model");
		return getGrid();
	}


	/**
	 * @param grid the grid to set
	 */
	private void setGrid(Grid grid) {
		this.grid = grid;
	}


	private Square getSquare(){
		return new Square();
	}	

	/**
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
		Coordinate2D coordinate;
		for(int x = 0; x < getGrid().getHSize(); x++){
			for(int y = 0; y < getGrid().getVSize(); y++){
				coordinate = new Coordinate2D(x, y);
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
		ArrayList<Coordinate2D> candidates = getGrid().getAllCoordinates();
		int totalWallLength = Math.round(Grid.PERCENTAGE_WALLS * candidates.size());
		
		/* Exclude starting positions from candidates */
		Coordinate2D bottomLeft = new Coordinate2D(0, getGrid().getVSize());
		Coordinate2D topRight = new Coordinate2D(getGrid().getHSize(), 0);
		candidates.remove(bottomLeft);
		candidates.remove(topRight);

		int remainingWallLength = totalWallLength;
		while(remainingWallLength  >= Grid.SMALLEST_WALL_LENGTH){
			ArrayList<Coordinate2D> wallSequence = getWall(candidates, remainingWallLength);
			if(wallSequence.size() >= 2){
				new Wall(getGrid().getSquares(wallSequence));
				removePerimeter(wallSequence, candidates);
				remainingWallLength = remainingWallLength - wallSequence.size();
				this.walls.addAll(wallSequence);
			}
		}
	}
	
	public ArrayList<Coordinate2D> getWalls(){
		return this.walls;
	}
	
	/**
	 * @param wall
	 * @param candidates
	 */
	private void removePerimeter(ArrayList<Coordinate2D> coordinates, ArrayList<Coordinate2D> candidates) {
		for(Coordinate2D coordinate : coordinates){
			for(Direction direction : Direction.values()){
				Coordinate2D neighbor = coordinate.getNeighbor(direction);
				candidates.remove(neighbor);
			}
			candidates.remove(coordinate);
		}
	}


	private ArrayList<Coordinate2D> getWall(ArrayList<Coordinate2D> candidates, int maxWallLength){
		ArrayList<Coordinate2D> wall = new ArrayList<Coordinate2D>();
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
		Coordinate2D start = candidates.get(getRandom().nextInt(candidates.size()));
		Coordinate2D next = start.getNeighbor(direction);
		/* As long as the length is within range and there is a square, continue */
		while(wall.size() < length && candidates.contains(next)){
			wall.add(next);
			next = next.getNeighbor(direction);
		}
		return wall;
	}
	
	public void constructObjects(){
		// TODO Auto-generated method stub
	}
	
	public void constructLightGrenades() {
		ArrayList<Coordinate2D> candidates = getGrid().getAllCoordinates();
		candidates.removeAll(walls);
		int maxGrenades = (int) Math.ceil(getGrid().getHSize() * getGrid().getVSize() * Grid.PERCENTAGE_GRENADES);
		for(int i = 0; i < maxGrenades; i++){
			Coordinate2D coordinate = candidates.get(getRandom().nextInt(candidates.size()));
			Square square = getGrid().getSquare(coordinate);
			candidates.remove(coordinate);
		}
	}
	
	

}
