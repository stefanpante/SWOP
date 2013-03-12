package player;

import static org.junit.Assert.*;

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
	 * Given an invalid name, an exception must be thrown.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidName() {
		assertFalse(Player.isValidName(null));
		
		new Player(new Square(), null);
	}
	
	/**
	 * Given an invalid square, an exception must be thrown.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidSquare() {
		assertFalse(Player.isValidStartPosition(null));
		
		new Player(null, new String("John"));
	}
	
	/**
	 * A square which is obstructed cannot be valid.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testObstructedSquare() {
		Square square = new Square();
		new Wall(new Square(), square);
		
		new Player(square, new String("Johnny"));
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
		Player player = new Player(new Square(), new String("John"));
		
		player.pickUp(null);
	}

}
