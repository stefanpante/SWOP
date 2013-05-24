package item;

import effect.Effect;
import effect.imp.EmptyEffect;
import effect.imp.ForceFieldEffect;
import effect.imp.ForceFieldStuckEffect;
import item.inter.Movable;
import game.Player;
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

    private boolean active;

    @Override
    public boolean canAddTo(Square square) {
        return !square.hasItem(this);
    }

	@Override
	public boolean isSameType(Item item) {
        return item instanceof ForceFieldGenerator;
    }

    @Override
    public ArrayList<Effect> getEffects() {
        ArrayList<Effect> effects = new ArrayList<>();
        return effects;
    }

    public boolean isActive() {
        return this.active;
    }

    public void activate(){
        this.active = true;
    }

    @Override
    public String toString() {
        return super.toString() + "ForceFieldGenerator";
    }

   
	
}
