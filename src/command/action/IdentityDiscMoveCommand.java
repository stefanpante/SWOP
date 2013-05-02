package command.action;

import item.IdentityDisc;
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

	private IdentityDisc id ;
    /**
     * Moves the player into a certain direction.
     */
    public IdentityDiscMoveCommand(Game game, IdentityDisc id, Square startSquare, Direction dir) {
        super(game, id, startSquare, dir);
        this.id = id;
    }

    @Override
    protected void beforeGameCommand() {
		/* Check whether it's possible to move in the given direction */
        if(getDirection().isDiagonal()){
            throw new IllegalStateException("Cannot throw into given direction.");
            
        }
        //getGame().getCurrentPlayer().getInventory().removeItem(id);
        
    }


    @Override
    protected void afterGameCommand() throws Exception {
        //See for solution
    	//getCurrentPosition().getInventory().addItem(id);
        
        
    }
    }