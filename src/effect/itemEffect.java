package effect;

import item.Flag;
import item.Item;
import item.IdentityDisc;

/**
 * User: jonas
 * Date: 14/05/13
 * Time: 10:37
 */
/*
*   TODO: Implement dropping of flag on neighboring square(s)
*       For affecting a flag (LightGrenade, Teleport and IdentityDisc should do so)
*       we need the square the flag is placed on to find out its neighbor.
*       Currently their is no way of doing this.
*       Also this three implementations for he three classes are exactly the same.
*       We need a way to avoid code duplication
*/
public interface ItemEffect {

    public void affect(Item item);

    public void affect(IdentityDisc identityDisc);

    public void affect(Flag flag);
}
