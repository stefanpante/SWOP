package effect;

import item.Activatable;
import item.Affectable;

/**
 * User: jonas
 * Date: 14/05/13
 * Time: 10:38
 */
public abstract class Effect<T extends Affectable> {

    private T affectable;

    public Effect(T affectable){
        this.affectable = affectable;
    }

    public T getAffectable(){
        return affectable;
    }
}
