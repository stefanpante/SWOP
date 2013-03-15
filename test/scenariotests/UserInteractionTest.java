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
import square.state.PowerFailureState;
import square.state.State;

import game.Game;
import grid.GridBuilder;
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
	private static int NUMBEROFTESTS = 1;
	private MoveHandler mh;

	@Before
	public void initialize(){
		game = new Game(10,10);
		game.clearPowerFailures();
		handlers = new ArrayList<Handler>();
		mh = new MoveHandler(game, null);
		handlers.add(mh);
		handlers.add(new UseItemHandler(game, null));
		handlers.add(new PickUpHandler(game, null));
	}
	/**
	 * Test switch player after three randomActions
	 * TODO: rekening houden met dat turns worden overgeslagen
	 */
	@Test
	public void testSwitch(){

		Random random = new Random();
		int length = handlers.size();
		int i = 0;
		int j = 0;

		while( i < NUMBEROFTESTS){

			Player currentPlayer = game.getCurrentPlayer();
			int numberOfActions = Player.MAX_ALLOWED_ACTIONS;
			j = 1;

			// Always one move 
			Direction direction = getValidDirection();
			Square currentPosition = game.getCurrentPlayer().getPosition();
			Square next = game.getGrid().getNeighbor(currentPosition, direction);
			next.getInventory().wearOut();
			mh.move(direction);
			while((j <= numberOfActions)){

				Handler handler = handlers.get(random.nextInt(length));

				if(handler instanceof MoveHandler){

					MoveHandler mh2 = (MoveHandler) handler;
					direction = getValidDirection();

					currentPosition = game.getCurrentPlayer().getPosition();
					next = game.getGrid().getNeighbor(currentPosition, direction);
					next.getInventory().wearOut();
					mh2.move(direction);
					assertEquals(currentPlayer, game.getCurrentPlayer());

				}

				if(handler instanceof PickUpHandler){
					PickUpHandler ph = (PickUpHandler) handler;
					Item item = placeItem();
					ph.pickUp(item);
					assertEquals(currentPlayer, game.getCurrentPlayer());
		
				}

				if(handler instanceof UseItemHandler){
					UseItemHandler uh = (UseItemHandler) handler;
					Item item = placeItemInPlayer();
					uh.useItem(item);
					assertEquals(currentPlayer, game.getCurrentPlayer());
	

				}
				j++;

			}
			assertFalse(game.getCurrentPlayer().equals(currentPlayer));
			j = 0;
			initialize();
			i++;
		}
	}

	private Direction getValidDirection(){
		Square currentPosition = game.getCurrentPlayer().getPosition();

		Square next = null;
		Direction direction = null;
		while(next == null ||next.isObstructed() 
				|| next.getInventory().hasActiveLightGrenade()
				|| !game.getGrid().canMoveTo(currentPosition, direction)){
			direction = Direction.getRandomDirection();
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
		Square current = player.getPosition();
		if(current.getInventory().hasLightGrenade()){
			LightGrenade lg = current.getInventory().getLightGrenade();
			current.getInventory().take(lg);

		}
		player.getInventory().addItem(item);


		return item;
	}

}
