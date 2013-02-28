package handlers;

import square.Square;
import game.Game;

/**
 * Controller/Handler which controls the player move use case
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class MoveHandler {


	private Game game;
	public MoveHandler(Game game) {
		this.game = game;
	}
	
	public void move(Direction direction){
		Square newPosition = game.getCurrentPlayer().getPosition().getNeighor(direction);
		game.getCurrentPlayer().move(newPosition);
		game.getCurrentPlayer().incrementActions();
	}

}
