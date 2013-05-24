package effect.imp;

import effect.Effect;
import effect.EffectPriority;
import game.Player;
import item.IdentityDisc;
import org.jetbrains.annotations.NotNull;

public class EmptyEffect extends Effect {

    public EmptyEffect(){
        setPriority(EffectPriority.Square);
    }

    @Override
    public void execute(Player player) {
        System.out.println("Executing on Player: " + this);
    }

    @Override
    public void execute(IdentityDisc identityDisc) {
        // Do nothing
    }

    @NotNull
    @Override
    public String toString() {
        return "EmptyEffect";
    }
}
