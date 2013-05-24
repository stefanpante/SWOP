package game.mode;


import game.Game;
import grid.AbstractGridBuilder;
import grid.FileGridBuilder;
import grid.RandomGridBuilder;
import org.jetbrains.annotations.NotNull;

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
    @NotNull
    final AbstractGridBuilder gridBuilder;
	
	
	GameMode(int hSize, int vSize) {
		this.gridBuilder = new RandomGridBuilder(hSize, vSize);
	}
	
	GameMode(String filepath) {
		this.gridBuilder =  new FileGridBuilder(filepath);
	}
	
	public void setGame(Game game){
		this.game = game;
	}
	Game getGame(){
		return this.game;
	}
	
	public abstract void build();
	public abstract boolean checkWin();
	public abstract boolean checkLoss();
	public abstract boolean isValidNumberOfPlayers(int numOfPlayers);

	public abstract int getMinimumAmountOfPlayers();
	public abstract int getMaximumAmountOfPlayers();

}
