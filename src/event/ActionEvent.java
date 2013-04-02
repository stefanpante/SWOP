/**
 * 
 */
package event;

import game.Game;

/**
 * @author jonas
 *
 */
public abstract class ActionEvent extends GameEvent {

	public ActionEvent(Game game, Object[] args) {
		super(game, args);
	}

	@Override
	protected void beforeGameEvent() {
		if(!getGame().isActive())
			throw new IllegalStateException("The game is over");
		if(getGame().getCurrentPlayer().getRemainingActions() <= 0)
			throw new IllegalStateException("The current player has no remaining action left.");
	}

	@Override
	protected void duringGameEvent(){
		
	}
	
	@Override
	protected void afterGameEvent() {
		
	}
	


}
