package grid;

import game.Player;

import item.ChargedIdentityDisc;

import java.util.ArrayList;

import util.Coordinate;

/**
 * Places the chargedIdentityDisc on the specified grid.
 * The chargedIdentityDisc is placed at an equal distance (+- 2 squares)
 * from each player
 * 
 * @author Dieter Castel, Jonas Devlieghere and Stefan Pante
 */
public class ChargedIdentityDiscPlacer extends ItemPlacer {

	/**
	 * The players in the game; Needed for the location of the chargedIdentityDisc
	 */
	private ArrayList<Player> players;
	
	public ChargedIdentityDiscPlacer(Grid grid, ArrayList<Player> players) {
		super(grid);
		this.players = players;
	}
	
	@Override
	public void placeItems(){
		Coordinate coor = getLocation();
		placeItem(getGrid().getSquare(coor), new ChargedIdentityDisc());
	}
	
	public Coordinate getLocation(){
		return null;
	}

}
