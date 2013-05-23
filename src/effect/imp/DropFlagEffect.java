package effect.imp;

import effect.Effect;
import game.Player;
import item.Flag;
import item.IdentityDisc;
import item.Item;
import square.Square;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Dieter
 * Date: 23/05/13
 * Time: 14:37
 * To change this template use File | Settings | File Templates.
 */
public abstract class DropFlagEffect extends Effect {
    Square centerSquare;

    /**
     * Creates a new  DropflagEffect with a square around which the flag will be dropped.
     *
     * @param   centerSquare
     *          The square of which its neighbors can contain the dropped flag.
     */
    public DropFlagEffect(Square centerSquare){
        this.centerSquare = centerSquare;
    }

    public Square getCenterSquare(){
        return centerSquare;
    }

    protected void dropFlag(Player player) {
        Item flag = null;
        for(Item i :  player.getAllItems()){
            if(i.isSameType(new Flag())){
                flag = i;
                break;
            }
        }
        if(flag != null){
            player.removeItem(flag);
            Random rnd = new Random();
            ArrayList<Square> neighbors = new ArrayList<>(getCenterSquare().getNeighbors().values());
            int randomIndex = rnd.nextInt(neighbors.size());
            Square newLocation = neighbors.get(randomIndex);
            newLocation.addItem(flag);
        }
    }
}
