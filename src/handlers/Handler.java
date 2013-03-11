/**
 * 
 */
package handlers;

import game.Game;

import java.util.Observable;

/**
 * Handler  class.
 */
public abstract class Handler extends Observable{

	private Game game;
	
	public Handler(Game game) {
		this.game = game;
	}
	
	/**
	 * Returns the reference to the game.
	 * 
	 * @return
	 */
	protected Game getGame() {
		return this.game;
	}
	
	/**
	 * Sets the game. This normally done in constructor.
	 * 
	 * @param game
	 */
	protected void setGame(Game game) {
		this.game = game;
	}
}
