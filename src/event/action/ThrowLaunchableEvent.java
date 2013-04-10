/**
 * 
 */
package event.action;

import event.effect.LoseTurnEffect;
import player.Player;
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
	
	@SuppressWarnings("static-access")
	@Override
	protected void beforeGameEvent() {
		// Check all parameters.
		if(!getGame().getCurrentPlayer().getInventory().hasItem(getLaunchableItem())){
			throw new IllegalStateException("Player can't throw a Launchable that isn't in his inventory!");
		}
		if(getLaunchableItem().isValidTravelDirection(getDirection())){
			throw new IllegalStateException("Player can't throw a Launchable in the direction: " + getDirection());
		}
	}
	
	@Override
	protected void duringGameEvent() {
		// get the start position of the launchable item
		Square currentPosition = getGame().getCurrentPlayer().getPosition();
		// Construct a new trajectory mediator.
		TrajectoryMediator trajectoryMediator = new TrajectoryMediator(getGame().getGrid());
		// determine the end position of the launchable item
		Square endSquare = trajectoryMediator.getEndSquare(currentPosition, getDirection(), getLaunchableItem().getRange());
		
		// remove the launchable from the players inventory
		getGame().getCurrentPlayer().useItem(getLaunchableItem());
		// add the launchable to the square's inventory.
		endSquare.getInventory().addItem(launchableItem);		
		
		// Checks for a hit on another player.
		for(Player player: getGame().getOtherPlayers()){
			if(player.getPosition() == endSquare){
				//FIXME: Check if the values are correct.
				LoseTurnEffect lte = new LoseTurnEffect(getGame(),player,1,true);
				lte.run();
				break;
			}
		}
	}

	/* (non-Javadoc)s
	 * @see event.AbstractGameEvent#afterGameEvent()
	 */
	@Override
	protected void afterGameEvent() {
		// TODO Auto-generated method stub
		
	}

}
