/**
 * 
 */
package board;

import items.LightGrenade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import game.Game;
import gui.ApplicationWindow;
import gui.GridCanvas;

import java.util.Random;

import board.obstacles.Wall;

import utils.Coordinate2D;

/**
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 */	
public class GridBuilder {
	/**
	 * Max percentage of squares covered by walls.
	 */
	public static float MAX_PERCENTAGEWALLS = 0.2f;


	/**
	 * Percentage of square covered by grenades
	 */
	public static float PERCENTAGEGRENADES = 0.05f;

	/**
	 * Percentage of max length of a wall
	 */
	public static float MAX_LENGTHPERCENTAGEWALL = 0.5f;

	/**
	 * Horizontal size of this grid.
	 */
	private int hSize;

	/**
	 * Vertical size of this grid.
	 */
	private int vSize;

	/**
	 * Two-dimensional array containing the squares in this grid.
	 */
	private Square[][] grid;

	private HashMap<Coordinate2D, Square> board;
	/**
	 * The square at the bottom left of this grid.
	 */
	private Square bottomLeft; 

	/**
	 * The square at the top right of this grid.
	 */
	private Square topRight;



	private ArrayList<Square> freeFields = new ArrayList<Square>();

	/**
	 * Create a new GridBuilder with the given dimension.
	 * 
	 * @param 	hSize
	 * 			The horizontal size for this grid builder
	 * @param 	vSize
	 * 			The vertical size for this grid builder
	 */
	public GridBuilder(int hSize, int vSize) throws IllegalArgumentException {
		if(!isValidSize(hSize,vSize))
			throw new IllegalArgumentException();
		this.hSize = hSize;
		this.vSize = vSize;
	}

	/**
	 * @param 	hSize
	 * 			The horizontal size
	 * @param 	vSize
	 * 			The vertical size
	 * @return	True if and only if when the given values
	 * 			are larger or equal to the minimal values.
	 */
	private boolean isValidSize(int hSize, int vSize) {
		return hSize >= Game.MIN_HSIZE && vSize >= Game.MIN_VSIZE;
	}

	/**
	 * Set the grid for this grid builder
	 * 
	 * @param 	grid
	 * 			The grid to be set
	 */
	private void setGrid(Square[][] grid){
		this.grid = grid;
	}

	/**
	 * Returns the grid constructed in the grid builder.
	 * 
	 * @return	The grid
	 */
	private Square[][] getGrid(){
		return this.grid;
	}

	/**
	 * Creator method for squares
	 * 
	 * @return	A new square
	 */
	private Square createSquare(){
		return new Square();
	}

	/**
	 * Construct the grid for this grid builder.
	 */
	public void constructSquares(){
		Square[][] grid = new Square[hSize][vSize];
		for(int i = 0; i < hSize; i++){
			for(int j = 0; j < vSize; j++){
				Square s = createSquare();
				grid[i][j] = s;
				board.put(new Coordinate2D(i,j), s);
			}
		}
		this.grid = grid;
		connect();
		this.bottomLeft = grid[0][0];
		this.topRight = grid[hSize-1][vSize-1];
		//Initialize freeFields
		for (int i = 0; i < grid.length; i++) {
			freeFields.addAll(Arrays.asList(grid[i]));
		}
	}

	/**
	 * Connect the squares in the grid of this grid builder.
	 * 
	 * @param 	grid
	 * 			The grid containing the squares to be connected.
	 */
	private void connect(){
		Square[][] grid = getGrid();
		for(int i = 0; i < hSize; i++){
			for(int j = 0; j < vSize; j++){
				Square square = grid[i][j];
				for(Direction direction : Direction.values()){
					try {
						Square otherSquare = neighborInGrid(i,j, direction);
						connect(square, direction, otherSquare);
					} catch (IndexOutOfBoundsException e) {
						// Nothing to do here, we expect this to happen.
					}
				}
			}
		}
	}


	/**
	 * Get the square from the grid position in the given direction from the square
	 * located at the given location.
	 * 
	 * @param 	i
	 * 			The horizontal location of the square
	 * @param 	j
	 * 			The vertical location of the square
	 * @param 	direction
	 * 			The direction in which the neighbor is requested
	 * @return	The square in the given direction from the square at
	 * 			the given location.
	 */
	private Square neighborInGrid(int i, int j, Direction direction) throws IndexOutOfBoundsException {
		int x = i; 
		int y = j;
		direction.getNeighborCoordinate(new Coordinate2D(i,j));
		if(x >= 0 && y >= 0 && x < hSize && y < vSize){
			Square[][] grid = getGrid();
			return grid[x][y];
		}
		throw new IndexOutOfBoundsException();
	}

