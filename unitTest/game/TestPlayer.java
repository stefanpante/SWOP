package game;

import item.*;
import org.junit.*;
import square.Square;

import static org.junit.Assert.*;

/**
 * Test classes for player.
 * 
 * @author Dieter Castel, Jonas Devlieghere and Stefan Pante
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
		
		square.addItem(item);
		
		Player player = new Player(square, 0);
		assertEquals(0, player.getAllItems().size());
		assertFalse(player.hasItem(item));
		
		player.pickUp(item);
		assertTrue(player.hasItem(item));
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
	@Test(expected=IllegalArgumentException.class)
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
		player.addItem(lightGrenade);
		
		player.useItem(lightGrenade);
		assertFalse(player.hasItem(lightGrenade));
	}
	

	/**
	 * Test move to a valid square.
	 */
	@Test
	public void testMove() {
		Square square = new Square();
		Player player = new Player(new Square(), 0);
		
		assertFalse(player.hasMoved());
		
		player.move(square);
		assertTrue(player.hasMoved());
	}
	

    @Test
    public void testPlayerInventoryMaximumSize() {
        Player p1 = new Player(new Square(),1);
        assertEquals(6, p1.getMaxItems());
    }

    @Test
    public void testPlayerInventorySize() {
        Player p1 = new Player(new Square(),1);

        LightGrenade lg1 = new LightGrenade();
        LightGrenade lg2 = new LightGrenade();
        LightGrenade lg3 = new LightGrenade();

        Item it1 = new IdentityDisc();
        Item it2 = new ChargedIdentityDisc();
        Item it3 = new IdentityDisc();

        assertEquals(0, p1.getAllItems().size());
        p1.addItem(it1);
        assertEquals(1, p1.getAllItems().size());
        p1.addItem(lg1);
        assertEquals(2, p1.getAllItems().size());
        p1.addItem(it2);
        assertEquals(3, p1.getAllItems().size());
        p1.addItem(lg2);
        assertEquals(4, p1.getAllItems().size());
        p1.addItem(it3);
        assertEquals(5, p1.getAllItems().size());
        p1.addItem(lg3);
        assertEquals(6, p1.getAllItems().size());
    }





    @Test(expected = IllegalArgumentException.class)
    public void testPlayerInventorySize2() {
        Player p1 = new Player(new Square(),1);

        LightGrenade lg1 = new LightGrenade();
        LightGrenade lg2 = new LightGrenade();
        LightGrenade lg3 = new LightGrenade();

        Item it1 = new IdentityDisc();
        Item it2 = new ChargedIdentityDisc();
        Item it3 = new IdentityDisc();
        Item it4 = new IdentityDisc();

        assertEquals(0, p1.getAllItems().size());
        p1.addItem(it1);
        assertEquals(1, p1.getAllItems().size());
        p1.addItem(lg1);
        assertEquals(2, p1.getAllItems().size());
        p1.addItem(it2);
        assertEquals(3, p1.getAllItems().size());
        p1.addItem(lg2);
        assertEquals(4, p1.getAllItems().size());
        p1.addItem(it3);
        assertEquals(5, p1.getAllItems().size());
        p1.addItem(lg3);
        assertEquals(6, p1.getAllItems().size());

        p1.addItem(it4);
        //Should throw exception cause limit is 6.
    }

    @Test(expected=IllegalArgumentException.class)
    public void testAddTeleport(){
        Player p1 = new Player(new Square(),1);
        p1.addItem(new Teleport());
    }

    @Test
    public void testAddFlag(){
        Player p1 = new Player(new Square(),1);
        Flag flag = new Flag();
        p1.addItem(flag);
        assertTrue(p1.hasItem(flag));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testAddMoreFlags(){
        Player p1 = new Player(new Square(),1);
        Flag flag = new Flag();
        p1.addItem(flag);
        assertTrue(p1.hasItem(flag));

        p1.addItem(new Flag());
    }
}
