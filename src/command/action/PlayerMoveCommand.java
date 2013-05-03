package command.action;

import game.Game;
import item.LightGrenade;
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
public class PlayerMoveCommand extends MoveCommand{

    /**
     * Moves the player into a certain direction.
     */
    public PlayerMoveCommand(Game game, Movable movable, Square startSquare, Direction dir) {
        super(game, movable, startSquare, dir);
    }

    @Override
    protected void beforeGameCommand() {
		/* Check whether it's possible to move in the given direction */
        if(!getGame().getGrid().canMoveTo(getStartPosition(), getDirection())){
            throw new IllegalStateException("Cannot move to given direction.");
        }
    }

    @Override
    protected void afterGameCommand() throws Exception {
        activateLightGrenade();
    }


    /**
     * If the player just dropped a LightGrenade on the square he's moving from it has
     * to be activated.
     */
    private void activateLightGrenade() {
        if(getStartPosition().getInventory().hasLightGrenade()){
            LightGrenade lg = getStartPosition().getInventory().getLightGrenade();

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
