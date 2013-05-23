package effect;

/**
 * User: jonas
 * Date: 23/05/13
 * Time: 12:46
 */
public enum EffectPriority {
    /**
     * Highest priority preventing players from entering the square
     * and allowing Idenity Disks to enter and be destroyed.
     */
    ForceField,
    /**
     * Priority allowing IdentityDisks to be affect by the Current Player on a square
     */
    Player,
    /**
     * Priority allowing a square to indicate it's not accesible by a movable.
     */
    MoveBlock,
    /**
     * Prioirty allowing to block the next level of priority: movement (in particular Teleport) effects
     */
    MoveEffectBlock,
    /**
     * Priority allowing movements such as teleports to place the Movable on a different location.
     */
    Move,
    /**
     * Priority allowing items on a Square to affect the movable on it such as Light Grenades.
     */
    Item,
    /**
     * Lowest priority allowing to prevent a player from leaving
     */
    MoveDeparture;
}
