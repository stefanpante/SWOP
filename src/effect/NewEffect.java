package effect;

import game.Player;
import item.IdentityDisc;
import item.inter.Movable;

/**
 * User: jonas
 * Date: 22/05/13
 * Time: 17:44
 */
public interface NewEffect {

    /**
     * Indicates whether this Effect prohibits the movable from
     * moving to the owner of this effect.
     *
     * @return  True if and only if the Movable can move to the
     *          owner of this effect.
     */
    public boolean canMoveTo();

    /**
     * Affect the Movable with this Effect on moving onto
     * the owner of this Effect.
     *
     * @param   movable
     *          The movable to be affected on moving
     *          onto the owner of this effect.
     */
    public void onMoveToEffect(Movable movable);

    public void onMoveToEffect(Player player);

    public void onMoveToEffect(IdentityDisc identityDisc);

    /**
     * Affect the Movable with this Effect on standing on
     * the owner of this Effect.
     *
     * @param   movable
     *          The movable to be affected on standing
     *          on the owner of this effect.
     */
    public void onStandOnEffect(Movable movable);

    public void onStandOnEffect(Player player);

    public void onStandOnEffect(IdentityDisc identityDisc);

    /**
     * Indicates whether this Effect prohibits the movable from
     * moving from the owner of this effect.
     *
     * @return  True if and only if the Movable can move from the
     *          owner of this effect.
     */
    public boolean canMoveFrom();
}
