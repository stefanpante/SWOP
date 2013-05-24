package square;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


import item.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



public class TestSquare {

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
	public void testAddItem(){
		Square sq = new Square();
		Item lg1 = new LightGrenade();
		Item it1 = new Teleport();
		Item it2 = new IdentityDisc();
		Item it3 = new IdentityDisc();


		sq.addItem(lg1);
		assertTrue(sq.hasItem(lg1));
		assertTrue(sq.getAllItems().size() == 1);
		
		sq.addItem(it1);
		assertTrue(sq.hasItem(lg1));
		assertTrue(sq.hasItem(it1));
		assertTrue(sq.getAllItems().size() == 2);
		
		sq.addItem(it2);
		assertTrue(sq.hasItem(lg1));
		assertTrue(sq.hasItem(it1));
		assertTrue(sq.hasItem(it2));
		assertTrue(sq.getAllItems().size() == 3);

		sq.addItem(it3);
		assertTrue(sq.hasItem(lg1));
		assertTrue(sq.hasItem(it1));
		assertTrue(sq.hasItem(it2));
		assertTrue(sq.hasItem(it3));
		assertTrue(sq.getAllItems().size() == 4);
	}
		
	@Test
	public void testGetItems() {
		Square sq = new Square();

		Item lg1 = new LightGrenade();
		Item it1 = new IdentityDisc();
		Item it2 = new IdentityDisc();
		Item it3 = new IdentityDisc();

		sq.addItem(lg1);
		sq.addItem(it1);
		sq.addItem(it2);
		sq.addItem(it3);

		assertTrue(sq.getAllItems().size() == 4);
		assertTrue(sq.hasItem(lg1));
		assertTrue(sq.hasItem(it1));
		assertTrue(sq.hasItem(it2));
		assertTrue(sq.hasItem(it3));

	}

	@Test
	public void testHasItem() {
		Square sq1 = new Square();
		Square sq2 = new Square();
		Square sq3 = new Square();

		Item lg1 = new LightGrenade();
		Item lg2 = new LightGrenade();
		Item lg3 = new LightGrenade();
		Item it = new IdentityDisc();
		
		sq1.addItem(lg1);
		assertTrue(sq1.hasItem((lg1)));
		
		
		sq2.addItem(lg2);
		assertTrue(sq2.hasItem(lg2));
		
		sq3.addItem(lg3);
		sq3.addItem(it);
		assertTrue(sq3.hasItem(lg3));
		assertTrue(sq3.hasItem(it));
	}
	
	/************************************
	 *  Neighbor tests
	 ************************************/

	
	@Test
	public void testIsObstructed() {
		Square square = new Square();
		assertFalse(square.isObstacle());
	}

    @Test(expected = IllegalArgumentException.class)
    public void testCanHaveAsItemTwice(){
        Square sq = new Square();
        Item it1 = new IdentityDisc();
        sq.addItem(it1);
        //Should give exception.
        sq.addItem(it1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddItemLightGrenade() {
        Square sq = new Square();
        LightGrenade lg = new LightGrenade();
        sq.addItem(lg);
        assertTrue(sq.hasType(new LightGrenade()));
        assertTrue(lg.equals(sq.filterItemsByType(new LightGrenade()).get(0)));
        LightGrenade lg2 = new LightGrenade();
        sq.addItem(lg2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddItemNull() {
        Square sq = new Square();
        sq.addItem(null);
    }



    @Test(expected = IllegalArgumentException.class)
    public void testTakeLightGrenade() {
        Square sq = new Square();
        LightGrenade lg = new LightGrenade();
        sq.addItem(lg);
        assertTrue(sq.hasType(new LightGrenade()));
        sq.removeItem(lg);
        assertFalse(sq.hasType(new LightGrenade()));
        assertFalse(sq.hasItem(lg));
        LightGrenade lg2 = new LightGrenade();
        sq.removeItem(lg2);
    }



    @Test
    public void testHasLightGrenade(){
        Square sq = new Square();
        Item it = new IdentityDisc();
        LightGrenade lg = new LightGrenade();

        assertFalse(sq.hasType(new LightGrenade()));
        sq.addItem(it);
        assertFalse(sq.hasType(new LightGrenade()));
        sq.addItem(lg);
        assertTrue(sq.hasType(new LightGrenade()));
        sq.removeItem(it);
        assertTrue(sq.hasType(new LightGrenade()));
        sq.removeItem(lg);
        assertFalse(sq.hasType(new LightGrenade()));
    }

    @Test
    public void testAddLaunchable(){
        Square sq = new Square();
        Item it = new IdentityDisc();
        sq.addItem(it);
        assertTrue(sq.hasType(new IdentityDisc()));
        Item it2 = new ChargedIdentityDisc();
        sq.addItem(it2);
        assertTrue(sq.hasType(new IdentityDisc()));
        sq.removeItem(it);
        assertTrue(sq.hasType(new IdentityDisc()));
    }


    @Test
    public void testAddFlag(){
        Square sq = new Square();
        Item it = new Flag();
        sq.addItem(it);
        assertTrue(sq.hasItem(it));
    }
}
