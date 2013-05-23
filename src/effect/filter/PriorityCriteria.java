package effect.filter;

import effect.Effect;
import effect.EffectPriority;

import java.util.ArrayList;

/**
 * User: jonas
 * Date: 23/05/13
 * Time: 13:35
 */
public abstract class PriorityCriteria implements Criteria {


    public abstract EffectPriority getAccepted();

    @Override
    public ArrayList<Effect> meetsCriteria(ArrayList<Effect> effects) {
        ArrayList<Effect> accepted = new ArrayList<>();
        for(Effect effect : effects){
            if(effect.hasPriority(getAccepted()))
                accepted.add(effect);
        }
        return accepted;
    }

}
