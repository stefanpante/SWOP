package item;

import effect.Effect;
import org.jetbrains.annotations.NotNull;
import square.Square;

import java.util.ArrayList;


/**
 * The ForceFieldGenerator generates a line segment between
 * another force field generator if it is within an appropriate distance
 * 
 * The Generator holds a reference to the line segment.
 * This line segment switches on and off.
 * 
 * @author Dieter Castel, Jonas Devlieghere and Stefan Pante
 */
public class ForceFieldGenerator extends Item{

    @Override
    public boolean canAddTo(@NotNull Square square) {
        return !square.hasItem(this);
    }

	@Override
	public boolean isSameType(Item item) {
        return item instanceof ForceFieldGenerator;
    }

    @NotNull
    @Override
    public ArrayList<Effect> getEffects() {
        return new ArrayList<>();
    }

    public boolean isActive() {
        return false;
    }


    @NotNull
    @Override
    public String toString() {
        return super.toString() + "ForceFieldGenerator";
    }

   
	
}
