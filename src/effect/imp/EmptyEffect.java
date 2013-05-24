package effect.imp;

import effect.Effect;
import effect.EffectPriority;
import game.Player;
import item.IdentityDisc;

public class EmptyEffect extends Effect {

    public EmptyEffect(){
        setPriority(EffectPriority.Square);
    }

    @Override
    public void execute(Player player) {
        // Do nothing
    }

    @Override
    public void execute(IdentityDisc identityDisc) {
        // Do nothing
    }

    @Override
    public String toString() {
        return "EmptyEffect";
    }
}
