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
	
	protected EffectValue effectValue;
	
	public ActionEvent(Game game) {
		super(game);
	}
	
	@Override
	public void run() {
		// Run code before Game Event
		super.beforeGameEvent();
		beforeGameEvent();
		// Run code during Game Event
		super.duringGameEvent();
		duringGameEvent();
		// Run code after Game Event
		afterGameEvent();
		super.afterGameEvent();
	}
	
	/**
	 * Add the given effect to this Action Event.
	 * 
	 * @param 	effectValue
	 * 			The effect value to be added to this action event
	 */
	protected void addEffect(EffectValue effectValue){
		this.effectValue.addEffect(effectValue);
	}

	@Override
	protected void beforeGameEvent() {
		if(getGame().getCurrentPlayer().getRemainingActions() <= 0)
			throw new IllegalStateException("The current player has no remaining action left.");
	}
	
	@Override
	protected void afterGameEvent() {
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
