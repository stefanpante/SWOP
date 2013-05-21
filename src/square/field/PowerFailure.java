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
    public static final int DURATION_TURNS = 3;
    public static final int DURATION_ACTIONS = Player.MAX_ALLOWED_ACTIONS * DURATION_TURNS;

    private static final int PRIMARY = 0;
    private static final int SECONDARY = 1;
    private static final int TERTIARY = 2;

    private int rotation;
    private Direction direction;
    private int actions;
    private boolean active;

    private final Random random = new Random();

    /**
     * Create a new Power Failure with the given square as primary.
     *
     * @param   primary
     *          The square with the primary power failure
     */
    public PowerFailure(Square primary){
        if(!isValidSquare(primary))
            throw new IllegalArgumentException("The given square cannot be added to this Power Failure.");
        this.actions = 0;
        this.active = true;
        addSquare(PRIMARY,primary);
        createTail();
    }

    /**
     * Increase the actions
     */
    public void increaseActions(){
        this.actions++;
        if(actions >= DURATION_ACTIONS)
            destroy();
        else if(actions%2 == 0)
            updateSecondary();
        else
            updateTertiary();
    }

    /**
     * Force field cannot extend its maximum length.
     */
    @Override
    public boolean isValidSquare(Square square) {
        return getLength() <= LENGTH && super.isValidSquare(square);
    }

    /**
     * Initial creation of the tail of this Power Failure
     */
    private void createTail(){
        // Random rotation and direction
        this.rotation = random.nextInt(1);
        this.direction = Direction.getRandomDirection();

        // Create secondary and tertiary
        Square primary = getSquare(PRIMARY);
        Square secondary = primary.getNeighbor(direction);
        Square tertiary = secondary.getNeighbor(getTertiaryDirection());

        // Add and bind the squaress
        addSquare(SECONDARY,secondary);
        addSquare(TERTIARY,tertiary);
        bindAll();
    }

    /**
     * Update the Secondary Power Failure
     */
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

    /**
     * Updat the tertiary Power Failure
     */
    private void updateTertiary(){
        Square oldTertiary = getSquare(TERTIARY);
        unbind(oldTertiary);
        removeSquare(oldTertiary);

        Square secondary = getSquare(SECONDARY);

        Direction direction = getTertiaryDirection();
        Square tertiary = secondary.getNeighbor(direction);
        addSquare(TERTIARY,tertiary);

        bind(tertiary);
    }

    /**
     * Determine a random direction for the tertiary Power Failure
     *
      * @return A random pick from the direction in which the
     *          secondary Power Failure lies and the two
     *          neighboring directions.
     */
    private Direction getTertiaryDirection(){
        ArrayList<Direction> possibleDirections = new ArrayList<Direction>();
        possibleDirections.add(getDirection());
        possibleDirections.addAll(getDirection().neighborDirections());
        return possibleDirections.get(random.nextInt(2));
    }

    /**
     * Get the direction in which the secondary Power Failure lies
     *
     * @return  The direction of the secondary Power Failure
     */
    private Direction getDirection(){
        return this.direction;
    }

    /**
     * The direction in which this Power Failure rotates: clockwise or counterclockwise.
     *
     * @return  1 if the rotation is clockwise
     *          0 if the rotation is counterclockwise
     */
    private int getRotation(){
        return this.rotation;
    }

    @Override
    public void affect(Player player) throws IllegalStateException {
    }

    @Override
    public void affect(IdentityDisc identityDisc) throws IllegalStateException {
    }

    /**
     * Desttry this Power Failure
     */
    public void destroy(){
        unbindAll();
        for(Square square: getSquares()){
            removeSquare(square);
        }
        this.active = false;
    }

    /**
     * Returns wether this Power Failure is active
     *
     * @return  True if and only if this Power Failure is active
     */
    public boolean isActive(){
        return this.active;
    }
}
