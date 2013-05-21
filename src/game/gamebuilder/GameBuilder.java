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
public abstract class GameBuilder {

	/**
	 * The game which is constructed
	 */
	private Game game;

	/**
	 * The builder used to construct the grid.
	 */
	private AbstractGridBuilder gridBuilder;


	/**
	 * The number of players to construct
	 */
	private int numOfPlayers;

	/**
	 * Constructs a new game based on the horizontal size of the grid,
	 * the vertical size and the number of players.
	 * @param hSize		the horizontal size of the grid.
	 * @param vSize		the vertical size of the grid.
	 * @param numOfPlayers	the number of player in the game.
	 */
	public GameBuilder(int hSize, int vSize, int numOfPlayers, Game game) {
		this.game = game;
		this.gridBuilder = new RandomGridBuilder(hSize, vSize);
		this.numOfPlayers = numOfPlayers;
	}

	/**
	 * 
	 * @param filename		the file on which the grid will be based.
	 * @param numOfPlayers	the number of players in the game.
	 * @throws IOException	throws an IOException when there is something wrong with the file.
	 */
	public GameBuilder(String filename, int numOfPlayers) throws IOException{
		this.gridBuilder = new FileGridBuilder(filename);
		this.numOfPlayers = numOfPlayers;
		
	}

	/**
	 * Constructs the grid for the game.
	 */
	protected void constructGrid(){
		Grid grid  = gridBuilder.getGrid();
		this.game.setGrid(grid);
	}
	
	/**
	 * Constructs the players for the game.
	 */
	protected void constructPlayers(){
		if(!isValidNumberOfPlayers(this.numOfPlayers)){
			throw new IllegalStateException("The amount of players is not valid for the specified game mode.");
		}
		ArrayList<Square> startPositions = this.game.getGrid().getStartPositions();

		if(startPositions.size() < numOfPlayers){
			throw new IllegalStateException("The number of players cannot be more than the number of startpositions");
		}
		ArrayList<Player> players = new ArrayList<Player>();

		for(int i = 0; i < this.numOfPlayers; i++){
			players.add(new Player(startPositions.get(0), i + 1));
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
	public void placeFlags(Grid grid, ArrayList<Player> players){
		FlagPlacer FPlacer = new FlagPlacer(game.getGrid(), game.getPlayers());
		FPlacer.placeItems();
	}
	
	

	/**
	 * Checks whether the given number of players is valid.
	 * @param numOfPlayers	the number of players to be checked.
	 * @return	true if the number of players is vaid, false otherwise.
	 */
	public abstract boolean isValidNumberOfPlayers(int numOfPlayers);

	/**
	 * Returns the game.
	 * @return
	 */
	public Game getGame(){
		return this.game;
	}

}
