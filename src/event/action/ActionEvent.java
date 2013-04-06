/**
 * 
 */
package event.action;

import java.util.Observer;

import controller.TurnHandler;
import event.AbstractGameEvent;
import game.Game;

/**
 * @author jonas
 *
 */
public abstract class ActionEvent extends AbstractGameEvent {
		
	public ActionEvent(Game game) {
		super(game);
		if(OBSERVER == null)
			throw new IllegalStateException("ActionEvent cannot be created without it's observer set.");
		addObserver(OBSERVER);
	}
	
	@Override
	public void run() {
		beforeActionEvent();
		beforeGameEvent();
		duringGameEvent();
		afterGameEvent();
		afterActionEvent();
		// Observer Pattern
		setChanged();
		notifyObservers();
	}
	
	protected void beforeActionEvent() {
		if(!getGame().isActive())
			throw new IllegalStateException("The game is over.");
		if(getGame().getCurrentPlayer().getRemainingActions() <= 0)
			throw new IllegalStateException("The current player has no remaining action left.");
	}
	
	protected void afterActionEvent() {
		//TODO : Update player's effectValue 
	}
	


}