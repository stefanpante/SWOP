package square.multi;

import square.GridElement;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * User: jonas
 * Date: 23/05/13
 * Time: 15:28
 */
public class MultiGridElement<T extends GridElement> {

    /**
     * An MultiGridElement covers a set of Grid Elements.
     */
    private final ArrayList<T> gridElements;

    /**
     * Create a new MutliGridElement
     */
    protected MultiGridElement(){
        gridElements = new ArrayList<>();
    }

    /**
     * Returns a list of Grid Elements which this MutliGridElement covers.
     */
    public ArrayList<T> getGridElements() {
        return new ArrayList<>(this.gridElements);
    }

    /**
     * Checks if a gridElement is part of this MutliGridElement.
     *
     * @param	gridElement
     *          GridElement to be checked
     * @return	True if and only if the gridElement is part of this MutliObstacle
     */
    public boolean contains(T gridElement){
        return this.gridElements.contains(gridElement);
    }


    /**
     * Adds a gridElement to this MultiGridElement
     *
     * @param   gridElement
     *          The gridElement to be added
     * @throws  IllegalArgumentException
     *          If a duplicate gridElement is given an exception is thrown.
     */
    public void addGridElement(T gridElement) throws IllegalArgumentException{
        if(!isValidGridElement(gridElement))
            throw new IllegalArgumentException("Cannot add gridElement to this MultiObstacle: the gridElement is invalid.");
        gridElements.add(gridElement);
    }

    /**
     * Adds a gridElement to this MultiGridElement at the given position
     *
     * @param   i
     *          The position of the gridElement to be added
     * @param   gridElement
     *          The gridElement to be added
     * @throws java.util.NoSuchElementException
     *          If there is no gridElement at the given position
     */
    protected void addGridElement(int i, T gridElement){
        if(!isValidGridElement(gridElement))
            throw new NoSuchElementException("Cannot add gridElement to this MultiObstacle: the gridElement is invalid.");
        gridElements.add(i, gridElement);
    }

    /**
     * Get the gridElement of this MultiGridElement at the given position
     *
     * @param   i
     *          The position of the gridElement to be added
     * @throws  NoSuchElementException
     *          If there is no gridElement at the given position
     */
    protected T getGridElement(int i){
        if(i >= gridElements.size())
            throw new NoSuchElementException("There is no gridElement at the given position");
        return gridElements.get(i);
    }

    public void removeGridElement(int i){
        if(i >= gridElements.size())
            throw new NoSuchElementException("There is no gridElement at the given position");
        gridElements.remove(i);
    }

    /**
     * Removes a gridElement of this MultiGridElement.
     *
     * @param 	gridElement
     *          The gridElement to be removed from this MultiGridElement
     * @throws 	IllegalArgumentException
     * 			If the gridElement is not
     */
    protected void removeGridElement(T gridElement) throws IllegalArgumentException {
        if(!getGridElements().contains(gridElement)){
            throw new IllegalArgumentException("A gridElement that is not added can not part of the obstacle cannot be removed.");
        }else{
            gridElements.remove(gridElement);
        }
    }

    /**
     * Check whether the given gridElement can be added to this MutliGridElement.
     *
     * @param 	gridElement
     * 			The gridElement to be checked
     * @return	True if and only the gridElement is not not null and not yet part of the MutliGridElement
     */
    public boolean isValidGridElement(T gridElement){
        return gridElement != null && !getGridElements().contains(gridElement);
    }

    /**
     * Get the current length of the obstacle.
     */
    public int getLength() {
        return getGridElements().size();
    }
}
