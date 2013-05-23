package effect.filter;

import effect.EffectPriority;

/**
 * User: jonas
 * Date: 23/05/13
 * Time: 12:29
 */
public class MoveBlockingCriteria extends PriorityCriteria {

    private static final EffectPriority ACCEPTED = EffectPriority.MoveBlock;

    @Override
    public EffectPriority getAccepted() {
        return ACCEPTED;
    }
}
