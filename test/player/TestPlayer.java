package player;

import static org.junit.Assert.*;
import items.Item;
import items.LightGrenade;
import items.PlayerInventory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import square.Square;
import square.obstacles.Wall;

/**
 * Test classes for player.
 * 
 * @author vincentreniers
 */
public class TestPlayer {

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
	
	
	/**
	 * Given an invalid square, an exception must be thrown.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidSquare() {
		assertFalse(Player.isValidStartPosition(null));
		
		new Player(null, 0);
	}
	
	/**
	 * A square which is obstructed cannot be valid.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testObstructedSquare() {
		Square square = new Square();
		new Wall(new Square(), square);
		
		new Player(square, 0);
	}
	
	/**
	 * Checks if remaining actions more than the maximum allowed is not valid.
	 */
	@Test
	public void testValidRemainingActions() {
		assertFalse(Player.isValidRemainingActions(Player.MAX_ALLOWED_ACTIONS + 1));
	}
	
	/**
	 * Checks if picking up a item which is null results in exception.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidPickUp() {
		Player player = new Player(new Square(), 0);
		
		player.pickUp(null);
	}
	
	/**
	 * Checks if picking up an item that is null results in an exception.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testPickUpNull() {
		Player player = new Player(new Square(), 0);
		player.pickUp(null);
	}
	
	/**
	 * Test if picking up the item actually adds it to the player inventory.
	 */
	@Test
	public void testPickUpItem() {
		Square square = new Square();
		Item item = new LightGrenade();
		
		square.getInventory().addItem(item);
		
		Player player = new Player(square, 0);
		assertTrue(player.isValidPickUp(item));
		assertEquals(player.getInventory().getSize(), 0);
		assertFalse(player.getInventory().hasItem(item));
		
		player.pickUp(item);
		assertTrue(player.getInventory().hasItem(item));
		assertFalse(player.isValidPickUp(item));
	}
	
	/**
	 * Test if inventory which is null is invalid.
	 */
	@Test
	public void testIsValidInventory() {
		assertFalse(Player.isValidInventory(null));
		
		Player player = new Player(new Square(), 0);
		PlayerInventory inventory = new PlayerInventory();
		
		assertTrue(Player.isValidInventory(inventory));
		player.setInventory(inventory);
	}
	
	/**
	 * Test if setting an inventory which is null, results in exception.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIsValidInventoryNull() {
		Player player = new Player(new Square(), 0);
		player.setInventory(null);
	}
	
	/**
	 * Test if using an item that is null results in exception.
	 */
	@Test(expected=IllegalStateException.class)
	public void testUseItem() {
		Player player = new Player(new Square(), 0);
		
		player.useItem(null);
	}
	
	/**
	 * Test if using an item which is not contained in the inventory
	 * results in an exception.
	 */
	@Test(expected=IllegalStateException.class)
	public void testUseItemNotContained() {
		Player player = new Player(new Square(), 0);
		
		player.useItem(new LightGrenade());
	}
	
	/**
	 * Test using a valid item.
	 */
	@Test
	public void testUseValidItem() {
		LightGrenade lightGrenade = new LightGrenade();
		Square square = new Square();
		
		Player player = new Player(square, 0);
		player.getInventory().addItem(lightGrenade);
		
		player.useItem(lightGrenade);
		assertFalse(player.getInventory().hasItem(lightGrenade));
	}
	
	/**
	 * Test moving to a square which is obstructed
	 * must result in an exception.
	 */
	@Test(expected=IllegalStateException.class)
	public void testMoveObstructed() {
		Square square = new Square();
		new Wall(new Square(), square);
		
		Player player = new Player(new Square(), 0);
		assertFalse(Player.isValidMove(square));
		assertFalse(Player.isValidMove(null));
		
		assertFalse(player.hasMoved());
		player.move(square);
	}
	
	/**
	 * Test move to a valid square.
	 */
	@Test
	public void testMove() {
		Square square = new Square();
		Player player = new Player(new Square(), 0);
		
		assertTrue(Player.isValidMove(square));
		assertFalse(player.hasMoved());
		
		player.move(square);
		assertTrue(player.hasMoved());
	}
	
	/**
	 * Test move, the square the player is moving to must become
	 * an obstacle. And the square moving from must become non-obstructed.
	 */
	@Test
	public void testMoveObstacle() {
		Square square = new Square();
		Square square2 = new Square();
		Player player = new Player(square, 0);
		
		assertTrue(square.isObstructed());
		assertFalse(square2.isObstructed());
		assertEquals(square.getObstacle(), player);
		
		player.move(square2);
		
		assertFalse(square.isObstructed());
		assertTrue(square2.isObstructed());
		assertEquals(square2.getObstacle(), player);
	}

}
