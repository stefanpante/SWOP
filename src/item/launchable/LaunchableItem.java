package item.launchable;

import item.Item;

/**
 * This is a special type of items which can be launched by the player.
 * For example an IdentityDisc.
 * 
 * @author vincentreniers
 */
public abstract class LaunchableItem extends Item {
	
	/**
	 * The maximum range of the item when launched.
	 * 
	 * @return
	 */
	abstract public int getRange();
	
}
