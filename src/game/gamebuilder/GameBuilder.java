package game.gamebuilder;

import game.Game;
import game.Player;
import grid.AbstractGridBuilder;
import grid.FileGridBuilder;
import grid.Grid;
import grid.RandomGridBuilder;

import itemplacer.ChargedIdentityDiscPlacer;
import itemplacer.ForceFieldGeneratorPlacer;
import itemplacer.IdentityDiscPlacer;
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
	protected Game game;

	/**
	 * The builder used to construct the grid.
	 */
	protected AbstractGridBuilder gridBuilder;


	private int numOfPlayers;

	/**
	 * Constructs a new game based on the horizontal size of the grid,
	 * the vertical size and the number of players.
	 * @param hSize		the horizontal size of the grid.
	 * @param vSize		the vertical size of the grid.
	 * @param numOfPlayers	the number of player in the game.
	 */
	public GameBuilder(int hSize, int vSize, int numOfPlayers) {
		this.gridBuilder = new RandomGridBuilder(hSize, vSize);
		this.numOfPlayers = numOfPlayers;
		this.build();

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
	 * Builds the actual game.
	 */
	protected void build(){
		this.constructGrid();
		this.constructPlayers();
		this.placeItems();
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
	 * Places all the items on the grid.
	 */
	protected void placeItems() {
		Grid grid = game.getGrid();
		ArrayList<Player> players = game.getPlayers();
		
		ChargedIdentityDiscPlacer CIDPlacer = new ChargedIdentityDiscPlacer(grid, players);
		CIDPlacer.placeItems();

		ForceFieldGeneratorPlacer FFGPlacer = new ForceFieldGeneratorPlacer(grid, players);
		FFGPlacer.placeItems();

		IdentityDiscPlacer IDPlacer = new IdentityDiscPlacer(grid, players);
		IDPlacer.placeItems();

		TeleportPlacer TPPlacer = new TeleportPlacer(grid, players);
		TPPlacer.placeItems();
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
