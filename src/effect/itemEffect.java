package effect;

import item.Item;
import item.IdentityDisc;

/**
 * User: jonas
 * Date: 14/05/13
 * Time: 10:37
 */
public interface ItemEffect {

    public void affect(Item item);

    public void affect(IdentityDisc identityDisc);

}
