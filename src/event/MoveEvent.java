/**
 * 
 */
package event;

import effect.EffectValue;

import item.LightGrenade;
import game.Game;
import square.Direction;
import square.Square;

/**
 * @author jonas
 *
 */
public class MoveEvent extends ActionEvent {

	private Direction direction;

	public MoveEvent(Game game, Direction dir) {
		super(game);
		this.direction = dir;
	}
	
	private Direction getDirection(){
		return this.direction;
	}
	
	@Override
	protected void beforeGameEvent() {
		super.beforeGameEvent();
		/* Check wether it's possible to move in the given direction */
		if(!getGame().getGrid().canMoveTo(getGame().getCurrentPlayer().getPosition(), getDirection())){
			throw new IllegalStateException("Cannot move to given direction.");
		}
		/* Activate Light Grenades on square leaving square */
		Square currentPosition = getGame().getCurrentPlayer().getPosition();
		if(currentPosition.getInventory().hasLightGrenade()){
			LightGrenade lg = currentPosition.getInventory().getLightGrenade();
			try{
				currentPosition.getInventory().activate(lg);
			} catch (Exception exc) {
				// Catched exception, if try to activate wornout item. -> ignore
			}
		}

	}

	@Override
	protected void duringGameEvent() {
		Square currentPosition = getGame().getCurrentPlayer().getPosition();
		Square newPosition = getGame().getGrid().getNeighbor(currentPosition, getDirection()); 
		getGame().getCurrentPlayer().move(newPosition);	
	}

	@Override
	protected void afterGameEvent() {
		Square newPosition = getGame().getCurrentPlayer().getPosition();
		if(newPosition.hasEffect()){
			EffectValue penaltyValue = newPosition.getEffectAfterAction();
			getGame().getCurrentPlayer().endTurn(penaltyValue);
			getGame().switchToNextPlayer();
		}
		getGame().getCurrentPlayer().getPosition().getInventory().wearOut();
		super.afterGameEvent();
	}

}
