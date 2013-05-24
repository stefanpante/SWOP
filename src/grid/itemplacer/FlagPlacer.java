package grid.itemplacer;

import game.Player;
import grid.Grid;
import item.Flag;
import item.Item;
import org.jetbrains.annotations.Nullable;
import square.Square;

import java.util.ArrayList;

/**
 * Class to place flags on the grid.
 * Needs to place a flag at each starting position of player.
 * @author Stefan
 *
 */
public class FlagPlacer extends ItemPlacer {
	

	/**
	 * Construct a new flag placer
	 * @param grid		the grid on which the flags should be placed
	 * @param players	the players, used for there starting positions.
	 */
	public FlagPlacer(Grid grid, ArrayList<Player> players) {
		super(grid, players);
	}

	@Override
	public void placeItems() {
		for(Player player: getPlayers()){
			placeItem(player.getStartPosition(), new Flag(player));
			System.out.println(player.getStartPosition().hasType(new Flag()));
		}
	}
	
	@Override
    void placeItem(@Nullable Square square, Item item) throws IllegalArgumentException {
		if(square == null)
			return;
			//			throw new IllegalArgumentException("Cannot place an object on a square that is obstructed.");
		square.addItem(item);
	}

}
