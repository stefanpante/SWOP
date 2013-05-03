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
	 * Moves the player into a certain direction. 
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
	public void move(){
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
