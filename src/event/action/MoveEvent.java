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

	public MoveEvent(Game game, Direction dir) {
		super(game);
		this.direction = dir;
	}
	
	private Direction getDirection(){
		return this.direction;
	}
	
	@Override
	protected void beforeGameEvent() {
		/* Check wether it's possible to move in the given direction */
		if(!getGame().getGrid().canMoveTo(getGame().getCurrentPlayer().getPosition(), getDirection())){
			throw new IllegalStateException("Cannot move to given direction.");
		}
		/* Activate Light Grenades on square leaving square */
		Square currentPosition = getGame().getCurrentPlayer().getPosition();
		if(currentPosition.getInventory().hasLightGrenade()){
			LightGrenade lg = currentPosition.getInventory().getLightGrenade();
			try{
				lg.activate();
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		}
		

	}

	@Override
	protected void duringGameEvent() {
		Square currentPosition = getGame().getCurrentPlayer().getPosition();
		Square newPosition = getGame().getGrid().getNeighbor(currentPosition, getDirection()); 
		getGame().getCurrentPlayer().move(newPosition);	
		
		if(newPosition.getInventory().hasTeleport()) {
			Teleport teleport = newPosition.getInventory().getTeleport();
			TeleportEvent teleportEvent = new TeleportEvent(getGame(), teleport);
			teleportEvent.run();
		}
	}

	@Override
	protected void afterGameEvent(){
		if(getGame().getCurrentPlayer().getPosition().getPower().isFailing()){
			LoseTurnEvent lte = new LoseTurnEvent(getGame(),1,false);
			lte.run();
		}
	}

}
