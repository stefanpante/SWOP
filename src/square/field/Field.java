package square.field;

import square.Square;
import square.multi.MultiGridElement;

/**
 * A field is a collection of several squares,
 * with an effect on moveables.
 */
public abstract class Field extends MultiGridElement<Square> {

    public void bindAll(){
        for(Square square : getGridElements())
            bind(square);
    }

    public void unbindAll(){
        for(Square square : getGridElements())
            unbind(square);
    }

    public void bind(Square square){
        // TODO:
    }

    public void unbind(Square square){
        // TODO:
    }

}
