package item.inter;

import effect.Effect;
import square.Square;

public interface Movable {

    /**
     * Moves the movable to the given square.
     *
     * Moving can involve getting affected by the square there is been moved on.
     *
     * @param square
     * @throws IllegalStateException
     */
	public void move(Square square) throws IllegalStateException;

	/**
	 * Sets the current position of the movable.
     *
	 * @param square
	 */
	public void setPosition(Square square);

    public void resetPosition(Square square);

	/**
	 * Returns the current position of the movable.
	 * @return
	 */
	public Square getPosition();

    public Square getPreviousPosition();

    /**
     * Returns the range of the movable object.
     * @return
     */
    public int getRange();

    /**
     * Resets the range of the movable object to it's original range.
     */
    public void resetRange();

}