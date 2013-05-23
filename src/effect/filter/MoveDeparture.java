package effect.filter;

import effect.EffectPriority;

/**
 * Created with IntelliJ IDEA.
 * User: Dieter
 * Date: 23/05/13
 * Time: 17:19
 * To change this template use File | Settings | File Templates.
 */
public class MoveDeparture extends PriorityCriteria {

    private static final EffectPriority ACCEPTED = EffectPriority.MoveDeparture;

    @Override
    public EffectPriority getAccepted() {
        return ACCEPTED;
    }
}
