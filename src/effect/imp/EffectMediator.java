package effect.imp;

import effect.Effect;
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

    public ArrayList<Effect> getResultingEffects(){
        ArrayList<Effect> forceFieldEffects = getSquareEffectBy(new ForceFieldCriteria());
        if(forceFieldEffects.size() > 0)
            return forceFieldEffects;

        ArrayList<Effect> playerEffects = getSquareEffectBy(new PlayerCriteria());
        if(playerEffects.size() > 0)
            return  playerEffects;

        ArrayList<Effect> moveBlockingEffects = getSquareEffectBy(new MoveBlockingCriteria());
        if(moveBlockingEffects.size() > 0)
            return moveBlockingEffects;

        ArrayList<Effect> moveEffects = getSquareEffectBy(new MoveCriteria());
        if(moveEffects.size() > 0)
            return moveEffects;

        ArrayList<Effect> itemEffects = getSquareEffectBy(new ItemCriteria());
        return  itemEffects;

    }

    private ArrayList<Effect> getSquareEffectBy(PriorityCriteria criteria){
        return criteria.meetsCriteria(this.effects);
    }

}