	/**
	 * Construct walls randomly within the limitations of this grid builder.
	 */
	public void constructWalls() {
		freeFields.remove(this.bottomLeft);
		freeFields.remove(this.topRight);

		int randomCoverage = 2 + (int) Math.floor((this.getMaxCoverage() - 2)
				* Math.random());


		int coverage = randomCoverage;
		while (coverage >= 2) {
			Orientation orientation = Orientation.getRandomOrientation();
			int maxlength = this.getMaxLengthWall(orientation);
			int length = (int) (2 + Math
					.floor(((maxlength - 2) * Math.random()))); // Upper limit
			// of the length
			ArrayList<Square> sequence = getRandomSquareSequence(freeFields,
					orientation);
			if (sequence.size() < 2) {
				coverage--;
				continue; 
			}
			if (sequence.size() <= length) {
				Wall wall = new Wall(sequence);
				freeFields.removeAll(getNeighborWalls(sequence));
				coverage -= sequence.size();
			}
			// Implement iterator in wall?
			if (sequence.size() > length) {
				int size = sequence.size();
				ArrayList<Square> blocks = new ArrayList<Square>(
						sequence.subList(0, length));
				Wall wall = new Wall(blocks);
				freeFields.removeAll(getNeighborWalls(blocks));
				coverage -= length;
			}
		}
		
		this.wallRepresentation();
	}

	
	private void createWall(ArrayList<Square> blocks){
		
	}

	/**
	 * Covers 5 percent of the field with grenades. Grenades cannot be placed on
	 * the starting position of a player or on a wall
	 */
	private void placeGrenades() {

		int grenades = getNumberOfGrenades();
		// Remove startpositions
		freeFields.remove(getBottomLeft());
		freeFields.remove(getTopRight());

		// Add the grenades to the squares, random distribution
		Random generator = new Random();
		int i = 0;
		while (i < grenades && !freeFields.isEmpty()) {
			int l = freeFields.size();
			Square s = freeFields.get(generator.nextInt(l));
			if (!s.isObstructed()) {
				s.getInventory().addItem(new LightGrenade());
				i++;
			}
			freeFields.remove(s);

		}

	}

	/**
	 * 
	 * Returns the number of grenades on the grid.
	 * @return the number of grenades on the grid.
	 */
	public int getNumberOfGrenades(){
		return (int) (Math.ceil(this.getGridSize() * PERCENTAGEGRENADES));
	}



	/**
	 * Returns an arrayList which contains all the neighbors of the given wall.
	 * Contains also the wall. ( may contain duplicates)
	 * 
	 * @param 	sequence 	the building blocks of the wall (squares)
	 * @return 	all the neighboring squares of a wall and all the 
	 * 			squares included in the wall
	 */
	private ArrayList<Square> getNeighborWalls(ArrayList<Square> sequence){
		ArrayList<Square> result = new ArrayList<Square>();
		for(Square s: sequence){
			result.addAll();
		}
		result.addAll(sequence);
		return result;
	} 
	/**
	 *
	 * returns the maximal permitted length of wall, which depends on the
	 * orientation of the wall
	 *
	 * @param orientation the orientation of the wall
	 * @return the maximal length which is permitted for a wall in this grid
	 * @throws IllegalArgumentException
	 *
	 */
	private int getMaxLengthWall(Orientation orientation) throws IllegalArgumentException{
		switch(orientation){
		case HORIZONTAL:
			return (int) (this.hSize * MAX_LENGTHPERCENTAGEWALL);
		case VERTICAL:
			return (int) (this.vSize * MAX_LENGTHPERCENTAGEWALL);
		default:	
			throw new IllegalArgumentException("This orientation is not supported:" + orientation);
		}
	}


	/**
	 * Returns the maximal number of squares covered by walls
	 * @return the max number of squares covered by walls
	 */
	public int getMaxCoverage() {
		return (int) ((vSize*hSize) * MAX_PERCENTAGEWALLS);
	}

