package square.field;

import square.MultiSquare;
import square.Square;

import java.util.ArrayList;

/**
 * User: jonas
 * Date: 02/05/13
 * Time: 16:27
 */
public class Field extends MultiSquare {

    @Override
    public void addSquare(Square square){
        super.addSquare(square);
        square.addField(this);
    }
}
