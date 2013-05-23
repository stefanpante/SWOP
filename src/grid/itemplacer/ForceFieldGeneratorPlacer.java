package grid.itemplacer;

import game.Player;
import grid.Grid;
import grid.GridConstraint;
import item.ForceFieldGenerator;

import java.util.ArrayList;

import util.Coordinate;

/**
 * Places forcefield generators on the grid.
 * @author Dieter Castel, Jonas Devlieghere and Stefan Pante
 *
 */
public class ForceFieldGeneratorPlacer extends ItemPlacer {

	/**
	 * Percentage of squares with a ForceFieldGenerator
	 */
	public static float PERCENTAGE_FORCEFIELDGENERATORS = 0.07f;

	/**
	 * Constructs a new ForceFieldGeneratorPlacer
	 * @param grid
	 */
	public ForceFieldGeneratorPlacer(Grid grid, ArrayList<Player> players) {
		super(grid, players);
		this.setItemConstraint(new GridConstraint(ForceFieldGeneratorPlacer.PERCENTAGE_FORCEFIELDGENERATORS, getPlayerCoordinates()));
	}
	
	public void placeItems(){
		ArrayList<Coordinate> coordinates = getLocations();
		for(Coordinate coor: coordinates){
			placeItem(getGrid().getGridElement(coor), new ForceFieldGenerator());
		}
	}

}
