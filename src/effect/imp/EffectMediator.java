package effect.imp;

import effect.Effect;
import effect.EffectPriority;
import effect.filter.*;
import square.Square;

import java.util.ArrayList;

/**
 * User: jonas
 * Date: 23/05/13
 * Time: 13:10
 */
public class EffectMediator {

    private ArrayList<Effect> effects;

    public void setEffects(ArrayList<Effect> effects){
        this.effects = effects;
    }

    private ArrayList<Effect> getSquareEffectBy(EffectPriority priority){
        PriorityCriteria priorityCriteria = new PriorityCriteria(priority);
        return priorityCriteria.meetsCriteria(this.effects);
    }

    public ArrayList<Effect> getResultingEffects(){
        int length = EffectPriority.values().length;
        return getEffectsBetween(EffectPriority.values()[0],EffectPriority.values()[length-1]);
    }

    public ArrayList<Effect> getEffectsBetween(EffectPriority low, EffectPriority high){
        int lowIndex = low.ordinal();
        int highIndex = high.ordinal();

        if(lowIndex > highIndex)
            throw new IllegalArgumentException("The priority for low is higher than for high.");

        boolean nextOneBlocked = false;
        for(int i = lowIndex; i <= highIndex; i++){
            ArrayList<Effect> effects = getSquareEffectBy(EffectPriority.values()[i]);
            if(effects.size() > 0){
                if(EffectPriority.values()[i] == EffectPriority.MoveEffectBlock){
                    nextOneBlocked = true;
                }else{
                    if(!nextOneBlocked){
                        return effects;
                    }else{
                        nextOneBlocked = false;
                    }
                }
            }
        }
        return new ArrayList<>();
    }



}
