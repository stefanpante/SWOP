/**
 * 
 */
package square;

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
	 * Percentage of square covered by grenades.
	 */
	public static float PERCENTAGEGRENADES = 0.05f;

	/**
	 * Max percentage of squares covered by walls.
	 */
	public static float MAX_PERCENTAGEWALLS = 0.2f;

	/**
	 * Percentage of max length of a wall.
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
	 * Create a new GridBuilder with the given dimension.
	 * 
	 * @param 	hSize
	 * 			The horizontal size for this grid builder
	 * @param 	vSize
	 * 			The vertical size for this grid builder
	 */
	public GridBuilder(int hSize, int vSize){
		this.hSize = hSize;
		this.vSize = vSize;
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
	public void construct(){
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
	
	
	private void setGrid(Square[][] grid){
		this.grid = grid;
	}
	
	private Square[][] getGrid(){
		return this.grid;
	}
	
	/**
	 * Connect the squares in the grid of this grid builder.
	 * 
	 * @param 	grid
	 * 			The grid containing the squares to be connected.
	 */
	public void connect(){
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
	public Square neighborInGrid(int i, int j, Direction direction) throws IndexOutOfBoundsException {
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
		Random random = new Random();
		int wallLimit = (int) (getGridSize() * MAX_PERCENTAGEWALLS);
		int wallSize = random.nextInt(wallLimit-1)+1;
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
	public Wall buildWall(Square square, int maxLength) throws IllegalStateException {
		Random random = new Random();
		boolean vertical = random.nextBoolean();
		Direction direction;
		if(vertical){
			direction = Direction.NORTH;
		}else{
			direction = Direction.EAST;
		}
		if(!square.hasNeigbor(direction))
			throw new IllegalStateException();
		Square lastSquare = square.getNeighor(direction);
		Wall wall = new Wall(square, lastSquare);
		lastSquare = lastSquare.getNeighor(direction);
		while(lastSquare.hasNeigbor(direction) && wall.getLength() <= maxLength){
			wall.addSquare(lastSquare);
			lastSquare = lastSquare.getNeighor(direction);
		}
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
	public void connect(Square square, Direction direction, Square otherSquare){
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
		Random  random = new Random();
		int x = random.nextInt(hSize);
		int y = random.nextInt(vSize);
		return getGrid()[x][y];
	}

}
