package board;

import java.util.ArrayList;
import java.util.HashMap;

import square.obstacles.Wall;
import utils.Coordinate2D;

public class Board {

	HashMap<Coordinate2D, Square> squares;
	ArrayList<Wall> walls;
	public Board(int hSize, int vSize) {
		BoardBuilder builder = new BoardBuilder(hSize, vSize);
		this.squares = builder.getSquares();
		this.walls = builder.getWalls();
		
	}
	
	public ArrayList<Wall> getWalls(){
		return walls;
	}
	
	public HashMap<Coordinate2D, Square> getSquares(){
		return squares;
	}

}
