package itemplacer;

import game.Player;
import grid.Grid;
import grid.GridConstraint;
import item.IdentityDisc;
import java.util.ArrayList;

import square.Square;
import util.Coordinate;

/**
 * Places IdentityDiscs on the grid.
 * @author Dieter Castel, Jonas Devlieghere and Stefan Pante
 *
 */
public class IdentityDiscPlacer extends ItemPlacer {

	/**
	 * The radius in which a IdentityDisc should be placed relative to the start position of
	 * the player.
	 */
	public static int INCLUDED_RADIUS = 7;
	
	/**
	 * Construct a new IdentityDiscPlacer.
	 * @param grid
	 */
	public IdentityDiscPlacer(Grid grid, ArrayList<Player> players) {
		super(grid, players);
		setItemConstraint(new GridConstraint(Grid.PERCENTAGE_IDENTITY_DISKS, getPlayerCoordinates(), getIncluded()));
	}
	
	public void placeItems(){
		ArrayList<Coordinate> coordinates = getLocations();
		for(Coordinate coor: coordinates){
			placeItem(getGrid().getSquare(coor), new IdentityDisc());
		}
	}
	
	public ArrayList<ArrayList<Coordinate>> getIncluded(){
		ArrayList<ArrayList<Coordinate>> included = new ArrayList<ArrayList<Coordinate>>();
		for(Coordinate coordinate: getPlayerCoordinates())
			included.add(getSquaredLocation(coordinate, INCLUDED_RADIUS));
		
		return included;
	}

}
