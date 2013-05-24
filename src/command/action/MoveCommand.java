/**
 * 
 */
package command.action;

import game.Game;
import item.inter.Movable;
import square.GridElement;
import square.Square;
import util.Direction;

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
	private final Direction direction;


	/**
	 * The movable of this MoveCommand.
	 */
	private final Movable movable;

	/**
	 * Position the player is moving from.
	 */
	private final Square startPosition;

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
    MoveCommand(Game game, Movable movable, Square startSquare, Direction dir) {
		super(game);
		this.direction = dir;
		this.movable = movable;
		startPosition = startSquare;
	}


	/**
	 * Returns the direction of the move.
	 */
    Movable getMovable(){
		return this.movable;
	}


	/**
	 * Returns the direction of the move.
	 */
    Square getStartPosition(){
		return this.startPosition;
	}


	/**
	 * Returns the direction of the move.
	 */
    Direction getDirection(){
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
	protected void duringGameCommand() {
		move();
	}

    @Override
    protected void afterGameCommand() {
        //NOOP
    }

    /**
	 * performs the actual move of the movable
	 */
    void move(){
        System.out.println("MOVE");

		setCurrentPosition(getStartPosition());
		int currentRange = 0;

        try {

            do{
            	GridElement gridElement = getCurrentPosition().getNeighbor(direction);
                if(!gridElement.isObstacle() && gridElement.isSameType(new Square())){
                    currentRange++;
                    Square square = (Square) gridElement;
                    movable.move(square);
                    //setPrevPosition(getCurrentPosition());
                    //setCurrentPosition(movable.getPosition());
                }else{
                    return;
                }
            } while(currentRange < getMovable().getRange()
                    && getMovable().getRange() > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
