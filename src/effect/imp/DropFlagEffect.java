package effect.imp;

import effect.Effect;
import game.Player;
import item.Flag;
import item.Item;
import square.GridElement;
import square.Square;

import java.util.ArrayList;
import java.util.Random;

/**
 * User: Dieter
 * Date: 23/05/13
 * Time: 14:37
 */
abstract class DropFlagEffect extends Effect {
    private final Square centerSquare;

    /**
     * Creates a new  DropflagEffect with a square around which the flag will be dropped.
     *
     * @param   centerSquare
     *          The square of which its neighbors can contain the dropped flag.
     */
    DropFlagEffect(Square centerSquare){
        this.centerSquare = centerSquare;
    }

    Square getCenterSquare(){
        return centerSquare;
    }

    void dropFlag(Player player) {
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
            ArrayList<GridElement> neighbors = new ArrayList<>(getCenterSquare().getNeighbors().values());
            int randomIndex = rnd.nextInt(neighbors.size());
            
            GridElement newLocation;
            while(true){
            	newLocation = neighbors.get(randomIndex);
            	if(newLocation.isSameType(new Square())){
            		((Square) newLocation).addItem(flag);
            		break;
            	}
            }
            
        }
    }

    @Override
    public String toString() {
        return "DropFlagEffect";
    }
}
