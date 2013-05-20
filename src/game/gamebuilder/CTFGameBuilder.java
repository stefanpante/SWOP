package game.gamebuilder;

import java.io.IOException;
import game.CTFGame;
import itemplacer.ChargedIdentityDiscPlacer;
import itemplacer.FlagPlacer;
import itemplacer.ForceFieldGeneratorPlacer;
import itemplacer.IdentityDiscPlacer;
import itemplacer.TeleportPlacer;

/**
 * Builds a CTFGame.
 * 
 * @author Dieter Castel, Jonas Devlieghere and Stefan Pante
 *
 */
public class CTFGameBuilder extends GameBuilder {

	

	/**
	 * Constructs a new game based on the horizontal size of the grid,
	 * the vertical size and the number of players.
	 * @param hSize		the horizontal size of the grid.
	 * @param vSize		the vertical size of the grid.
	 * @param numOfPlayers	the number of player in the game.
	 */
	public CTFGameBuilder(int hSize, int vSize, int numOfPlayers) {
		super(hSize, vSize, numOfPlayers);
		this.game = new CTFGame();
		this.build();
		
	}
	
	/**
	 * 
	 * @param filename		the file on which the grid will be based.
	 * @param numOfPlayers	the number of players in the game.
	 * @throws IOException	throws an IOException when there is something wrong with the file.
	 */
	public CTFGameBuilder(String filename, int numOfPlayers) throws IOException{
		super(filename, numOfPlayers);
		this.game = new CTFGame();
		this.build();
	}

	@Override
	public void placeItems() {
		ChargedIdentityDiscPlacer CIDPlacer = new ChargedIdentityDiscPlacer(grid, players);
		CIDPlacer.placeItems();
		
		ForceFieldGeneratorPlacer FFGPlacer = new ForceFieldGeneratorPlacer(grid, players);
		FFGPlacer.placeItems();
		
		IdentityDiscPlacer IDPlacer = new IdentityDiscPlacer(grid, players);
		IDPlacer.placeItems();
		
		TeleportPlacer TPPlacer = new TeleportPlacer(grid, players);
		TPPlacer.placeItems();
		
		FlagPlacer FPlacer = new FlagPlacer(grid,players);
		FPlacer.placeItems();
		
	}

	@Override
	public boolean isValidNumberOfPlayers(int numOfPlayers) {
		return (numOfPlayers <= CTFGame.MAX_PLAYERS && numOfPlayers >= CTFGame.MIN_PLAYERS);
	}

}
