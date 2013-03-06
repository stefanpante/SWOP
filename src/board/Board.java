package board;

import java.util.ArrayList;
import java.util.HashMap;

import square.obstacles.Wall;
import utils.Coordinate2D;

public class Board {

	/**
	 * HashMap containing all the squares
	 */
	private HashMap<Coordinate2D, Square> squares;
	
	/**
	 * List containing all the walls
	 */
	private ArrayList<Wall> walls;
	
	/**
	 * Constructs a new Board with the given dimensions.
	 * @param hSize the horizontal size of the board
	 * @param vSize	the vertical size of the board
	 */
	public Board(int hSize, int vSize) {
		BoardBuilder builder = new BoardBuilder(hSize, vSize);
		this.squares = builder.getSquares();
		this.walls = builder.getWalls();
		
	}
	
	/**
	 * Returns all the walls on the board.
	 * @return the walls on the board.
	 */
	public ArrayList<Wall> getWalls(){
		return walls;
	}
	
	/**
	 * Returns all the squares of which the board consists.
	 * @return the squares of the board.
	 */
	public HashMap<Coordinate2D, Square> getSquares(){
		return squares;
	}
	
	/**
	 * Checks if a move to the specified coordinate is valid.
	 * @param coordinate the coordinate which needs to be checked
	 */
	public boolean  isValidMove(Coordinate2D coordinate){
		return squares.get(coordinate).isObstructed();
	}

}
