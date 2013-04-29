package effect.player;

import game.Player;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Jonas
 * Date: 29/04/13
 * Time: 12:30
 */
public class CompositePlayerEffect implements PlayerEffect {

    private ArrayList<PlayerEffect> childEffects = new ArrayList<PlayerEffect>();

    @Override
    public void affect(Player player) {
        for(PlayerEffect playerEffect : childEffects){
            playerEffect.affect(player);
        }
    }

    public void add(PlayerEffect playerEffect){
        childEffects.add(playerEffect);
    }

    public void remove(PlayerEffect playerEffect){
        childEffects.remove(playerEffect);
    }

}
