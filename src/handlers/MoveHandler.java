package handlers;

import game.Game;

/**
 * Controller/Handler which controls the player move use case
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class MoveHandler {

	private static final MoveHandler instance = new MoveHandler();
	private Game game;
	public MoveHandler() {
		this.game = Game.getInstance();
	}
	
	public MoveHandler getInstance(){
		return instance;
	}

}
