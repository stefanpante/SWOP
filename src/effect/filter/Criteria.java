package effect.filter;

import effect.Effect;
import effect.EffectPriority;

import java.util.ArrayList;

/**
 * User: jonas
 * Date: 23/05/13
 * Time: 12:18
 */
public interface Criteria {

    /**
     * Returns a list of Effects that meet the criteria
     *
     * @param   effects
     *          The list of effects to be checked
     * @return  The list of effects that meet the criteria
     */
    public ArrayList<Effect> meetsCriteria(ArrayList<Effect> effects);

}
