package square.field;

import game.Player;
import item.IdentityDisc;
import item.inter.Movable;
import square.Square;
import util.Direction;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * User: jonas
 * Date: 21/05/13
 * Time: 16:23
 */
public class PowerFailure extends Field {

    /**
     * The lenght of a Power Failure Field
     */
    public static final int LENGTH = 3;

    /**
     * The duration of this Power Failure in terms of turns
     */
    public static final int DURATION_TURNS = 3;

    /**
     * The duration of this Power Failure in therms of actions
     */
    public static final int DURATION_ACTIONS = Player.MAX_ALLOWED_ACTIONS * DURATION_TURNS;


    private static final int PRIMARY = 0;
    private static final int SECONDARY = 1;
    private static final int TERTIARY = 2;

    /**
     * The direction in which this Power Failure rotates
     */
    private int rotation;

    /**
     * The direction in which this Power Failure lies
     */
    private Direction direction;

    /**
     * The amount of actions this Power Failure has lived
     */
    private int actions;

    /**
     * Flag that indicates whether this Power Failure is alive
     */
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
        this.rotation = random.nextInt(2);
        try{
            this.direction = Direction.getRandomDirection();
            // Create secondary and tertiary
            Square primary = getSquare(PRIMARY);
            Square secondary = primary.getNeighbor(direction);
            Square tertiary = secondary.getNeighbor(getTertiaryDirection());

            // Add and bind the squaress
            addSquare(SECONDARY,secondary);
            addSquare(TERTIARY, tertiary);
            bindAll();
        }catch (NoSuchElementException ignored){

        }
    }

    /**
     * Update the Secondary Power Failure
     */
    private void updateSecondary(){
        try{
            Square oldSecondary = getSquare(SECONDARY);
            unbind(oldSecondary);
            removeSquare(oldSecondary);

            Direction direction = getDirection().neighborDirections().get(getRotation());
            Square primary = getSquare(PRIMARY);
            Square secondary = primary.getNeighbor(direction);
            addSquare(SECONDARY,secondary);
            bind(secondary);
            updateTertiary();
        }catch (NoSuchElementException ignored){

        }
    }

    /**
     * Updat the tertiary Power Failure
     */
    private void updateTertiary(){
        try{
            Square oldTertiary = getSquare(TERTIARY);
            unbind(oldTertiary);
            removeSquare(oldTertiary);

            Square secondary = getSquare(SECONDARY);

            Direction direction = getTertiaryDirection();
            Square tertiary = secondary.getNeighbor(direction);
            addSquare(TERTIARY,tertiary);
            bind(tertiary);
        }catch (NoSuchElementException ignored){

        }

    }

    /**
     * Determine a random direction for the tertiary Power Failure
     *
     * @return  A random pick from the direction in which the
     *          secondary Power Failure lies and the two
     *          neighboring directions.
     */
    private Direction getTertiaryDirection(){
        ArrayList<Direction> possibleDirections = new ArrayList<Direction>();
        possibleDirections.add(getDirection());
        possibleDirections.addAll(getDirection().neighborDirections());
        return possibleDirections.get(random.nextInt(3));
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
     * Returns whether this Power Failure is active
     *
     * @return  True if and only if this Power Failure is active
     */
    public boolean isActive(){
        return this.active;
    }

    @Override
    public String toString() {
        return "PowerFailure{" +
                "rotation=" + rotation +
                ", direction=" + direction +
                ", actions=" + actions +
                ", active=" + active +
                '}';
    }

	@Override
	public boolean canMoveTo() {
		return true;
	}

	@Override
	public void onMoveToEffect(Movable movable) {
		movable.acceptMoveToEffect(this);
		
	}

	@Override
	public void onMoveToEffect(Player player) {
		player.loseActions(1);
		
	}

	@Override
	public void onMoveToEffect(IdentityDisc identityDisc) {
		identityDisc.decreaseRange();
	}

	@Override
	public void onStandOnEffect(Movable movable) {
		movable.acceptStandOnEffect(this);
		
	}

	@Override
	public void onStandOnEffect(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStandOnEffect(IdentityDisc identityDisc) {
		// nothing to do here.
		
	}

	@Override
	public boolean canMoveFrom() {
		return true;
	}
}
