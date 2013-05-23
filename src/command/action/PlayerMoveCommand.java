package command.action;

import game.Game;
import item.ForceFieldGenerator;
import item.LightGrenade;
import item.inter.Movable;
import util.Direction;
import square.Square;

/**
 * Created with IntelliJ IDEA.
 * User: Dieter
 * Date: 2/05/13
 * Time: 15:07
 */
public class PlayerMoveCommand extends MoveCommand{

    /**
     * Moves the player into a certain direction.
	 * @param game			the Game.
     * @param movable		the movable which moves across the grid.
     * @param startSquare	the Square on which the move initiates.
     * @param dir			the direction in which the identityDisc moves. 
     */
    public PlayerMoveCommand(Game game, Movable movable, Square startSquare, Direction dir) {
        super(game, movable, startSquare, dir);
    }

    /**
     * Checks the precondition for the move command.
     */
    @Override
    protected void beforeGameCommand() {
        if(getGame().isCurrentPlayerStuck())
            throw new IllegalStateException("The current player is stuck.");
		/* Check whether it's possible to move in the given direction */
//        TODO: remove this.
//        if(!getGame().getGrid().canMoveTo(getStartPosition(), getDirection())){
//            throw new IllegalStateException("Cannot move to given direction.");
//        }
    }

    /**
     * Performs additional actions after the move to ensure consistency with the domain model.
     */
    @Override
    protected void afterGameCommand() throws Exception {
        getMovable().resetRange();
    }

}
