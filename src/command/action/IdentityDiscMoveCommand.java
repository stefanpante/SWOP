package command.action;

import game.Game;
import move.Movable;
import square.Direction;
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
     */
    public IdentityDiscMoveCommand(Game game, Movable movable, Square startSquare, Direction dir) {
        super(game, movable, startSquare, dir);
    }

    @Override
    protected void beforeGameCommand() {
		/* Check whether it's possible to move in the given direction */
        if(getDirection().isDiagonal()){
            throw new IllegalStateException("Cannot throw into given direction.");
        }
    }


    @Override
    protected void afterGameCommand() throws Exception {
        //See for solution
        getCurrentPosition().getInventory().remove(getMovable());
    }
    }