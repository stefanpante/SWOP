package effect.filter;

import effect.Effect;
import effect.EffectPriority;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * User: jonas
 * Date: 23/05/13
 * Time: 13:35
 */
public class PriorityCriteria implements Criteria {

    private final EffectPriority effectPriority;

    public PriorityCriteria(EffectPriority priority){
        this.effectPriority = priority;
    }

    @NotNull
    @Override
    public ArrayList<Effect> meetsCriteria(@NotNull ArrayList<Effect> effects) {
        ArrayList<Effect> accepted = new ArrayList<>();
        for(Effect effect : effects){
            if(effect.hasPriority(this.effectPriority))
                accepted.add(effect);
        }
        return accepted;
    }

}
