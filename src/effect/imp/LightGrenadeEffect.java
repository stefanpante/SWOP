package effect.imp;

import effect.EffectPriority;
import game.Player;
import item.IdentityDisc;
import org.jetbrains.annotations.NotNull;
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
    public void execute(@NotNull Player player) {
        System.out.println("Executing on Player: " + this);
        dropFlag(player);
    }

    @Override
    public void execute(IdentityDisc identityDisc) {
    }

    @NotNull
    @Override
    public String toString() {
        return "LightGrenadeEffect";
    }

}
