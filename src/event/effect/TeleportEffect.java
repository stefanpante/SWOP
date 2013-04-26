/**
 * 
 */
package event.effect;

import game.Game;
import game.Player;
import item.Teleport;
import square.Square;

/**
 * The event where a Player teleports from one square 
 * to the other.
 * 
 * @author jonas
 */
public class TeleportEffect extends Effect {
	
	private Teleport teleport;
	
	public TeleportEffect(Game game, Teleport teleport) {
		super(game);
		this.teleport = teleport;
	}
	
	private Teleport getTeleport(){
		return this.teleport;
	}

	@Override
	protected void beforeGameCommand() {
		Square destination = getGame().getGrid().getSquareWith(getTeleport().getDestination());
		if(!canTeleportTo(destination))
			throw new IllegalStateException("Cannot teleport to location containing another player.");
	}
	
	@Override
	protected void duringGameCommand() {
		Square destination = getGame().getGrid().getSquareWith(getTeleport().getDestination());
		getGame().getCurrentPlayer().setPosition(destination);
	}
	
	private boolean canTeleportTo(Square destination){
		for(Player otherPlayer : getGame().getOtherPlayers()){
			if(otherPlayer.getPosition().equals(destination))
				return false;
		}
		return true;
	}

	@Override
	protected void afterGameCommand() {
		// Nothing to do here
	}
}
