package grid;

import item.Teleport;

import java.util.ArrayList;

import square.Square;
import util.Coordinate;

public class TeleportPlacer extends ItemPlacer {


	public TeleportPlacer(Grid grid) {
		super(grid);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void placeItems() {
		ArrayList<Coordinate> coordinates = getRandomLocations();
		ArrayList<Teleport> teleports = new ArrayList<Teleport>();
		ArrayList<Square> destinations = new ArrayList<Square>();
		for(Coordinate coor: coordinates){
			Teleport teleport = new Teleport();
			teleports.add(teleport);
			Square square = getGrid().getSquare(coor);
			destinations.add(square);
			placeItem(square, teleport);
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
			while(candidateDestination.getInventory().hasItem(teleport)){
				candidateDestination = destinations.get(getRandomIndex(destinations));
			}
			teleport.setDestination(candidateDestination);
		}
	}
}
