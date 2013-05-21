package effect;

import game.Player;
import item.IdentityDisc;
import item.inter.Movable;

/**
 * User: jonas
 * Date: 21/05/13
 * Time: 11:50
 */
public interface Effect {

    public void affect(Movable movable);

    public void affect(Player player) throws IllegalStateException;

    public void affect(IdentityDisc identityDisc) throws IllegalStateException;

}
