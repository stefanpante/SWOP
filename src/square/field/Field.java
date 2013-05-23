package square.field;

import square.Square;
import square.multi.MultiGridElement;

/**
 * A field is a collection of several squares,
 * with an effect on moveables.
 */
public abstract class Field extends MultiGridElement<Square> {

    public void setAllEffects(){
        for(Square square : getGridElements())
            setEffects(square);
    }

    public void removeAllEffects(){
        for(Square square : getGridElements())
            removeEffects(square);
    }

    public abstract void setEffects(Square square);

    public abstract void removeEffects(Square square);

}
