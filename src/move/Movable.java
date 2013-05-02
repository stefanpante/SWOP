package move;

import square.Direction;
import square.Square;

public interface Movable {

	public void doMove(Square currentSquare, Direction direction);
	
	public void setPosition(Square square);
	
	public void setMoveMediator(MoveMediator moveMediator);
	
	public MoveMediator getMoveMediator();
}