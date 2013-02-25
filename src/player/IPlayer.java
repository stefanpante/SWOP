package player;

import grid.core.Square;



/**
 * This interface shows what actions and methods you can call
 * on the player object such as moving, picking up items etc.
 * 
 * @author vincentreniers
 */
public interface IPlayer extends IActions{

	public Square getPosition();
	
}
