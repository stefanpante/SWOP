/**
 * 
 */
package square;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import square.obstacles.Wall;

/**
 * @author jonas
 *
 */	
public class GridBuilder {
	
	/**
	 * the minimum vertical and horizontal size are 10 squares.
	 */
	public static int MIN_VSIZE = 10;
	public static int MIN_HSIZE = 10;

	/**
	 * Max percentage of squares covered by walls.
	 */
	public static float MAX_PERCENTAGEWALLS = 0.2f;
	
	

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
	
	/**
	 * The square at the bottom left of this grid.
	 */
	private Square bottomLeft; 
	
	/**
	 * The square at the top right of this grid.
	 */
	private Square topRight;
	
	/**
	 * Random generator
	 */
	private static final Random RANDOM = new Random();

	
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
		return hSize >= MIN_HSIZE && vSize >= MIN_VSIZE;
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
				grid[i][j] = createSquare();
			}
		}
		this.grid = grid;
		connect();
		this.bottomLeft = grid[0][0];
		this.topRight = grid[hSize-1][vSize-1];
		
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
		switch (direction) {
		case NORTH:
			y++;
			break;
		case NORTHEAST:
			x++; y++; 
			break;
		case EAST:
			x++;
			break;
		case SOUTHEAST:
			x++; y--;
			break;
		case SOUTH:
			y--;
			break;
		case SOUTHWEST:
			x--;y--;
			break;
		case WEST:
			x--;
			break;
		case NORTHWEST:
			x--; y++;
			break;
		}
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
		ArrayList<Square> candidates = new ArrayList<Square>();
		for (int i = 0; i < grid.length; i++) {
			candidates.addAll(Arrays.asList(grid[i]));
		}

		candidates.remove(this.bottomLeft);
		candidates.remove(this.topRight);

		int randomCoverage = 2 + (int) Math.floor((this.getMaxCoverage() - 2)
				* Math.random());

		int amountOfWalls = 1 + (int) Math.floor((randomCoverage / 2)
				* Math.random());

		int coverage = randomCoverage;
		while (coverage >= 2) {
			Orientation orientation = Orientation.getRandomOrientation();
			int maxlength = this.getMaxLengthWall(orientation);
			int length = (int) (2 + Math
					.floor(((maxlength - 2) * Math.random()))); // Upper limit
																// of the length
			ArrayList<Square> sequence = getRandomSquareSequence(candidates,
					orientation);
			if (sequence.size() < 2) {
				coverage--;
				continue;
			}
			if (sequence.size() <= length) {
				Wall wall = new Wall(sequence);
				candidates.removeAll(getNeighborWalls(sequence));
				coverage -= sequence.size();
			}
			// Implement iterator in wall?
			if (sequence.size() > length) {
				int size = sequence.size();
				ArrayList<Square> blocks = new ArrayList<Square>(
						sequence.subList(0, length));
				Wall wall = new Wall(blocks);
				candidates.removeAll(getNeighborWalls(blocks));
				coverage -= length;
			}
		}

	}
	
	private ArrayList<Square> getNeighborWalls(ArrayList<Square> sequence){
		ArrayList<Square> result = new ArrayList<Square>();
		for(Square s: sequence){
			if(!result.contains(s)) result.addAll(s.getNeighborsAsList());
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
		
		Square x = start.getNeighor(Direction.EAST) ;
		while(squares.contains(x)){
			sequence.add(x);
			try{
				x = x.getNeighor(Direction.EAST);
			} catch(Exception ex){
				//Happens at the border of the grid.
				break;
			}
		}
		
		Square y = start.getNeighor(Direction.WEST) ;
		while(squares.contains(y)){
			sequence.add(y);
			try{
				y = y.getNeighor(Direction.WEST);
			} catch(Exception ex){
				//Happens at the border of the grid.
				break;
			}
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
	private ArrayList<Square> getVerticalSequence(Square start, ArrayList<Square> squares){

		ArrayList<Square> sequence = new ArrayList<Square>();
		sequence.add(start);
		
		Square x = start.getNeighor(Direction.NORTH) ;
		while(squares.contains(x)){
			sequence.add(x);
			try{
				x = x.getNeighor(Direction.NORTH);
			} catch(Exception ex){
				//Happens at the border of the grid.
				break;
			}
		}
		
		Square y = start.getNeighor(Direction.SOUTH) ;
		while(squares.contains(y)){
			sequence.add(y);
			try{
				y = y.getNeighor(Direction.SOUTH);
			} catch(Exception ex){
				//Happens at the border of the grid.
				break;
			}
		}
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
	 * Build a wall with a given maximum length starting at the given square
	 * 
	 * @param 	square
	 * 			The square where the wall starts
	 * @param 	maxLength
	 * 			The maximal length of the wall
	 */
	private Wall buildWall(Square square, int maxLength) throws IllegalStateException {
		Direction direction = getRandomDirection();
		
		if(!square.hasNeigbor(direction))
			throw new IllegalStateException("A wall needs at least two neighboring squares");
		
		Square lastSquare = square.getNeighor(direction);
		Wall wall = new Wall(square, lastSquare);
		
		while(lastSquare.hasNeigbor(direction) && wall.getLength() <= maxLength){
			lastSquare = lastSquare.getNeighor(direction);
			wall.addSquare(lastSquare);
		}
				
		if(wall.contains(getBottomLeft()) || wall.contains(getTopRight()))
			throw new IllegalStateException("A wall cannot contain the bottom left or top right square");
		
		
		return wall;
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
			square.setNeigbor(direction, otherSquare);
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
	
	private Square getRandomSquare(){
		int x = RANDOM.nextInt(hSize);
		int y = RANDOM.nextInt(vSize);
		return getGrid()[x][y];
	}
	
	private Direction getRandomDirection(){
		if(RANDOM.nextBoolean())
			return Direction.NORTH;
		return Direction.EAST;
	}

}
