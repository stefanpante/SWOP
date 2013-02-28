package handlers;

import game.Game;

/**
 * The Controller/Handler to end the turn of a player
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class EndTurnHandler {

	private Game game;
	private static final EndTurnHandler instance = new EndTurnHandler();
	
	private EndTurnHandler() {
		this.game = Game.getInstance();
	}
	
	public EndTurnHandler getInstance(){
		return instance;
	}
	public void endTurn(){
		game.endTurn();
	}
	//TODO checkAllConditions for end turn, turns inactive items active

}
