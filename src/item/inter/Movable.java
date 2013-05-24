package item.inter;

import square.Square;

public interface Movable {

    /**
     * Moves the movable to the given square.
     *
     * Moving can involve getting affected by the square there is been moved on.
     *
     * @param   square
     *          The square to move to
     * @throws IllegalStateException
     */
	public void move(Square square) throws IllegalStateException;

	/**
	 * Sets the current position of the movable.
     *
	 * @param   square
     *          The square to set the position of this movable to.
	 */
	public void setPosition(Square square);

    public void resetPosition(Square square);

	/**
	 * Returns the current position of the movable.
	 *
     * @return  The position of this movable.
	 */
	public Square getPosition();

    public Square getPreviousPosition();

    /**
     * Returns the range of the movable object.
     *
     * @return  The range of this Movable.
     */
    public int getRange();

    /**
     * Resets the range of the movable object to it's original range.
     */
    public void resetRange();

}