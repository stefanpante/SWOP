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
public abstract class ActionEvent extends AbstractGameEvent {
	
	protected EffectValue effectValue;
	
	public ActionEvent(Game game) {
		super(game);
	}
	
	@Override
	public void run() {
		beforeActionEvent();
		beforeGameEvent();
		duringGameEvent();
		afterGameEvent();
		afterActionEvent();
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

	protected void beforeActionEvent() {
		if(!getGame().isActive())
			throw new IllegalStateException("The game is over.");
		if(getGame().getCurrentPlayer().getRemainingActions() <= 0)
			throw new IllegalStateException("The current player has no remaining action left.");
	}
	
	protected void afterActionEvent() {
		
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
