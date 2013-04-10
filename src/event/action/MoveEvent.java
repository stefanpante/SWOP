/**
 * 
 */
package event.action;

import event.effect.LoseActionEvent;
import event.effect.LoseTurnEvent;
import event.effect.TeleportEvent;
import game.Game;
import item.LightGrenade;
import item.Teleport;

import square.Direction;
import square.Square;

/**
 * The Move event handles all the logic for a player to move from one square
 * to the other. It takes into account all the possible constraints and effects.
 * Such as Teleports, LightGrenades and various other items.
 * 
 * @author jonas, Vincent
 */
public class MoveEvent extends ActionEvent {

	/**
	 * The direction moving to.
	 */
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

	//TODO: check if the player is in the startposition of the other player to see if he has won.
	@Override
	protected void afterGameEvent(){
		activateLightGrenade();
		
		executeEffects();		
	}

	/**
	 * If there is a LightGrenade or PowerFailure there needs to be an effect on the Player.
	 * There are 3 possible situations.
	 * 
	 * 1) There is a PowerFailure and <b>active</b> LightGrenade
	 * 2) There is only a PowerFailure
	 * 3) There is only an <b>active</b> LightGrenade
	 * 
	 * @effect
	 * 1) Player loses next 4 actions
	 * 2) Player loses his next 3 actions.
	 * 3) Player loses his turn.
	 * 
	 * @post
	 * If there was a lightGrenade it is deactivated.
	 */
	private void executeEffects() {
		boolean hasNoPower = newPosition.getPower().isFailing();
		boolean hasActiveGrenade = false;
		
		LightGrenade grenade = null;
		
		if(newPosition.getInventory().hasLightGrenade()) {
			grenade = newPosition.getInventory().getLightGrenade();
			hasActiveGrenade = grenade.isActive();
		}
		
		if(hasActiveGrenade && hasNoPower) {
			new LoseActionEvent(getGame(), 4).run();	
		}else if(hasActiveGrenade)
			new LoseActionEvent(getGame(), 3).run();
		else if(hasNoPower)
			new EndTurnEvent(getGame()).run();
		
		if(hasActiveGrenade)
			grenade.deactivate();
	}

	/**
	 * If the player just dropped a LightGrenade on the square he's moving from it has 
	 * to be activated.
	 */
	private void activateLightGrenade() {
		if(currentPosition.getInventory().hasLightGrenade()){
			LightGrenade lg = currentPosition.getInventory().getLightGrenade();
			
			try{
				if(lg.isDropped()){
					lg.activate();
				}
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		}
	}

}
