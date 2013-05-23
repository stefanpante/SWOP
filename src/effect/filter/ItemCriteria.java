package effect.filter;

import effect.Effect;
import effect.EffectPriority;

import java.util.ArrayList;

/**
 * User: jonas
 * Date: 23/05/13
 * Time: 12:38
 */
public class ItemCriteria extends PriorityCriteria {

    private static final EffectPriority ACCEPTED = EffectPriority.Item;

    @Override
    public EffectPriority getAccepted() {
        return ACCEPTED;
    }
}
