package effect.filter;

import effect.EffectPriority;

/**
 * User: jonas
 * Date: 23/05/13
 * Time: 12:34
 */
public class MoveCriteria extends PriorityCriteria {

    private static final EffectPriority ACCEPTED = EffectPriority.Move;

    @Override
    public EffectPriority getAccepted() {
        return ACCEPTED;
    }
}
