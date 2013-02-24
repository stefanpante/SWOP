package handlers;

import game.Game;

/**
 * The Controller/Handler to end the turn of a player
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class EndTurnHandler {

	private Game game;
	
	public EndTurnHandler(Game game) {
		this.game = game;
	}
	
	public void endTurn(){
		game.endTurn();
	}
	//TODO checkAllConditions for end turn, turns inactive items active

}
