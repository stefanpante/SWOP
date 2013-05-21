package command.action;

import item.IdentityDisc;
import game.Game;
import util.Direction;
import square.Square;

/**
 * Created with IntelliJ IDEA.
 * User: Dieter
 * Date: 2/05/13
 * Time: 15:07
 * To change this template use File | Settings | File Templates.
 */
public class IdentityDiscMoveCommand extends MoveCommand {

	
    /**
     * Moves the player into a certain direction.
     * @param game			the Game.
     * @param id			the IdentityDisc which moves across the grid.
     * @param startSquare	the Square on which the move initiates.
     * @param dir			the direction in which the identityDisc moves.
     */
    public IdentityDiscMoveCommand(Game game, IdentityDisc id, Square startSquare, Direction dir) {
        super(game, id, startSquare, dir);
    }

    /**
     * Checks whether the given direction is valid for an IdentityDisc ( which is a precondition)
     */
    @Override
    protected void beforeGameCommand() {
		/* Check whether it's possible to move in the given direction */
        if(getDirection().isDiagonal()){
            throw new IllegalStateException("Cannot throw into given direction.");
            
        }
    }
    
    /**
     * Performs additional actions after the move to ensure consistency with the domain model.
     */
    @Override
    protected void afterGameCommand() throws Exception {
        getMovable().resetRange();
    }

    }