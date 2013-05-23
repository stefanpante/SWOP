package effect.filter;

import effect.Effect;
import effect.EffectPriority;

import java.util.ArrayList;

/**
 * User: jonas
 * Date: 23/05/13
 * Time: 13:34
 */
public class ForceFieldCriteria extends PriorityCriteria {

    private static final EffectPriority ACCEPTED = EffectPriority.ForceField;

    @Override
    public EffectPriority getAccepted() {
        return ACCEPTED;
    }
}
