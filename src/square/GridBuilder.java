/**
 * 
 */
package square;

import game.Game;

import java.util.Random;

import square.obstacles.Wall;

/**
 * @author jonas
 *
 */
public class GridBuilder {
	


	/**
	 * Max percentage of squares covered by walls.
	 */
	public static float MAX_PERCENTAGEWALLS = 0.2f;
	
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
	public void constructWalls(){
		int wallLimit = (int) (getGridSize() * MAX_PERCENTAGEWALLS);
		int wallSize = RANDOM.nextInt(wallLimit-1)+1;
		while(wallSize >= 2){
			try {
				Wall wall = buildWall(getRandomSquare(), wallSize);
				wallSize -= wall.getLength();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
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
