package game.mode;


import game.Game;
import grid.AbstractGridBuilder;
import grid.FileGridBuilder;
import grid.RandomGridBuilder;

import java.io.IOException;

/**
 * Implements all the things which are dependent on the game mode.
 * A game mode can influence the build process, the win and the 
 * lose conditions.
 * 
 * @author Dieter Castel, Jonas Devlieghere and Stefan Pante
 *
 */
public abstract class GameMode {

	/**
	 * The game to which this gameMode belongs
	 */
	private Game game;
	
	/**
	 * The gridbuilder used to construct the grid for the game.
	 */
	protected AbstractGridBuilder gridBuilder;
	
	
	public GameMode(int hSize, int vSize) {
		this.gridBuilder = new RandomGridBuilder(hSize, vSize);
	}
	
	public GameMode(String filepath) throws IOException{
		this.gridBuilder =  new FileGridBuilder(filepath);
	}
	
	public void setGame(Game game){
		this.game = game;
	}
	protected Game getGame(){
		return this.game;
	}
	
	public abstract void build();
	public abstract boolean checkWin();
	public abstract boolean checkLoss();
	public abstract boolean isValidNumberOfPlayers(int numOfPlayers);

	public abstract int getMinimumAmountOfPlayers();
	public abstract int getMaximumAmountOfPlayers();

}
