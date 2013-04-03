/**
 * 
 */
package event;

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

}
