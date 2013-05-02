/**
 * 
 */
package command.action;

import item.launchable.IdentityDisc;
import square.Direction;
import square.Square;
import game.Game;
import game.Player;
import grid.TrajectoryMediator;

/**
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class ThrowLaunchableCommand extends ActionCommand {

	private IdentityDisc disc;
	private Direction direction;
	
	/**
	 * @param game
	 */
	public ThrowLaunchableCommand(Game game, IdentityDisc disc, Direction direction) {
		super(game);
		
		this.disc = disc;
		this.direction = direction;
	}
	
	private IdentityDisc getDisc(){
		return this.disc;
	}
	
	private Direction getDirection(){
		return this.direction;
	}
	
	@SuppressWarnings("static-access")
	@Override
	protected void beforeGameCommand() {
		// Check all parameters.
		if(!getGame().getCurrentPlayer().getInventory().hasItem(getDisc())){
			throw new IllegalStateException("Player can't throw a Launchable that isn't in his inventory!");
		}
		if(!getDisc().isValidTravelDirection(getDirection())){
			throw new IllegalStateException("Player can't throw a Launchable in the direction: " + getDirection());
		}
	}
	
	@Override
	protected void duringGameCommand() throws Exception {
		// get the start position of the launchable item
		Square currentPosition = getGame().getCurrentPlayer().getPosition();
		// Construct a new trajectory mediator.
		TrajectoryMediator trajectoryMediator = new TrajectoryMediator(getGame().getGrid());
		// determine the end position of the launchable item
		Square endSquare = trajectoryMediator.getEndSquare(currentPosition, getDirection(), getDisc().getRange());
		
		// remove the launchable from the players inventory
		getGame().getCurrentPlayer().useItem(getDisc());
		// add the launchable to the square's inventory.
		endSquare.getInventory().addItem(disc);		
		
		// Checks for a hit on another player.
		for(Player player: getGame().getOtherPlayers()){
			if(player.getPosition() == endSquare){
				//FIXME: Add effect to player

				break;
			}
		}
	}

	/* (non-Javadoc)s
	 * @see event.AbstractGameEvent#afterGameEvent()
	 */
	@Override
	protected void afterGameCommand() {
		// TODO Auto-generated method stub
		
	}

}
