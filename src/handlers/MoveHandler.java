package handlers;

import square.Direction;
import square.Square;
import game.Game;

/**
 * Controller/Handler which controls the player move use case
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class MoveHandler extends Handler {


	private Game game;
	public MoveHandler(Game game) {
		this.game = game;
	}
	
	// Checks the precondition of the use case Move
	public boolean checkToProceed(){
		if(game.getCurrentPlayer().getRemainingActions() > 0){
			return true;
		}
		
		return false;
	}
	
	public void move(Direction direction){
		Square currentPosition = game.getCurrentPlayer().getPosition();
		Square newPosition = currentPosition.getNeighor(direction);
		if(currentPosition.canMoveTo(newPosition)){
			game.getCurrentPlayer().move(newPosition);
		}
		game.getCurrentPlayer().incrementActions();
	}
	
	public void endAction(){

}
