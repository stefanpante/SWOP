package grid;

import item.LightGrenade;

import java.util.ArrayList;
import util.Coordinate;

/**
 * Places all the light grenades on the grid.
 * 
 *@author Dieter Castel, Jonas Devlieghere and Stefan Pante
 */
public class LightGrenadePlacer extends ItemPlacer{

	public static int INCLUDED_RADIUS = 5;
	/**
	 * Constructs a new LightGrenadePlacer, places the items on the given grid.
	 * @param grid		The grid on which the items should be placed.
	 */
	public LightGrenadePlacer(Grid grid){
		super(grid);
		ArrayList<Coordinate> excluded = getGrid().getCoordinates(getGrid().getStartPositions());
		this.setItemConstraint(new GridConstraint(Grid.PERCENTAGE_GRENADES, excluded, getIncluded()));
		
	}
	
	/**
	 * Places all the light grenades on the given grid.
	 */
	public void placeItems(){
		ArrayList<Coordinate> coordinates = getLocations();
		for(Coordinate coor: coordinates){
			placeItem(getGrid().getSquare(coor), new LightGrenade());
		}
	}
	
	public ArrayList<ArrayList<Coordinate>> getIncluded(){
		ArrayList<ArrayList<Coordinate>> included = new ArrayList<ArrayList<Coordinate>>();
		for(Coordinate coordinate: getGrid().getCoordinates(getGrid().getStartPositions()))
			included.add(getSquaredLocation(coordinate, INCLUDED_RADIUS));
		
		return included;
	}
}
