/**
 * 
 */
package grid;

import item.Item;
import item.LightGrenade;
import item.launchable.IdentityDisc;

import java.util.ArrayList;
import java.util.Random;

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
	
	public static GridConstraint LIGHT_GRENADE_CONSTRAINT;
	public static GridConstraint IDENTITY_DISK_CONSTRAINT;
	public static GridConstraint WALL_CONSTRAINT;
	
	/**
	 * The grid which will be build
	 */
	private Grid grid;
	
	/**
	 * a random generator used in various creation methods
	 */
	private Random random;
	
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
	public void setRandom(Random random) {
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
	
	/**
	 * Place light grenades at the the given coordinates, in respect with the given constraint.
	 * 
	 * @param 	coordinates
	 * 			The coordinates where to place the light grenades
	 * @param 	constraint
	 * 			The constraint for placing light grenades
	 */
	public void placeLightGrenade(ArrayList<Coordinate> coordinates, GridConstraint constraint){
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
	public void placeIdentityDisk(ArrayList<Coordinate> coordinates, GridConstraint constraint){
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
	public void placeWalls(ArrayList<Wall> walls, GridConstraint constraint){
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
	public boolean satisfiesConstraint(ArrayList<Coordinate> coordinates, GridConstraint constraint){
		int totalSquaes = getGrid().getHSize()*getGrid().getVSize();
		float percentage = (float) coordinates.size() / (float) totalSquaes;
		boolean[] includes = new boolean[constraint.getIncluded().size()];
		if(percentage > constraint.getPercentage())
			return false;
		for(Coordinate coordinate : coordinates){
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
	private void placeItem(Square square, Item item){
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
}
