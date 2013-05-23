package effect.imp;

import effect.Effect;
import game.Player;
import item.IdentityDisc;
import square.Square;

/**
 * User: jonas
 * Date: 23/05/13
 * Time: 13:00
 */
public class PlayerEffect extends DropFlagEffect {

    Player interactingPlayer;

    /**
     * Creates a new  DropflagEffect with a square around which the flag will be dropped.
     *
     * @param centerSquare The square of which its neighbors can contain the dropped flag.
     */
    public PlayerEffect(Square centerSquare, Player interactingPlayr) {
        super(centerSquare);
        this.interactingPlayer = interactingPlayr;
    }

    public Player getInteractingPlayer() {
        return interactingPlayer;
    }

    @Override
    public void execute(Player player) {
        //TODO: set the player on its previous square
    }

    @Override
    public void execute(IdentityDisc identityDisc) {
       dropFlag(getInteractingPlayer());
       identityDisc.setPosition(getCenterSquare());
    }
}
