/**
 * 
 */
package controller;

import event.ActionEvent;
import game.Game;

import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import player.Player;

/**
 * @author Jonas Devlieghere
 *
 */
public class TurnHandler extends Handler implements Observer {

	private HashMap<Player,Integer> counter;
	
	public TurnHandler(Game game, PropertyChangeListener listener) {
		super(game, listener);
		ActionEvent.setObserver(this);
		for(Player player : getGame().getPlayers()){
			counter.put(player, 0);
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(isEndOfTurn())
			endTurn();
	}
	
	/**
	 * Check wether this turn is finished by checking the current player
	 * has any actions left.
	 * 
	 * @return	True if and only if the player has remaining actions
	 */
	public boolean isEndOfTurn(){
		return getGame().getCurrentPlayer().getRemainingActions() <= 0;
	}
	
	/**
	 * End the current turn
	 */
	public void endTurn(){
		if(!getGame().getCurrentPlayer().hasMoved()){
			getGame().end();
			throw new IllegalStateException("The current player hasn't moved in this turn " +
					"and has no actions left and therefore lost the game");
		}else{
			getGame().getCurrentPlayer().endTurn();
			getGame().switchToNextPlayer();
		}
	}
	
	/**
	 * Start a new turn
	 */
	public void startTurn(){
		increaseCurrentPlayerCount();
		//TODO: Reset player count
	}
	
	/**
	 * Increase the turn count of the current player.
	 */
	private void increaseCurrentPlayerCount(){
		Player currentPlayer = getGame().getCurrentPlayer();
		int currentCount = counter.remove(currentPlayer);
		counter.put(currentPlayer, ++currentCount);
	}
	

}
