/**
 * 
 */
package controller;

import event.AbstractGameCommand;
import event.action.EndTurnCommand;
import event.effect.LoseActionEffect;
import game.Game;
import game.Player;

import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import manager.ActionManager;

import square.Square;

/**
 * @author Jonas Devlieghere
 *
 */
public class TurnHandler extends Handler implements Observer {

	private HashMap<Player,Integer> counter;
	
	public TurnHandler(Game game, PropertyChangeListener listener) {
		super(game, listener);
		ActionManager.getInstance().addObserver(this);
		counter = new HashMap<Player,Integer>();
		
		for(Player player : getGame().getPlayers()){
			counter.put(player, 0);
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		getGame().getPowerManager().decreaseAction();
		
		if(hasWon()){
    		firePropertyChange(GameHandler.WIN_PROPERTY, getGame().getCurrentPlayer().toString());
    		getGame().end();
    	} else if(isEndOfTurn()) {
			endTurn(false);
    	}
	}
	
	/**
	 * Check whether this turn is finished by checking the current player
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
	public void endTurn(boolean skip){
		if(!skip && !getGame().getCurrentPlayer().hasMoved()){
			getGame().end();
			throw new IllegalStateException("The current player hasn't moved in this turn " +
					"and has no actions left and therefore lost the game");
		}else{			
	    	getGame().getCurrentPlayer().endTurn();
	    	getGame().switchToNextPlayer();
	    	
	    	getGame().getPowerManager().decreaseTurn();
	    	
			startTurn();
		}
	}
	
	/**
	 * Start a new turn
	 */
	public void startTurn(){
		Player currentPlayer = getGame().getCurrentPlayer();
		
		if(hasLost()){
			getGame().end();
    		firePropertyChange(GameHandler.LOSE_PROPERTY, getGame().getCurrentPlayer().toString());	
		}
		
		increaseCurrentPlayerCount();
		getGame().getPowerManager().powerFailSquares();
		
		if(getGame().getCurrentPlayer().getPosition().getPower().isFailing()) {
			LoseActionEffect lae = new LoseActionEffect(getGame(),1);
			lae.execute();
		}
		
		if(!getGame().getCurrentPlayer().hasRemainingActions())
			endTurn(true);
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
