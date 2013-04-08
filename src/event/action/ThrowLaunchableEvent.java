/**
 * 
 */
package event.action;

import square.Direction;
import game.Game;
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
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see event.AbstractGameEvent#duringGameEvent()
	 */
	@Override
	protected void duringGameEvent() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see event.AbstractGameEvent#afterGameEvent()
	 */
	@Override
	protected void afterGameEvent() {
		// TODO Auto-generated method stub
		
	}

}
