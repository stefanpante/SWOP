package effect;

import game.Player;
import item.IdentityDisc;
import item.inter.Movable;

/**
 * User: jonas
 * Date: 23/05/13
 * Time: 12:22
 */
public abstract class Effect {

    private EffectPriority effectPriority;


    public void setPriority(EffectPriority effectPriority){
        this.effectPriority = effectPriority;
    }

    public boolean hasPriority(EffectPriority effectPriority){
        return this.effectPriority == effectPriority;
    }

    public abstract void execute(Player player);

    public abstract void execute(IdentityDisc identityDisc);

}
