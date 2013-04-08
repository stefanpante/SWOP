/**
 * 
 */
package event.action;

import game.Game;
import item.launchable.LaunchableItem;

/**
 * @author jonas
 *
 */
public class ThrowLaunchableEvent extends ActionEvent {

	private LaunchableItem launchableItem;
	
	/**
	 * @param game
	 * @param args
	 */
	public ThrowLaunchableEvent(Game game, LaunchableItem launchableItem) {
		super(game);
		this.launchableItem = launchableItem;
	}
	
	private LaunchableItem getLaunchableItem(){
		return this.launchableItem;
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
