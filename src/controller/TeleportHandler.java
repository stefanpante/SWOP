/**
 * 
 */
package controller;

import game.Game;
import item.Teleport;
import player.Player;
import square.Square;

/**
 * @author jonas
 *
 */
public class TeleportHandler extends Handler {
	
	public TeleportHandler(){
		super();
	}
	
	public TeleportHandler(Game game) {
		super(game);
	}
	
	public void Teleport(Teleport teleport) throws IllegalStateException {
		startAction();
		Square destination = getGame().getGrid().getSquareWith(teleport.getDestination());
		if(!canTeleportTo(destination))
			throw new IllegalStateException("Cannot teleport to location containing another player.");
		
		endAction();
	}
	
	public boolean canTeleportTo(Square destination){
		for(Player otherPlayer : getGame().getOtherPlayers()){
			if(otherPlayer.getPosition().equals(destination))
				return false;
		}
		return true;
	}
}
