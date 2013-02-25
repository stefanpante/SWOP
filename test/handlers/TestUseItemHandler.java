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
	public void testUseItemHandler() {
		fail("Not yet implemented");
	}

	@Test
	public void testUseItem1() {
		//Succesfully use an item in casu: add a LightGrenade to a square.
		Game game = new Game();
		game.setCurrentPlayer(game.getPlayer1());
		int al = game.getPlayer1().getActionsLeft();
		Inventory inv = new Inventory(6);
		Item lg = new LightGrenade();
		inv.addItem(new LightGrenade());
		game.getPlayer1().setInventory(inv);
		UseItemHandler uh = new UseItemHandler(game);
		//Use the light grenade.
		uh.useItem(lg);
		assertTrue(game.getPlayer1().getPosition().getUsedItems().contains(lg));
		assertFalse(game.getPlayer1().getInventory().hasItem(lg));
		assertFalse(lg.isActive());
		assertEquals(al-1 , game.getPlayer1().getActionsLeft());
	}

	@Test
	public void testUseItem1() {
		//unsuccesfully add an item to a square.
		Game game = new Game();
		game.setCurrentPlayer(game.getPlayer1());
		Inventory inv = new Inventory(6);
		Item lg = new LightGrenade();
// "FORGET" to add the item to the inventory of the player.
//		inv.addItem(new LightGrenade());
		game.getPlayer1().setInventory(inv);
		UseItemHandler uh = new UseItemHandler(game);
		//TODO: Check for failure? 
		/*
		 * Does the player lose his turn?
		 * Where is the exception handled? At player-,game- or Handler-level? 
		 */
		uh.useItem(lg);
		
		assertFalse(game.getPlayer1().getPosition().getUsedItems().contains(lg));
		assertFalse(lg.isActive());
		
	}

}
