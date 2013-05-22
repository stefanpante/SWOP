package effect.event;

import item.inter.Movable;

/**
 * Created with IntelliJ IDEA.
 * User: Dieter
 * Date: 22/05/13
 * Time: 21:31
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractEffectEvent {

    /**
     * The game of this command
     */
    private Movable movable;

    /**
     * Construct a Game Command
     * @param   movable
     *          The game of this command
     */
    public AbstractEffectEvent(Movable movable){
        setMovable(movable);
    }

    public void setMovable(Movable movable) {
        this.movable = movable;
    }

    public Movable getMovable(){
        return movable;
    }

    abstract void execute() throws Exception;
}
