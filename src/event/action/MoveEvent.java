/**
 * 
 */
package event.action;

import event.effect.LoseTurnEvent;
import event.effect.TeleportEvent;
import game.Game;
import item.LightGrenade;
import item.Teleport;

import square.Direction;
import square.Square;

/**
 * @author jonas
 *
 */
public class MoveEvent extends ActionEvent {

	private Direction direction;
	
	/**
	 * Position the player is moving from.
	 */
	private Square currentPosition;
	
	/**
	 * Position the player is moving to.
	 */
	private Square newPosition;

	/**
	 * Moves the player into a certain direction. 
	 */
	public MoveEvent(Game game, Direction dir) {
		super(game);
		this.direction = dir;
		
		currentPosition = getGame().getCurrentPlayer().getPosition();
		newPosition = getGame().getGrid().getNeighbor(currentPosition, getDirection()); 
	}

	/**
	 * Returns the direction of the move.
	 */
	private Direction getDirection(){
		return this.direction;
	}

	@Override
	protected void beforeGameEvent() {
		/* Check wether it's possible to move in the given direction */
		if(!getGame().getGrid().canMoveTo(getGame().getCurrentPlayer().getPosition(), getDirection())){
			throw new IllegalStateException("Cannot move to given direction.");
		}
	}

	@Override
	protected void duringGameEvent() {
		getGame().getCurrentPlayer().move(newPosition);	

		if(newPosition.getInventory().hasTeleport()) {
			Teleport teleport = newPosition.getInventory().getTeleport();
			TeleportEvent teleportEvent = new TeleportEvent(getGame(), teleport);
			teleportEvent.run();
		}
	}

	//TODO: Does this handle the effect of a lightGrenade, check if the player is in the startposition of the other player
	// to see if he has won.
	@Override
	protected void afterGameEvent(){
		/* Activate Light Grenades on square leaving square */
		if(currentPosition.getInventory().hasLightGrenade()){
			LightGrenade lg = currentPosition.getInventory().getLightGrenade();
			
			try{
				if(lg.isDropped()){
					System.out.println("move and is dropped");
					lg.activate();
				}
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		}
		
		if(getGame().getCurrentPlayer().getPosition().getPower().isFailing()){
			LoseTurnEvent lte = new LoseTurnEvent(getGame(),getGame().getCurrentPlayer(),1,false);
			lte.run();
		}
		
		
	}

}
