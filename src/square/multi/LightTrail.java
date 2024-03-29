package square.multi;

import effect.imp.LightTrailEffect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import square.Square;
import square.field.Field;

/**
 * LightTrail is a trail that is left behind the player while he
 * executes actions on the grid. The LightTrail is an obstacle.
 * 
 * @author Dieter Castel, Jonas Devlieghere   and Stefan Pante
 */
public class LightTrail extends Field {

    @NotNull
    private final LightTrailEffect lightTrailEffect;

	/**
	 * Maximum length of a LightTrail.
	 */
	private static final int MAX_LENGTH = 3;
	
	/**
	 * Initialises a new linkedList for the LightTrail.
	 */
	public LightTrail() {
		super();
        this.lightTrailEffect = new LightTrailEffect();
	}
	
	/**
	 * Extend the light trail by adding a new square to the trail.
	 * 
	 * @param 	square 	
	 * 			The square to add to the trail
	 * @throws 	IllegalArgumentException
	 * 			If the square is not valid.
	 */
	@Override
	public void addGridElement(@NotNull Square square) throws IllegalArgumentException {
		if(!isValidGridElement(square))
			throw new IllegalArgumentException("This square is not valid for this Light Trail.");
		
		if(getLength() >= MAX_LENGTH) 
			this.removeGridElement(getLastSquare());
		
		super.addGridElement(square);
        setEffects(square);
	}
	
	/**
	 * Removes a square of the obstacle.
	 * 
	 * @param 	square
	 *          The square to be removed.
	 * @throws 	IllegalArgumentException 
	 * 			If the square is not valid.
	 */
	@Override
    protected void removeGridElement(@NotNull Square square) throws IllegalArgumentException {
		super.removeGridElement(square);
        removeEffects(square);
	}
	
	/**
	 * Returns the last square that was added in the LightTrail.
	 * 
	 * @return	Square	If the trail is not empty the last one is returned.
	 * 			Null	If the trail is empty.
	 */
    @Nullable
    Square getLastSquare() {
		if(getLength() >= 1)
			return getGridElements().get(0);
		else
			return null;
	}
	
	/**
	 * Returns the newest square which was recently added.
	 * 
	 * @return	Square	If the trail is not empty the newest one is returned.
	 * 			Null	If the trail is empty.
	 */
    @Nullable
    Square getNewestSquare() {
		if(getLength() >= 1)
			return getGridElements().get(getLength() - 1);
		else
			return null;
	}

	public void setHead(@NotNull Square square) {
        try{
            if(getNewestSquare() == square)
                this.removeGridElement(getLastSquare());
            else
                this.addGridElement(square);
        }catch (Exception e){
            e.printStackTrace();
        }

	}

    @Override
    protected void setEffects(@NotNull Square square) {
        square.addSquareEffect(lightTrailEffect);
    }

    @Override
    protected void removeEffects(@NotNull Square square) {
        square.removeSquareEffect(lightTrailEffect);
    }


}
