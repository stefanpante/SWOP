package square;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Abstract class that functions as a container for multiple squares.
 * This is used for MultiObstacle and Field.
 */
public abstract class MultiSquare {

    /**
     * An MultiSquare covers a set of squares.
     */
    private ArrayList<Square> squares;

    /**
     * Create a new MutliSquare
     */
    public MultiSquare(){
        squares = new ArrayList<Square>();
    }

    /**
     * Returns a list of squares which this MultiSquare covers.
     */
    public ArrayList<Square> getSquares() {
        return new ArrayList<Square>(this.squares);
    }

    /**
     * Checks if a square is part of this MutliSquare.
     *
     * @param	square	Square to be checked
     * @return	True if and only if the square is part of this MutliObstacle
     */
    public boolean contains(Square square){
        return this.squares.contains(square);
    }


    /**
     * Adds a square to this MultiSquare
     *
     * @param   square
     *          The square to be added
     * @throws  IllegalArgumentException
     *          If a duplicate square is given an exception is thrown.
     */
    public void addSquare(Square square) throws IllegalArgumentException{
        if(!isValidSquare(square))
            throw new IllegalArgumentException("Cannot add square to this MultiObstacle: the square is invalid.");
        squares.add(square);
    }

    /**
     * Adds a square to this MultiSquare at the given position
     *
     * @param   i
     *          The position of the square to be added
     * @param   square
     *          The square to be added
     * @throws  NoSuchElementException
     *          If there is no square at the given position
     */
    public void addSquare(int i, Square square){
        if(!isValidSquare(square))
            throw new NoSuchElementException("Cannot add square to this MultiObstacle: the square is invalid.");
        squares.add(i,square);
    }

    /**
     * Get the square of this MultiSquare at the given position
     *
     * @param   i
     *          The position of the square to be added
     * @throws  NoSuchElementException
     *          If there is no square at the given position
     */
    public Square getSquare(int i){
        if(i >= squares.size())
            throw new NoSuchElementException("There is no square at the given position");
        return squares.get(i);
    }

    public void removeSquare(int i){
        if(i >= squares.size())
            throw new NoSuchElementException("There is no square at the given position");
        squares.remove(i);
    }

    /**
     * Removes a square of this MultiSquare.
     *
     * @param 	square
     *          The square to be removed from this MultiSquare
     * @throws 	IllegalArgumentException
     * 			If the square is not
     */
    public void removeSquare(Square square) throws IllegalArgumentException {
        if(!getSquares().contains(square)){
            throw new IllegalArgumentException("A square that is not added can not part of the obstacle cannot be removed.");
        }else{
            squares.remove(square);
        }
    }

    /**
     * Check whether the given square can be added to this MutliSquare.
     *
     * @param 	square
     * 			The square to be checked
     * @return	True if and only the square is not not null and not yet part of the MutliSquare
     */
    public boolean isValidSquare(Square square){
        return square != null && !getSquares().contains(square);
    }

    /**
     * Get the current length of the obstacle.
     */
    public int getLength() {
        return getSquares().size();
    }

    @Override
    public boolean equals(Object o) {
        MultiSquare that = (MultiSquare) o;

        if(getSquares().size() != that.getSquares().size())
            return false;

        for(Square square : getSquares()){
            if(!that.getSquares().contains(square))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return squares.hashCode();
    }

    @Override
    public String toString() {
        String s = "";
        for(Square square: getSquares()){
            s += "("+square.hashCode()+")";
        }
        return s;
    }
}
