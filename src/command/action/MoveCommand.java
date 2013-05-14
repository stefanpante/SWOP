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
 * @author Dieter Castel, Jonas Devlieghere   and Stefan Pante
 */
public class MoveCommand extends ActionCommand {

	/**
	 * The direction moving to.
	 */
	private Direction direction;


	/**
	 * The movable of this MoveCommand.
	 */
	private Movable movable;

	/**
	 * Position the player is moving from.
	 */
	private Square startPosition;

	/**
	 * Position the Movable was on before the currentPosition.
	 */
	private Square prevPosition;

	/**
	 * Position the Movable will end on.
	 */
	private Square currentPosition;

	/**
	 * Moves the movable( Player and IdentityDisc for now)  into a certain direction.
	 * @param game			the Game.
     * @param movable		the movable which moves across the grid.
     * @param startSquare	the Square on which the move initiates.
     * @param dir			the direction in which the identityDisc moves. 
	 */
	public MoveCommand(Game game, Movable movable, Square startSquare, Direction dir) {
		super(game);
		this.direction = dir;
		this.movable = movable;
		startPosition = startSquare;
	}


	/**
	 * Returns the direction of the move.
	 */
	protected Movable getMovable(){
		return this.movable;
	}


	/**
	 * Returns the direction of the move.
	 */
	protected Square getStartPosition(){
		return this.startPosition;
	}


	/**
	 * Returns the direction of the move.
	 */
	protected Direction getDirection(){
		return this.direction;
	}

	private void setPrevPosition(Square prevPosition){
		this.prevPosition = prevPosition;
	}

	protected Square getPrevPosition(){
		return prevPosition;
	}

	private void setCurrentPosition(Square currentPosition){
		this.currentPosition = currentPosition;
	}

	protected Square getCurrentPosition(){
		return currentPosition;
	}

	@Override
	protected void beforeGameCommand() {
		//NOOP
	}



	@Override
	protected void duringGameCommand() throws Exception {
		move();
	}

	@Override
	protected void afterGameCommand() throws Exception {
		//NOOP
	}

	/**
	 * Checks if the action would cause an invalidation of the model.
	 * Checks all the precondition for the move action.
	 */
	@Override
	protected void beforeActionCommand(){
		if(!getGame().isActive())
			throw new IllegalStateException("The game is over.");
		if(getGame().getCurrentPlayer().getRemainingActions() <= 0)
			throw new IllegalStateException("The current player has no remaining action left.");
		if(getGame().isCurrentPlayerStuck())
			throw new IllegalStateException("The current player is stuck.");
	}


	/**
	 * performs the actual move of the movable
	 */
	protected void move(){
		setCurrentPosition(getStartPosition());
		int currentRange = 0;
		Square currentSquare;

		do{
			try {
				currentSquare = getGame().getGrid().getNeighbor(getCurrentPosition(), direction);
				if(!currentSquare.isObstructed()){
					movable.move(currentSquare);
					setPrevPosition(getCurrentPosition());
					setCurrentPosition(movable.getPosition());
				}
				else{
					return;
				}

			} catch (NoSuchElementException e) {
				return;
			}  catch (IllegalArgumentException e){
				return;
			}
			currentRange++;
		} while(!getCurrentPosition().isObstructed()
				&& currentRange < getMovable().getRange()
				&& getMovable().getRange() > 0);

	}
}
