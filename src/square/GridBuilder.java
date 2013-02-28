/**
 * 
 */
package square;

/**
 * @author jonas
 *
 */
public class GridBuilder {
	
	private int hSize;
	private int vSize;
	private Square bottomLeft; 
	private Square topRight;
	
	public GridBuilder(int hSize, int vSize){
		this.hSize = hSize;
		this.vSize = vSize;
	}
	
	private Square createSquare(){
		return new Square();
	}
	
	public void construct(){
		Square[][] grid = new Square[hSize][vSize];
		for(int i = 0; i < hSize; i++){
			for(int j = 0; j < vSize; j++){
				grid[i][j] = createSquare();
			}
		}
		connect(grid);
		this.bottomLeft = grid[0][0];
		this.topRight = grid[hSize-1][vSize-1];
		
	}
	
	public void connect(Square[][] grid){
		for(int i = 0; i < hSize; i++){
			for(int j = 0; j < vSize; j++){
				Square square = grid[i][j];
				for(Direction direction : Direction.values()){
					try {
						Square otherSquare = squareFromGrid(grid, i,j, direction);
						connect(square, direction, otherSquare);
					} catch (IllegalAccessException e) {
						// Do nothing
					}
				}
			}
		}
	}
	


	/**
	 * @param i
	 * @param j
	 * @param direction
	 * @return
	 */
	private Square squareFromGrid(Square[][] grid, int i, int j, Direction direction) throws IllegalAccessException {
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
		if(x >= 0 && y >= 0 && x < hSize && y < vSize)
			return grid[x][y];
		throw new IllegalAccessException();
	}

	public void connect(Square square, Direction direction, Square otherSquare){
		try{
			square.setNeigbor(direction, otherSquare);
		} catch (Exception e){
			
		}
	}
	

	
	public Square getBottomLeft(){
		return this.bottomLeft;
	}
	
	public Square getTopRight(){
		return this.topRight;
	}

}
