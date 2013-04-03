/**
 * 
 */
package event;

import player.Player;
import game.Game;
import item.Teleport;
import square.Square;

/**
 * @author jonas
 *
 */
public class TeleportEvent extends AbstractGameEvent {
	
	private Teleport teleport;
	
	/**
	 * @param game
	 * @param args
	 */
	public TeleportEvent(Game game, Teleport teleport) {
		super(game);
		this.teleport = teleport;
	}
	
	private Teleport getTeleport(){
		return this.teleport;
	}

	@Override
	protected void beforeGameEvent() {
		Square destination = getGame().getGrid().getSquareWith(getTeleport().getDestination());
		if(!canTeleportTo(destination))
			throw new IllegalStateException("Cannot teleport to location containing another player.");
	}
	
	@Override
	protected void duringGameEvent() {
		Square destination = getGame().getGrid().getSquareWith(getTeleport().getDestination());
		getGame().getCurrentPlayer().setPosition(destination);
	}

	@Override
	protected void afterGameEvent() {
		// TODO Auto-generated method stub
	}
	
	private boolean canTeleportTo(Square destination){
		for(Player otherPlayer : getGame().getOtherPlayers()){
			if(otherPlayer.getPosition().equals(destination))
				return false;
		}
		return true;
	}
	
	
}
