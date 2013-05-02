package move;

import square.Square;

public interface Movable {

    public void getsAffectedBy(MovableEffect effect);
	public void setPosition(Square square) throws IllegalStateException;
    public int getRange();
    public void resetRange();
}