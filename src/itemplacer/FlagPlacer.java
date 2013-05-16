package itemplacer;

import game.Player;
import grid.Grid;

import item.Flag;

import java.util.ArrayList;

public class FlagPlacer extends ItemPlacer {
	
	private ArrayList<Player> players;

	public FlagPlacer(Grid grid, ArrayList<Player> players) {
		super(grid);
		this.players = players;
	}

	@Override
	public void placeItems() {
		for(Player player: players){
			placeItem(player.getStartPosition(), new Flag(player));
		}
	}

}
