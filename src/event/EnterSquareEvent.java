/**
 * 
 */
package event;

import item.Teleport;
import game.Game;
import player.Player;
import square.Square;

/**
 * @author jonas
 *
 */
public class EnterSquareEvent extends GameEvent {

	private Square square;
	
	public EnterSquareEvent(Game game, Square square) {
		super(game);
		this.square = square;
	}

	private Square getSquare(){
		return this.square;
	}

	@Override
	protected void duringGameEvent() {
		// Handle case where there's a teleport on the current square
		// FIXME: (!) Incorrect since not yet implemented (!)
		if(getSquare().getInventory().hasItem(new Teleport())){
			Teleport teleport = (Teleport) getSquare().getInventory().getItem(0);
			TeleportEvent teleportEvent = new TeleportEvent(getGame(), teleport);
			teleportEvent.run();
		}
	}

}
