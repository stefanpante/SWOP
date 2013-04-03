/**
 * 
 */
package event;

import item.Teleport;
import game.Game;
import square.Square;

/**
 * @author Jonas Devlieghere
 *
 */
public class EnterSquareEvent extends AbstractGameEvent {

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

	@Override
	protected void beforeGameEvent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void afterGameEvent() {
		// TODO Auto-generated method stub
		
	}

}
