/**
 * 
 */
package event.action;

import square.Direction;
import square.Square;
import game.Game;
import grid.TrajectoryMediator;
import item.launchable.LaunchableItem;

/**
 * @author jonas
 *
 */
public class ThrowLaunchableEvent extends ActionEvent {

	private LaunchableItem launchableItem;
	private Direction direction;
	
	/**
	 * @param game
	 * @param args
	 */
	public ThrowLaunchableEvent(Game game, LaunchableItem launchableItem, Direction direction) {
		super(game);
		this.launchableItem = launchableItem;
		this.direction = direction;
	}
	
	private LaunchableItem getLaunchableItem(){
		return this.launchableItem;
	}
	
	private Direction getDirection(){
		return this.direction;
	}

	/* (non-Javadoc)
	 * @see event.AbstractGameEvent#beforeGameEvent()
	 */
	@Override
	protected void beforeGameEvent() {
		
	}

	/* (non-Javadoc)
	 * @see event.AbstractGameEvent#duringGameEvent()
	 */
	@Override
	protected void duringGameEvent() {
		// get the start position of the launchable item
		Square currentPosition = getGame().getCurrentPlayer().getPosition();
		// Construct a new trajectory mediator.
		TrajectoryMediator trajectoryMediator = new TrajectoryMediator(getGame().getGrid());
		// determine the end position of the launchable item
		Square endSquare = trajectoryMediator.getEndSquare(currentPosition, getDirection(), getLaunchableItem().getRange());
		// remove the launchable from the players inventory
		getGame().getCurrentPlayer().getInventory().take(launchableItem);
	}

	/* (non-Javadoc)
	 * @see event.AbstractGameEvent#afterGameEvent()
	 */
	@Override
	protected void afterGameEvent() {
		// TODO Auto-generated method stub
		
	}

}
