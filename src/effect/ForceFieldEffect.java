package effect;

import game.Player;
import item.IdentityDisc;
import item.Item;
import square.field.ForceField;

/**
 * User: jonas
 * Date: 14/05/13
 * Time: 11:35
 */
public class ForceFieldEffect extends Effect<ForceField> implements ItemEffect {

    public ForceFieldEffect(ForceField affectable) {
        super(affectable);
    }

    @Override
    public void affect(Item item) {
    }

    @Override
    public void affect(IdentityDisc identityDisc) {
        identityDisc.destroy();
    }
}
