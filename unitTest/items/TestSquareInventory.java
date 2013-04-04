package items;

import static org.junit.Assert.*;

import item.Item;
import item.ItemState;
import item.LightGrenade;
import item.Teleport;
import item.inventory.SquareInventory;
import item.launchable.IdentityDisc;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSquareInventory {

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
	public void testConstructor(){
		SquareInventory sqinv1 = new SquareInventory(17);
		assertEquals(17, sqinv1.getMaximumSize());
		
		SquareInventory sqinv2 = new SquareInventory(1615);
		assertEquals(1615, sqinv2.getMaximumSize());
	}
	
	@Test
	public void testCanHaveAsItemFull(){
		SquareInventory sqinv1 = new SquareInventory(1);
		Item it1 = new Teleport();
		Item it2 = new IdentityDisc();
		sqinv1.addItem(it1);
		assertFalse(sqinv1.canHaveAsItem(it2));
	}
	
	@Test
	public void testCanHaveAsItemTwice(){
		SquareInventory sqinv1 = new SquareInventory(1);
		Item it1 = new IdentityDisc();
		sqinv1.addItem(it1);
		assertFalse(sqinv1.canHaveAsItem(it1));
	}
	
	@Test
	public void testAddItemLightGrenade() {
		SquareInventory sqinv = new SquareInventory();
		LightGrenade lg = new LightGrenade();
		sqinv.addItem(lg);
		assertTrue(sqinv.hasLightGrenade());
		assertTrue(lg.equals(sqinv.getLightGrenade()));
		
		
		LightGrenade lg2 = new LightGrenade();
		try {
			sqinv.addItem(lg2);
			fail("You shouldn't be able to add another lightGrenade");
		} catch (Exception e) {
			assert(true);
		}

	}
	
	@Test(expected = IllegalStateException.class)
	public void testAddItemNull() {
		SquareInventory sqinv = new SquareInventory();
		sqinv.addItem(null);
	}

	@Test
	public void testTakeLightGrenade() {
		SquareInventory sqinv = new SquareInventory();
		LightGrenade lg = new LightGrenade();
		sqinv.addItem(lg);
		assertTrue(sqinv.hasLightGrenade());
		sqinv.take(lg);
		assertFalse(sqinv.hasLightGrenade());
		assertFalse(sqinv.hasItem(lg));		
		LightGrenade lg2 = new LightGrenade();
		try {
			sqinv.take(lg2);
			fail("You shouldn't be able to remove a non added lightGrenade");
		} catch (Exception e) {
			assert(true);
		}
	}
	
	
	@Test
	public void testHasLightGrenade(){
		SquareInventory sqinv = new SquareInventory();
		Item it = new IdentityDisc();
		LightGrenade lg = new LightGrenade();
		
		assertFalse(sqinv.hasLightGrenade());
		sqinv.addItem(it);
		assertFalse(sqinv.hasLightGrenade());
		sqinv.addItem(lg);
		assertTrue(sqinv.hasLightGrenade());
		sqinv.take(it);
		assertTrue(sqinv.hasLightGrenade());
		sqinv.take(lg);
		assertFalse(sqinv.hasLightGrenade());
	}
}
