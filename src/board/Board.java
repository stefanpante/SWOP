package board;

import java.util.ArrayList;
import java.util.HashMap;

import utils.Coordinate2D;

public class Board {

	HashMap<Coordinate2D, Square> squares;
	ArrayList<Wall> walls;
	public Board(int hSize, int vSize) {
		GridBuilder grid = new GridBuilder(hSize, vSize);
		
	}

}
