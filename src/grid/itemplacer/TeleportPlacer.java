package grid.itemplacer;

import game.Player;
import grid.Grid;
import grid.GridConstraint;
import item.Teleport;
import square.GridElement;
import square.Square;
import util.Coordinate;

import java.util.ArrayList;

public class TeleportPlacer extends ItemPlacer {


	/**
	 * Percentage of squares with a teleport.
	 */
	private static float PERCENTAGE_TELEPORTS = 0.03f;

	public TeleportPlacer(Grid grid, ArrayList<Player> players) {
		super(grid, players);
		this.setItemConstraint(new GridConstraint(TeleportPlacer.PERCENTAGE_TELEPORTS, getPlayerCoordinates()));
	}

	@Override
	public void placeItems() {
		ArrayList<Coordinate> coordinates = getLocations();
		ArrayList<Teleport> teleports = new ArrayList<Teleport>();
		ArrayList<Square> destinations = new ArrayList<Square>();
		for(Coordinate coor: coordinates){
			Teleport teleport = new Teleport();
			teleports.add(teleport);
			GridElement square = getGrid().getGridElement(coor);
			if(square.isSameType(new Square())){
				Square s = (Square) square;
				destinations.add(s);
				placeItem(s, teleport);
				System.out.println("teleport placed");
			}
		}

		this.linkTeleports(teleports, destinations);
	}

	/**
	 * Links a list of teleports according to the given boolean value.
	 *
	 * @param 	teleports
	 * 			The list of teleports to link.
	 * @param 	linkRandomly
	 * 			Boolean that hould be true if the linking should happen randomly.
	 * 			Otherwise each teleport will be linked to its next neighbor in the list.
	 */
	private void linkTeleports(ArrayList<Teleport> teleports, ArrayList<Square> destinations) {
		for(Teleport teleport : teleports){
			Square candidateDestination = destinations.get(getRandomIndex(destinations));
			while(candidateDestination.hasItem(teleport)){
				candidateDestination = destinations.get(getRandomIndex(destinations));
			}
			teleport.setDestination(candidateDestination);
		}
	}
}
