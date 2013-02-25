package handlers;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringWriter;

import items.Inventory;
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
	public void testUseItem() {
		Game game = new Game();
		game.setCurrentPlayer(game.getPlayer1());
		Inventory inv = new Inventory(6);
		inv.addItem(new LightGrenade());
		game.getPlayer1().setInventory(inv);
		UseItemHandler uh = new UseItemHandler(game);
		uh.useItem();
	}

}
