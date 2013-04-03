/**
 * 
 */
package event;

import effect.EffectValue;
import game.Game;

/**
 * @author jonas
 *
 */
public abstract class ActionEvent extends GameEvent {
	
	
	public ActionEvent(Game game) {
		super(game);
	}

	@Override
	protected void beforeGameEvent() {
		super.beforeGameEvent();
		if(getGame().getCurrentPlayer().getRemainingActions() <= 0)
			throw new IllegalStateException("The current player has no remaining action left.");
	}

	@Override
	protected void duringGameEvent(){
		super.duringGameEvent();
	}
	
	@Override
	protected void afterGameEvent() {
		super.afterGameEvent();
		if(getGame().getCurrentPlayer().getRemainingActions() <= 0){
			if(!getGame().getCurrentPlayer().hasMoved()){
				getGame().end();
				throw new IllegalStateException("The current player hasn't moved in this turn " +
						"and has no actions left and therefore lost the game");
			}else{
				getGame().getCurrentPlayer().endTurn();
				getGame().switchToNextPlayer();
			}
		}
	}
	


}
