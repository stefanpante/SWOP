package game.gamebuilder;

import game.Game;
import game.Player;
import grid.AbstractGridBuilder;
import grid.FileGridBuilder;
import grid.Grid;
import grid.RandomGridBuilder;

import itemplacer.ChargedIdentityDiscPlacer;
import itemplacer.FlagPlacer;
import itemplacer.ForceFieldGeneratorPlacer;
import itemplacer.IdentityDiscPlacer;
import itemplacer.LightGrenadePlacer;
import itemplacer.TeleportPlacer;

import java.io.IOException;
import java.util.ArrayList;

import square.Square;

/**
 * Class used to create the game. Used to construct all the components of
 * a game: Grid, items, players. Assures a consistent and coherent game
 * construction.
 * 
 * 
 * @author Dieter Castel, Jonas Devlieghere and Stefan Pante
 *
 */
public class GameBuilder {

	/**
	 * The game which is constructed
	 */
	private Game game;

	/**
	 * The number of players to construct
	 */
	private int numOfPlayers;

	public GameBuilder(Game game){
		this.game = game;
	}
	
	/**
	 * Constructs a new game based on the horizontal size of the grid,
	 * the vertical size and the number of players.
	 * @param hSize		the horizontal size of the grid.
	 * @param vSize		the vertical size of the grid.
	 * @param numOfPlayers	the number of player in the game.
	 */
	public GameBuilder(Game game, int numOfPlayers) {
		this.game = game;
		this.numOfPlayers = numOfPlayers;
	}

	/**
	 * Constructs the players for the game.
	 */
	public void constructPlayers(){
		ArrayList<Square> startPositions = this.game.getGrid().getStartPositions();

		if(startPositions.size() < numOfPlayers){
			throw new IllegalStateException("The number of players cannot be more than the number of startpositions");
		}
		ArrayList<Player> players = new ArrayList<Player>();

		for(int i = 0; i < this.numOfPlayers; i++){
			players.add(new Player(startPositions.get(i), i + 1));
		}
		this.game.setPlayers(players);
	}
	
	/**
	 * Places the lightGrenades on the grid.
	 * @param grid
	 * @param players
	 */
	public void placeLightGrenades(){
		LightGrenadePlacer LGPlacer = new LightGrenadePlacer(game.getGrid(), game.getPlayers());
		LGPlacer.placeItems();
	}
	
	/**
	 * Places the ForcefieldGenerators on the grid.
	 * @param grid
	 * @param players
	 */
	public void placeForceFieldGenerators(){
		ForceFieldGeneratorPlacer FFGPlacer = new ForceFieldGeneratorPlacer(game.getGrid(), game.getPlayers());
		FFGPlacer.placeItems();
	}
	
	/**
	 * Places the identityDiscs on the grid.
	 * @param grid
	 * @param players
	 */
	public void placeIdentityDiscs(){
		IdentityDiscPlacer IDPlacer = new IdentityDiscPlacer(game.getGrid(), game.getPlayers());
		IDPlacer.placeItems();
	}
	
	/**
	 * Places the chargedIdentityDiscs on the grid.
	 * @param grid
	 * @param players
	 */
	public void placeChargedIdentityDisc(){
		ChargedIdentityDiscPlacer CIDPlacer = new ChargedIdentityDiscPlacer(game.getGrid(), game.getPlayers());
		CIDPlacer.placeItems();
	}
	
	/**
	 * Places the teleports on the grid.
	 * @param grid
	 * @param players
	 */
	public void placeTeleports(){
		TeleportPlacer TPPlacer = new TeleportPlacer(game.getGrid(), game.getPlayers());
		TPPlacer.placeItems();
	}
	
	/**
	 * Places the flags on the grid.
	 * @param grid
	 * @param players
	 */
	public void placeFlags(){
		FlagPlacer FPlacer = new FlagPlacer(game.getGrid(), game.getPlayers());
		FPlacer.placeItems();
	}

	/**
	 * Returns the game.
	 * @return
	 */
	public Game getGame(){
		return this.game;
	}

}
