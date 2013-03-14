package scenariotests;

import static org.junit.Assert.*;
import items.Item;
import items.LightGrenade;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import player.Player;
import square.Direction;
import square.Square;
import square.state.PowerFailure;
import square.state.State;

import game.Game;
import gui.ApplicationWindow;
import handlers.GameHandler;
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
	private MoveHandler mh;
	private GameHandler gh;

	@Before
	public void initialize(){
		game = new Game(10,10);
		handlers = new ArrayList<Handler>();
		mh = new MoveHandler(game, null);
		handlers.add(mh);
		handlers.add(new UseItemHandler(game, null));
		handlers.add(new PickUpHandler(game, null));
	}
	/**
	 * Test switch player after three randomActions
	 */
	@Test
	public void testSwitch(){
		Random random = new Random();
		int length = handlers.size();
		int i = 0;
		int j = 0;
		while( i < NUMBEROFTESTS){
			initialize();
			Player currentPlayer = game.getCurrentPlayer();
			State state = game.getCurrentPlayer().getPosition().getState();
			int numberOfActions = Player.MAX_ALLOWED_ACTIONS;
			if(state instanceof PowerFailure){
				numberOfActions -= 1;
			}
			j = 1;
			Direction direction = getValidDirection();
			Square currentPosition = game.getCurrentPlayer().getPosition();
			Square next = game.getGrid().getNeighbor(currentPosition, direction);
			if(mh == null) System.out.println("null");
			mh.move(direction);
			if(next.getState() instanceof PowerFailure){
				j = 3;
			}
			
			while(j < numberOfActions){
				
				Handler handler = handlers.get(random.nextInt(length));
				if(handler instanceof MoveHandler){
					MoveHandler mh2 = (MoveHandler) handler;
					direction = getValidDirection();
					currentPosition = game.getCurrentPlayer().getPosition();
					next = game.getGrid().getNeighbor(currentPosition, direction);
					mh2.move(direction);
					if(next.getState() instanceof PowerFailure){
						break;
					}
					
				}
				
				if(handler instanceof PickUpHandler){
					PickUpHandler ph = (PickUpHandler) handler;
					Item item = placeItem();
					ph.pickUp(item);
				}

				if(handler instanceof UseItemHandler){
					UseItemHandler uh = (UseItemHandler) handler;
					Item item = placeItemInPlayer();
					uh.useItem(item);

				}
				
				j++;
			}
			assertFalse(game.getCurrentPlayer().equals(currentPlayer));
			j = 0;
			
			i++;
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
	
	private Item placeItem(){
			Item item = new LightGrenade();
			
			Square currentPosition = game.getCurrentPlayer().getPosition();
			try{
				currentPosition.getInventory().addItem(item);
			}
			catch(Exception e){
				item = currentPosition.getInventory().getLightGrenade();
			}
			return item;
	}
	
	private Item placeItemInPlayer(){
		Item item = new LightGrenade();
		Player player = game.getCurrentPlayer();
		player.getInventory().addItem(item);
		return item;
	}

}
