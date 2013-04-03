/**
 * 
 */
package event;

import game.Game;

/**
 * @author jonas
 *
 */
public abstract class ActionEvent extends AbstractGameEvent {

	public ActionEvent(Game game, Object... args) {
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
