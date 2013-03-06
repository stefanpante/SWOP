package board.obstacles;

import board.Square;

public class Brick extends Square {
	
	public Brick(){
		super();
	}
	
	@Override
	public boolean isObstructed(){
		return true;
	}

}
