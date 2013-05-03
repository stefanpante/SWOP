package move;

import square.Square;

public interface Movable {

    public void getsAffectedBy(MovableEffect effect);
	public void move(Square square) throws IllegalStateException;
	/**
	 * Sets the current position of the movable
	 * @param square
	 */
	public void setPosition(Square square);
	/**
	 * Returns the current position of the movable
	 * @return
	 */
	public Square getPosition();
    public int getRange();
    public void resetRange();
	public boolean justTeleported();
	public void setJustTeleported(boolean b);
}