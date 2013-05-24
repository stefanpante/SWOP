package effect.imp;

import effect.Effect;
import effect.EffectPriority;
import effect.filter.*;

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

    public ArrayList<Effect> getResultingEffects(ArrayList<Effect> effects){

        System.out.println("--- Get Resulting Effects ---");
        System.out.println("Effects on Square :" + effects);

        boolean nextOneBlocked = false;
        for(int i = 0; i < EffectPriority.values().length; i++){

            EffectPriority effectPriority = EffectPriority.values()[i];
            System.out.println(i + " Checking priority: " + effectPriority);
            ArrayList<Effect> result = getSquareEffectsBy(effects, effectPriority);
            System.out.println(i + " Result: " + result);
            if(result.size() > 0){
                System.out.println("in        in");
                if(EffectPriority.values()[i] == EffectPriority.TeleportBlocked){
                    nextOneBlocked = true;
                    System.out.println("NextOneBlocked!");
                }else{
                    if(!nextOneBlocked){
                        System.out.println("not nextone");
                        System.out.println("--- End of Resulting Effects ---");
                        return result;
                    }else{
                        System.out.println("nextone");
                        nextOneBlocked = false;
                    }
                }
            }
        }
        System.out.println("--- End of Resulting Effects ---");
        return new ArrayList<>();
    }
}
