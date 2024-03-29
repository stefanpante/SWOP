package grid.itemplacer;

import game.Player;
import grid.Grid;
import grid.GridConstraint;
import item.LightGrenade;
import org.jetbrains.annotations.NotNull;
import square.Square;
import util.Coordinate;

import java.util.ArrayList;

/**
 * Places all the light grenades on the grid.
 * 
 *@author Dieter Castel, Jonas Devlieghere and Stefan Pante
 */
public class LightGrenadePlacer extends ItemPlacer{

	private static final int INCLUDED_RADIUS = 5;
	/**
	 * Percentage of square covered by grenades
	 */
	private static final float PERCENTAGE_GRENADES = 0.05f;
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
	
	@NotNull
    ArrayList<ArrayList<Coordinate>> getIncluded(){
		ArrayList<ArrayList<Coordinate>> included = new ArrayList<>();
		for(Coordinate coordinate: getPlayerCoordinates())
			included.add(getSquaredLocation(coordinate, INCLUDED_RADIUS));
		
		return included;
	}
}
