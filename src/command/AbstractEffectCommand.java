package command;

import item.inter.Movable;

/**
 * Created with IntelliJ IDEA.
 * User: Dieter
 * Date: 21/05/13
 * Time: 15:52
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractEffectCommand implements Command {

    /**
     * The game of this command
     */
    private Movable movable;

    /**
     * Construct a Game Command
     * @param   movable
     *          The game of this command
     */
    public AbstractEffectCommand(Movable movable){
        setMovable(movable);
    }

    public void setMovable(Movable movable) {
        this.movable = movable;
    }

    public Movable getMovable(){
        return movable;
    }
}
