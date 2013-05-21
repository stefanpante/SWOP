package effect;

import command.AbstractEffectCommand;
import game.Player;
import item.Flag;
import item.Item;
import item.inter.Movable;
import square.Square;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Dieter
 * Date: 21/05/13
 * Time: 15:57
 * To change this template use File | Settings | File Templates.
 */
public class DropFlagCommand extends AbstractEffectCommand{

    /**
     * Construct a Game Command
     *
     * @param movable The game of this command
     */
    public DropFlagCommand(Player movable) {
        super(movable);
    }

    @Override
    public void execute() throws Exception {
        Item flag = null;
        //We are sure that movable is a Player for this command.
        Player player = (Player)getMovable();
        for(Item i :  player.getAllItems()){
            if(i.isSameType(new Flag())){
                flag = i;
                break;
            }
        }
        if(flag == null){
            throw new Exception("No flag to drop.");
        }
        player.removeItem(flag);
        Random rnd = new Random();
        ArrayList<Square> neighbors = new ArrayList<Square>(player.getPosition().getNeighbors().values());
        int randomIndex = rnd.nextInt(neighbors.size());
        Square newLocation = neighbors.get(randomIndex);
        newLocation.addItem(flag);
    }
}
