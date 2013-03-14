package scenariotests;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import player.Player;
import square.Direction;
import square.Square;

import game.Game;
import handlers.Handler;
import handlers.MoveHandler;
import handlers.PickUpHandler;
import handlers.UseItemHandler;

/**
 * Tests the interaction between two users in the game

 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class UserInteractionTest {


	private Game game;
	private ArrayList<Handler> handlers;
	private static int NUMBEROFTESTS = 100;

	@Before
	public void initialize(){
		game = new Game(10,10);
		handlers = new ArrayList<Handler>();
		handlers.add(new MoveHandler(game, null));
		handlers.add(new UseItemHandler(game, null));
		handlers.add(new PickUpHandler(game, null));
	}
	/**
	 * Test switch player after three randomActions
	 */
	// Rekening houden met powerfailure
	@Test
	public void testSwitch(){
		Random random = new Random();
		int length = handlers.size();
		int i = 0;
		int j = 0;
		while( i < NUMBEROFTESTS){
			Player currentPlayer = game.getCurrentPlayer();
			while(i < Player.MAX_ALLOWED_ACTIONS){

				Handler handler = handlers.get(random.nextInt(length));
				if(handler instanceof MoveHandler){
					MoveHandler mh = (MoveHandler) handler;
					Direction direction = getValidDirection();
					// Check op powerfailure, in dit geval zou de actie moeten eindigen
					// break;
					mh.move(direction);
				}
				if(handler instanceof PickUpHandler){
					PickUpHandler ph = (PickUpHandler) handler;
				}

				if(handler instanceof UseItemHandler){
					UseItemHandler uh = (UseItemHandler) handler;

				}
				j++;
			}
			j = 0;
			initialize();
		}
	}

	private Direction getValidDirection(){
		Square currentPosition = game.getCurrentPlayer().getPosition();

		Square next = null;
		Direction direction = null;
		while(next == null ||next.isObstructed() || next.getInventory().hasActiveLightGrenade()){
			direction = Direction.getRandom();
			try{
				next = game.getGrid().getNeighbor(currentPosition, direction);
			}
			catch(Exception e){}
		}

		return direction;
	}

}
