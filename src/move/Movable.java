package move;

import square.Square;

public interface Movable {

    public void getsAffectedBy(Square square);
	public void setPosition(Square square) throws IllegalStateException;
    public int getRange();
}