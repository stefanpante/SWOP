package effect;

import game.Player;
import item.launchable.IdentityDisc;
import move.Movable;

/**
 * User: jonas
 * Date: 02/05/13
 * Time: 13:39
 */
public interface MovableEffect {

    public void affect(Movable movable);

    public void affect(Player player);

    public void affect(IdentityDisc identityDisc);

}
