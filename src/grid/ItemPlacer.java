package grid;

import java.util.ArrayList;
import java.util.Random;

import item.Item;
import square.Square;
import util.Coordinate;

/**
 * Class used to place all the items on the grid.
 * 
 *@author Dieter Castel, Jonas Devlieghere and Stefan Pante
 */
public abstract class ItemPlacer {
	
	/**
	 * The grid on which the items are placed
	 */
	private Grid grid;
	
	/**
	 * The constraint for placing the items;
	 */
	private GridConstraint itemConstraint;
	
	/**
	 * Random used for random locations in the grid.
	 */
	private Random random;
	
	/**
	 * 
	 * @param grid
	 */
	public ItemPlacer(Grid grid){
		this.grid = grid;
		this.random = new Random();
	}
	
	/**
	 * Sets the constraint for the placement of the items.
	 * @param itemConstraint	the constraint for the item
	 */
	public void setItemConstraint(GridConstraint itemConstraint){
		this.itemConstraint = itemConstraint;
	}
	
	/**
	 * returns the constraint for the item placement.
	 * @return
	 */
	public GridConstraint getItemConstraint(){
		return this.itemConstraint;
	}
	
	/**
	 * Place an item on the given coordinate
	 * 
	 * @param 	square
	 * 			The coordinate to place the given item on
	 * @param 	item
	 * 			The item to be placed on the given coordinate
	 */
	protected void placeItem(Square square, Item item) throws IllegalArgumentException {
		if(square.isObstructed())
			return;
			//			throw new IllegalArgumentException("Cannot place an object on a square that is obstructed.");
		square.getInventory().addItem(item);
	}
	
	public abstract void placeItems();
	
	
	
	/**
     * Selects a number of random coordinates ( in respect to the itemConstraint)
     * corresponding to Squares on the grid.
     * @return	An arrayList with coordinates which satisfy the itemConstraint.
     */
    public ArrayList<Coordinate> getRandomLocations(){
        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
        ArrayList<Coordinate> candidates = getGrid().getAllCoordinates();
        int max = (int) (getItemConstraint().getPercentage() * getGrid().getAllSquares().size());
        // Removed excluded squares from candidates
        candidates.removeAll(getItemConstraint().getExcluded());
        // Removed obstructed squares from candidates
        ArrayList<Coordinate> toBeRemoved = new ArrayList<Coordinate>();
        for(Coordinate coordinate : candidates){
            if(getGrid().getSquare(coordinate).isObstructed())
                toBeRemoved.add(coordinate);
        }
        candidates.removeAll(toBeRemoved);

        for(ArrayList<Coordinate> includes : getItemConstraint().getIncluded()){
            Coordinate candidate = includes.get(getRandomIndex(includes));
            while(!candidates.contains(candidate)){
                candidate = includes.get(getRandomIndex(includes));
            }
            coordinates.add(candidate);
            candidates.remove(candidate);
        }

        // Keep adding coordinates from candidates until max is reached
        while(coordinates.size() < max){
            Coordinate candidate = candidates.get(getRandomIndex(candidates));
            coordinates.add(candidate);
            candidates.remove(candidate);
        }

        return coordinates;
    }
    
    /**
	 * Returns a random index inside an arrayList
	 * @param a	the list of which a random index is selected.
	 */
    protected int getRandomIndex(@SuppressWarnings("rawtypes") ArrayList a){
        return random.nextInt(a.size());
    }
    
    public Grid getGrid(){
    	return this.grid;
    }
	
	

}
