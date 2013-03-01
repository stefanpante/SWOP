package handlers;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringWriter;

import items.Inventory;
import items.Item;
import items.LightGrenade;
import game.Game;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import player.Player;

import square.Square;

public class TestUseItemHandler {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCheckToProceed(){
		Game game = new Game(10,10);
		UseItemHandler handler = new UseItemHandler(game);
		assertTrue(handler.checkToProceed());
		for(int i = 0; i < Player.MAX_ALLOWED_ACTIONS; i++){
			game.getCurrentPlayer().incrementActions();
		}
		assertFalse(handler.checkToProceed());
	}

	@Test
	public void testUseItem(){
		Game game = new Game(10,10);
		UseItemHandler handler = new UseItemHandler(game);
		
		Item item = new LightGrenade();
		handler.useItem(item);
		assertTrue(game.getCurrentPlayer().getPosition().getUsedItems().contains(item));
		
	}
	

}
