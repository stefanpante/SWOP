package grid.itemplacer;

import game.Player;
import grid.Grid;
import grid.GridConstraint;
import item.LightGrenade;

import java.util.ArrayList;

import square.Square;
import util.Coordinate;

/**
 * Places all the light grenades on the grid.
 * 
 *@author Dieter Castel, Jonas Devlieghere and Stefan Pante
 */
public class LightGrenadePlacer extends ItemPlacer{

	public static int INCLUDED_RADIUS = 5;
	/**
	 * Percentage of square covered by grenades
	 */
	public static float PERCENTAGE_GRENADES = 0.05f;
	/**
	 * Constructs a new LightGrenadePlacer, places the items on the given grid.
	 * @param grid		The grid on which the items should be placed.
	 */
	public LightGrenadePlacer(Grid grid, ArrayList<Player> players){
		super(grid, players);
		this.setItemConstraint(new GridConstraint(LightGrenadePlacer.PERCENTAGE_GRENADES, getPlayerCoordinates(), getIncluded()));
		
	}
	
	/**
	 * Places all the light grenades on the given grid.
	 */
	public void placeItems(){
		ArrayList<Coordinate> coordinates = getLocations();
		for(Coordinate coor: coordinates){
			if(getGrid().getGridElement(coor).isSameType(new Square()))
				placeItem((Square) getGrid().getGridElement(coor), new LightGrenade());
		}
	}
	
	public ArrayList<ArrayList<Coordinate>> getIncluded(){
		ArrayList<ArrayList<Coordinate>> included = new ArrayList<ArrayList<Coordinate>>();
		for(Coordinate coordinate: getPlayerCoordinates())
			included.add(getSquaredLocation(coordinate, INCLUDED_RADIUS));
		
		return included;
	}
}
