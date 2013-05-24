package square.field;

import effect.imp.PowerFailureEffect;
import game.Player;
import org.jetbrains.annotations.NotNull;
import square.GridElement;
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
    private static final int LENGTH = 3;

    /**
     * The duration of this Power Failure in terms of turns
     */
    private static final int DURATION_TURNS = 3;

    /**
     * The duration of this Power Failure in therms of actions
     */
    private static final int DURATION_ACTIONS = Player.MAX_ALLOWED_ACTIONS * DURATION_TURNS;


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
    private PowerFailureEffect powerFailureEffect;

    /**
     * Create a new Power Failure with the given square as primary.
     *
     * @param   primary
     *          The square with the primary power failure
     */
    public PowerFailure(Square primary){
        if(!isValidGridElement(primary))
            throw new IllegalArgumentException("The given square cannot be added to this Power Failure.");
        this.powerFailureEffect = new PowerFailureEffect();
        this.actions = 0;
        this.active = true;
        addGridElement(PRIMARY,primary);
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
    public boolean isValidGridElement(Square square) {
        return getLength() <= LENGTH && super.isValidGridElement(square);
    }

    /**
     * Initial creation of the tail of this Power Failure
     */
    private void createTail(){
        this.rotation = random.nextInt(2);
        try{
            this.direction = Direction.getRandomDirection();
            // Create secondary and tertiary
            Square primary = getGridElement(PRIMARY);
            setEffects(primary);
            GridElement secondary = primary.getNeighbor(direction);
            GridElement tertiary = secondary.getNeighbor(getTertiaryDirection());
            if(secondary.isSameType(new Square()) && tertiary.isSameType(new Square())) {
                // Add and setEffects the squaress
                setPowerFailure(SECONDARY, (Square)secondary);
                setPowerFailure(TERTIARY, (Square)tertiary);
            }
        }catch (NoSuchElementException ignored){

        }
    }

    /**
     * Update the Secondary Power Failure
     */
    private void updateSecondary(){
        try{
            Square oldSecondary = getGridElement(SECONDARY);
            removePowerFailure(oldSecondary);

            Direction direction = getDirection().neighborDirections().get(getRotation());
            Square primary = getGridElement(PRIMARY);
            GridElement secondary = primary.getNeighbor(direction);
            if(secondary.isSameType(new Square())){
                setPowerFailure(SECONDARY, (Square) secondary);
                updateTertiary();
            }
        }catch (NoSuchElementException ignored){

        }
    }

    /**
     * Updat the tertiary Power Failure
     */
    private void updateTertiary(){
        try{
            Square oldTertiary = getGridElement(TERTIARY);
            removePowerFailure(oldTertiary);

            Square secondary = getGridElement(SECONDARY);

            Direction direction = getTertiaryDirection();
            GridElement tertiary = secondary.getNeighbor(direction);
            if(tertiary.isSameType(new Square())){
                addGridElement((Square)tertiary);
            }
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
        ArrayList<Direction> possibleDirections = new ArrayList<>();
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
    void destroy(){
        for(Square square: getGridElements()){
            removePowerFailure(square);
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

    @NotNull
    @Override
    public String toString() {
        return "PowerFailure{" +
                "rotation=" + rotation +
                ", direction=" + direction +
                ", actions=" + actions +
                ", active=" + active +
                '}';
    }

    void setPowerFailure(int i, @NotNull Square square) {
        addGridElement(i, square);
        setEffects(square);
    }

    void removePowerFailure(@NotNull Square square) {
        removeGridElement(square);
        removeEffects(square);
    }

    @Override
    protected void setEffects(@NotNull Square square) {
        square.addSquareEffect(powerFailureEffect);
    }

    @Override
    protected void removeEffects(@NotNull Square square) {
        square.removeSquareEffect(powerFailureEffect);
    }
}
