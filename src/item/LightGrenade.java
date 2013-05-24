package item;

import effect.Effect;
import effect.imp.EmptyEffect;
import effect.imp.LightGrenadeEffect;
import org.jetbrains.annotations.NotNull;
import square.Square;

import java.util.ArrayList;

/**
 * This class extends Square and represents a LightGrenade object.
 *  
 * @author Dieter Castel, Jonas Devlieghere and Stefan Pante
 *
 */
public class LightGrenade extends Item{

    public static final int LOST_ACTIONS = 3;
    private boolean activated = false;

    /**
     * Returns whether the grenade has been activated.
     */
	public boolean isActive() {
        return activated;
	}

	public void activate() throws IllegalStateException {
        activated = true;
	}

    public boolean canAddTo(@NotNull Square square){
        return (!square.hasItem(this) && !square.hasType(this));
    }

	/**
	 * Returns the string representation of this LightGrenade
	 */
	@NotNull
    @Override
	public String toString() {
		return super.toString() + "LightGrenade";
	}

	/*
	 * Returns whether the given object is a lightgrenade
	 */
	public static boolean isLightGrenade(Object o){
		return (o instanceof LightGrenade);
	}
	
	/**
	 * returns whether the given object is of the same type.
	 * @param o the item to be checked
	 */
	@Override
	public boolean isSameType(Item o){
		return (o instanceof LightGrenade);
	}

    @NotNull
    @Override
    public ArrayList<Effect> getEffects() {
        ArrayList<Effect> effects = new ArrayList<>();
        if(getContainer().isSameType(new Square())){
            if(isActive())
                effects.add(new LightGrenadeEffect((Square) getContainer()));
        }else{
            effects.add(new EmptyEffect());
        }
        return effects;
    }

}