/**
 * 
 */
package event;

import penalty.PenaltyValue;
import game.Game;
import square.Direction;
import square.Square;

/**
 * @author jonas
 *
 */
public class MoveEvent extends ActionEvent {


	public MoveEvent(Game game, Object... args) {
		super(game, args);
	}
	
	private Direction getDirection(){
		return (Direction) getArgument(0);
	}
	
	@Override
	protected void beforeGameEvent() {
		super.beforeGameEvent();
		if(!getGame().getGrid().canMoveTo(getGame().getCurrentPlayer().getPosition(), getDirection()))
			throw new IllegalStateException("Cannot move to given direction.");
	}


	@Override
	protected void duringGameEvent() {
		Square currentPosition = getGame().getCurrentPlayer().getPosition();
		Square newPosition = getGame().getGrid().getNeighbor(currentPosition, getDirection()); 
		getGame().getCurrentPlayer().move(newPosition);	
	}

	@Override
	protected void afterGameEvent() {
		super.afterGameEvent();
		Square newPosition = getGame().getCurrentPlayer().getPosition();
		if(newPosition.hasPenalty()){
			PenaltyValue penaltyValue = newPosition.getPenalty();
			getGame().getCurrentPlayer().endTurn(penaltyValue);
			getGame().switchToNextPlayer();
		}
		getGame().getCurrentPlayer().getPosition().getInventory().wearOut();
	}

}
