package grid.itemplacer;

import game.Player;
import grid.Grid;
import grid.GridConstraint;
import item.Item;
import square.Square;
import util.Coordinate;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Class used to place all the items on the grid.
 * 
 *@author Dieter Castel, Jonas Devlieghere and Stefan Pante
 */
public abstract class ItemPlacer {
	
	/**
	 * The grid on which the items are placed
	 */
	private final Grid grid;
	
	/**
	 * The players. Various constraints are in respect to the players.
	 */
	
	private final ArrayList<Player> players;
	/**
	 * The constraint for placing the items;
	 */
	private GridConstraint itemConstraint;
	
	/**
	 * Random used for random locations in the grid.
	 */
	private final Random random;
	
	/**
	 * Creates a new ItemPlacer
	 * @param grid	the grid on which the items should be placed.
	 */
    ItemPlacer(Grid grid, ArrayList<Player> players){
		this.grid = grid;
		this.players = players;
		this.random = new Random();
	}
	
	/**
	 * Sets the constraint for the placement of the items.
	 * @param itemConstraint	the constraint for the item
	 */
    void setItemConstraint(GridConstraint itemConstraint){
		this.itemConstraint = itemConstraint;
	}
	
	/**
	 * returns the constraint for the item placement.
	 * @return
	 */
    GridConstraint getItemConstraint(){
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
    void placeItem(Square square, Item item) throws IllegalArgumentException {
		if(square == null || square.isObstacle())
			return;
			//			throw new IllegalArgumentException("Cannot place an object on a square that is obstructed.");

		square.addItem(item);
	}
	
	public abstract void placeItems();
	
	
	
	/**
     * Selects a number of random coordinates ( in respect to the itemConstraint)
     * corresponding to Squares on the grid.
     * @return	An arrayList with coordinates which satisfy the itemConstraint.
     */
    ArrayList<Coordinate> getLocations(){
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        ArrayList<Coordinate> candidates = getGrid().getAllCoordinates();
        int max = (int) (getItemConstraint().getPercentage() * getGrid().getAllGridElements().size());
        // Removed excluded squares from candidates
        candidates.removeAll(getItemConstraint().getExcluded());
        // Removed obstructed squares from candidates
        ArrayList<Coordinate> toBeRemoved = new ArrayList<>();
        for(Coordinate coordinate : candidates){
            if(getGrid().getGridElement(coordinate).isObstacle())
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
     * Returns a list with all the coordinates forming a size X size
     * rectangle with start as center.
     * @param   start
     *          The center of the square that will be returned.
     * @param   size
     *          The amount of coordinates surrounding the starting coordinate.
     * @return A list of coordinates surrounding the starting coordinate
     */
    protected ArrayList<Coordinate> getSquaredLocation(Coordinate start, int size){
		ArrayList<Coordinate> coordinates = new ArrayList<>();
        int centerX = start.getX();
        int centerY = start.getY();
        int startX = centerX - size/2;
        int startY = centerY - size/2;
        int endX = centerX + size/2;
        int endY = centerY + size/2;
        for (int x =startX; x<=endX; x++){
            for (int y =startY; y<=endY; y++){
                    Coordinate coor =new Coordinate(x,y);
                    try{
	                    if(!getGrid().getGridElement(coor).isObstacle()){
	                        coordinates.add(coor);
	                    }
                    }
                    catch(NoSuchElementException ignored){}
            }
        }
        coordinates.remove(start);
        return coordinates;
	}
    
    /**
	 * Returns a random index inside an arrayList
	 * @param a	the list of which a random index is selected.
	 */
    int getRandomIndex(@SuppressWarnings("rawtypes") ArrayList a){
        return random.nextInt(a.size());
    }
    
    /**
     * Returns the grid.
     */
    Grid getGrid(){
    	return this.grid;
    }
    
    ArrayList<Player> getPlayers(){
    	return new ArrayList<>(players);
    }
    
    protected ArrayList<Coordinate> getPlayerCoordinates(){
    	ArrayList<Coordinate> coordinates = new ArrayList<>();
    	for(Player player: players){
    		coordinates.add(getGrid().getCoordinate(player.getStartPosition()));
    	}
		return coordinates;
    }
}