	/**
	 * Selects a random square in the given HashMap with squares
	 * and builds the longest possible sequence of squares in the given orientation
	 *
	 * @param squares the possible squares that can be used for this randomSequence
	 * @param orientation the orientation in which the wall faces
	 * @return a list of squares which are in sequence, but the first square is
	 * selected randomly and the orientation is given
	 */
	private ArrayList<Square> getRandomSquareSequence(ArrayList<Square> squares, 
			Orientation orientation) throws IllegalArgumentException{

		ArrayList<Square> sequence;
		Random ran = new Random();

		Square rand = squares.get(ran.nextInt(squares.size()));
		

		switch(orientation){
		case HORIZONTAL:
			sequence = getHorizontalSequence(rand, squares );
			break;
		case VERTICAL:	
			sequence = getVerticalSequence(rand, squares);
			break;
		default:	
			throw new IllegalArgumentException("This orientation is not supported:" + orientation);
		}

		return sequence;
	}

	/**
	 *
	 * Builds the longest possible horizontal sequence of squares with a given start position
	 * @param start the given start position
	 * @param squares The possible squares
	 * @return the longest possible horizontal sequence of squares
	 */
	private ArrayList<Square> getHorizontalSequence(Square start, ArrayList<Square> squares){

		ArrayList<Square> sequence = new ArrayList<Square>();
		sequence.add(start);

		try{
			Square x = start.getNeighbor(Direction.EAST) ;
			while(squares.contains(x)){
				sequence.add(x);
				try{
					x = x.getNeighbor(Direction.EAST);
				} catch(Exception ex){
					//Happens at the border of the grid.
					break;
				}
			}
		} catch(Exception e){}

		try{
			Square y = start.getNeighbor(Direction.WEST) ;
			while(squares.contains(y)){
				sequence.add(y);
				try{
					y = y.getNeighbor(Direction.WEST);
				} catch(Exception ex){
					//Happens at the border of the grid.
					break;
				}
			}
		} catch(Exception e){}
		return sequence;
	}

	private void wallRepresentation(){
		ArrayList<Coordinate2D> coors = new ArrayList<Coordinate2D>();
		for(int i = 0; i <grid.length; i++ ){
			for(int j = 0; j < grid[i].length; j++){
				if(grid[i][j].isObstructed()){
					coors.add(new Coordinate2D(i, grid[i].length - 1 - j)); 
				}
			}
		}
		ApplicationWindow.GRID_MODEL.setWalls(coors);
	}
	/**
	 *
	 * Builds the longest possible horizontal sequence of squares with a given start position
	 * @param start the given start position
	 * @param squares The possible squares
	 * @return the longest possible horizontal sequence of squares
	 */
	private ArrayList<Square> getVerticalSequence(Square start, ArrayList<Square> squares){

		ArrayList<Square> sequence = new ArrayList<Square>();
		sequence.add(start);
		try{
			Square x = start.getNeighbor(Direction.NORTH) ;
			while(squares.contains(x)){
				sequence.add(x);
				try{
					x = x.getNeighbor(Direction.NORTH);
				} catch(Exception ex){
					//Happens at the border of the grid.
					break;
				}
			}
		}
		catch(Exception e){//no neighbors in that direction
		}

		try{
			Square y = start.getNeighbor(Direction.SOUTH) ;
			while(squares.contains(y)){
				sequence.add(y);
				try{
					y = y.getNeighbor(Direction.SOUTH);
				} catch(Exception ex){
					//Happens at the border of the grid.
					break;
				}
			}
		}catch(Exception e){}
		return sequence;
	}



	// TODO: Testing method
	public String getCoordinate(Square square){
		for(int i = 0; i < hSize; i++){
			for(int j = 0; j < vSize; j++){
				if(grid[i][j] == square){
					return "("+i+","+j+")";
				}
			}
		}
		return "";
	}



	/**
	 * Connect the given square with the other square in the given direction
	 * 
	 * @param 	square
	 * 			The square to connect
	 * @param 	direction
	 * 			The direction in which to connect the squares
	 * @param 	otherSquare
	 * 			The other square to connect the square to
	 */
	private void connect(Square square, Direction direction, Square otherSquare){
		try{
			square.setNeighbor(direction, otherSquare);
		} catch (Exception e){

		}
	}

	/**
	 * Return the bottom left square of the constructed grid
	 * 
	 * @return	The bottom left square of the constructed grid
	 */
	public Square getBottomLeft(){
		return this.bottomLeft;
	}

	/**
	 * Return the top right square of the constructed grid
	 * 
	 * @return	The top right square of the constructed grid
	 */
	public Square getTopRight(){
		return this.topRight;
	}

	public int getGridSize(){
		return this.vSize * this.hSize;
	}


}
