package effect.imp;

import effect.Effect;
import effect.EffectPriority;
import effect.filter.PriorityCriteria;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * User: jonas
 * Date: 23/05/13
 * Time: 13:10
 */
public class EffectMediator {

    private ArrayList<Effect> getSquareEffectsBy(ArrayList<Effect> effects, EffectPriority priority){
        PriorityCriteria priorityCriteria = new PriorityCriteria(priority);
        return priorityCriteria.meetsCriteria(effects);
    }


    ArrayList<Effect> getResultingEffectsWithPriorityAbove(ArrayList<Effect> effects, @NotNull EffectPriority priority){

        int max = priority.ordinal();

        boolean nextOneBlocked = false;
        for(int i = 0; i < max; i++){

            EffectPriority effectPriority = EffectPriority.values()[i];
            ArrayList<Effect> result = getSquareEffectsBy(effects, effectPriority);
            if(result.size() > 0){
                System.out.println("in        in");
                if(EffectPriority.values()[i] == EffectPriority.TeleportBlocked){
                    nextOneBlocked = true;
                    System.out.println("NextOneBlocked!");
                }else{
                    if(!nextOneBlocked){
                        return result;
                    }else{
                        nextOneBlocked = false;
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    public ArrayList<Effect> getResultingEffects(ArrayList<Effect> effects){
        return getResultingEffectsWithPriorityAbove(effects, EffectPriority.values()[EffectPriority.values().length - 1]);
    }
}
