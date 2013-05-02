/**
 * 
 */
package command.action;

import game.Game;
import item.LightGrenade;
import move.Movable;
import square.Direction;
import square.Square;

import java.util.HashSet;
import java.util.NoSuchElementException;

/**
 * The Move event handles all the logic for a player to move from one square
 * to the other. It takes into account all the possible constraints and effects.
 * Such as Teleports, LightGrenades and various other items.
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 */
public class MoveCommand extends ActionCommand {

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
	public MoveCommand(Game game, Movable movable, Direction dir) {
		super(game);
		this.direction = dir;
        this.movable = movable;
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
	protected void beforeGameCommand() {
		/* Check whether it's possible to move in the given direction */
		if(!getGame().getGrid().canMoveTo(getGame().getCurrentPlayer().getPosition(), getDirection())){
			throw new IllegalStateException("Cannot move to given direction.");
		}
	}

	@Override
	protected void duringGameCommand() throws Exception {
		getGame().getCurrentPlayer().move(newPosition);
	}
	
	@Override
	protected void afterGameCommand() throws Exception {
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
	private void executeEffects() throws Exception {
		boolean hasNoPower = newPosition.getPower().isFailing();
		boolean hasActiveGrenade = false;
		
		LightGrenade grenade = null;
		
		if(newPosition.getInventory().hasLightGrenade()) {
			grenade = newPosition.getInventory().getLightGrenade();
			hasActiveGrenade = grenade.isActive();
		}

		
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


    /**
     * Returns the last possible square an object can
     * 	move to in the given direction with the given maximum range.
     * 	The method takes obstacles into account.
     * 	A MultiObstacle makes the result end just before the obstacle.
     * 	Another obstacle makes the result end on the obstacle.
     *
     * @param 	startSquare
     * 			The square on which the trajectory starts.
     * @param 	direction
     * 			The direction of the trajectory.
     * @param 	maximumRange
     * 			The maximum range of this trajectory.
     * @return
     */
    public Square getEndSquare(Square startSquare, Direction direction, int maximumRange){
        Square prevSquare;
        Square currentSquare = startSquare;
        HashSet<Square> passedDestinations = new HashSet<Square>();
        Square destination;
        int currentRange = 0;
        do{
            prevSquare = currentSquare;
            if(prevSquare.getPower().isFailing()){//Afterwards a check because and ID can always move
                maximumRange--;
            }
            teleport = prevSquare.getInventory().getTeleport();
            if(maximumRange > 0 && currentRange < maximumRange) {
                try {
                    if(teleport!= null && !prevWasTeleport){
                        destination = teleport.getDestination();
                        if(passedDestinations.contains(destination)){
                            return prevSquare;
                        }
                        passedDestinations.add(destination);
                        currentSquare = destination;
                        prevWasTeleport = true;
                    } else {
                        currentSquare = getGame().getGrid().getNeighbor(prevSquare, direction);
                        prevWasTeleport = false;
                    }
                } catch (NoSuchElementException e) {
                    return prevSquare;
                } catch (IllegalArgumentException e){
                    return prevSquare;
                }
                currentRange++;
            }

        } while(!currentSquare.isObstructed() && currentRange < maximumRange);
        if(currentSquare.isObstructed() && currentSquare.getObstacle().bouncesBack()){
            return prevSquare;
        }
        return currentSquare;
    }
}
