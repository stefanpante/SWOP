package grid.itemplacer;

import game.Player;
import grid.Grid;
import grid.GridConstraint;
import item.Teleport;
import org.jetbrains.annotations.NotNull;
import square.GridElement;
import square.Square;
import util.Coordinate;

import java.util.ArrayList;

public class TeleportPlacer extends ItemPlacer {


	/**
	 * Percentage of squares with a teleport.
	 */
	private static final float PERCENTAGE_TELEPORTS = 0.03f;

	public TeleportPlacer(Grid grid, ArrayList<Player> players) {
		super(grid, players);
		this.setItemConstraint(new GridConstraint(TeleportPlacer.PERCENTAGE_TELEPORTS, getPlayerCoordinates()));
	}

	@Override
	public void placeItems() {
		ArrayList<Coordinate> coordinates = getLocations();
		ArrayList<Teleport> teleports = new ArrayList<>();
		ArrayList<Square> destinations = new ArrayList<>();
		for(Coordinate coor: coordinates){
			Teleport teleport = new Teleport();
			teleports.add(teleport);
			GridElement square = getGrid().getGridElement(coor);
			if(square.isSameType(new Square())){
				Square s = (Square) square;
				destinations.add(s);
				placeItem(s, teleport);
			}
		}

		this.linkTeleports(teleports, destinations);
	}

	/**
	 * Links a list of teleports according to the given boolean value.
	 *
	 * @param 	teleports
	 * 			The list of teleports to link.
     */
	private void linkTeleports(@NotNull ArrayList<Teleport> teleports, @NotNull ArrayList<Square> destinations) {
		for(Teleport teleport : teleports){
			Square candidateDestination = destinations.get(getRandomIndex(destinations));
			while(candidateDestination.hasItem(teleport)){
				candidateDestination = destinations.get(getRandomIndex(destinations));
			}
			teleport.setDestination(candidateDestination);
		}
	}
}
