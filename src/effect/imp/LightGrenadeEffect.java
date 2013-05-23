package effect.imp;

import effect.EffectPriority;
import game.Player;
import item.IdentityDisc;
import square.Square;

/**
 * User: jonas
 * Date: 23/05/13
 * Time: 13:01
 */
public class LightGrenadeEffect extends DropFlagEffect {

    /**
     * Creates a new  DropflagEffect with a square around which the flag will be dropped.
     *
     * @param centerSquare The square of which its neighbors can contain the dropped flag.
     */
    public LightGrenadeEffect(Square centerSquare) {
        super(centerSquare);
        setPriority(EffectPriority.Square);
    }

    @Override
    public void execute(Player player) {
        dropFlag(player);
    }

    @Override
    public void execute(IdentityDisc identityDisc) {
    }
}
