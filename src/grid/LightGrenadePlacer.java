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

	/**
	 * Constructs a new LightGrenadePlacer, places the items on the given grid.
	 * @param grid		The grid on which the items should be placed.
	 */
	public LightGrenadePlacer(Grid grid){
		super(grid);
	}
	
	/**
	 * Places all the light grenades on the given grid.
	 */
	public void placeItems(){
		ArrayList<Coordinate> coordinates = getRandomLocations();
		for(Coordinate coor: coordinates){
			placeItem(getGrid().getSquare(coor), new LightGrenade());
		}
	}
}
