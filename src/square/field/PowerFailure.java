package square.field;

import game.Player;
import item.IdentityDisc;
import square.Square;
import util.Direction;

import java.util.ArrayList;
import java.util.Random;

/**
 * User: jonas
 * Date: 21/05/13
 * Time: 16:23
 */
public class PowerFailure extends Field {

    public static final int LENGTH = 3;

    private static final int PRIMARY = 0;
    private static final int SECONDARY = 1;
    private static final int TERTIARY = 2;

    private int rotation;
    private Direction direction;

    private final Random random = new Random();

    /**
     *
     * @param   primary
     *          The square with the primary power failure
     * @param   rotation
     *          Zero is counterclockwise
     *          One is clockwise
     */
    public PowerFailure(Square primary){
        addSquare(PRIMARY,primary);
        createTail();
    }

    /**
     * Force field cannot extend its maximum length.
     */
    @Override
    public boolean isValidSquare(Square square) {
        return getLength() <= LENGTH && super.isValidSquare(square);
    }


    private void createTail(){

    }

    private void updateSecondary(){
        Square oldSecondary = getSquare(SECONDARY);
        unbind(oldSecondary);
        removeSquare(oldSecondary);

        Direction direction = getDirection().neighborDirections().get(getRotation());
        Square primary = getSquare(PRIMARY);
        Square secondary = primary.getNeighbor(direction);
        addSquare(SECONDARY,secondary);
        bind(secondary);

        updateTertiary();
    }

    private void updateTertiary(){
        Square oldTertiary = getSquare(TERTIARY);
        unbind(oldTertiary);
        removeSquare(oldTertiary);

        Square secondary = getSquare(SECONDARY);

        // Determine possible direction for this power failure
        ArrayList<Direction> possibleDirections = new ArrayList<Direction>();
        possibleDirections.add(getDirection());
        possibleDirections.addAll(getDirection().neighborDirections());

        Direction direction = possibleDirections.get(random.nextInt(2));
        Square tertiary = secondary.getNeighbor(direction);
        addSquare(TERTIARY,tertiary);

        bind(tertiary);
    }

    private Direction getDirection(){
        return this.direction;
    }

    private int getRotation(){
        return this.rotation;
    }



    @Override
    public void affect(Player player) throws IllegalStateException {
    }

    @Override
    public void affect(IdentityDisc identityDisc) throws IllegalStateException {
    }
}
