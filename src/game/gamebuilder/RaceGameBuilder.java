package game.gamebuilder;

import game.CTFGame;
import game.RaceGame;

import java.io.IOException;



public class RaceGameBuilder extends GameBuilder {

	/**
	 * Constructs a new game based on the horizontal size of the grid,
	 * the vertical size and the number of players.
	 * @param hSize		the horizontal size of the grid.
	 * @param vSize		the vertical size of the grid.
	 * @param numOfPlayers	the number of player in the game.
	 */
	public RaceGameBuilder(int hSize, int vSize, int numOfPlayers) {
		super(hSize, vSize, numOfPlayers);
		this.game = new RaceGame();
		this.build();
		
	}
	
	/**
	 * 
	 * @param filename		the file on which the grid will be based.
	 * @param numOfPlayers	the number of players in the game.
	 * @throws IOException	throws an IOException when there is something wrong with the file.
	 */
	public RaceGameBuilder(String filename, int numOfPlayers) throws IOException{
		super(filename, numOfPlayers);
		this.game = new RaceGame();
		this.build();
	}


	@Override
	public boolean isValidNumberOfPlayers(int numOfPlayers) {
		return (numOfPlayers <= RaceGame.MAX_PLAYERS && numOfPlayers >= RaceGame.MIN_PLAYERS);
	}

	
}
