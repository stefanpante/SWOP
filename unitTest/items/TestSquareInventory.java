package items;

import static org.junit.Assert.*;

import items.inventory.SquareInventory;

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
		Item it1 = new Item();
		Item it2 = new Item();
		sqinv1.addItem(it1);
		assertFalse(sqinv1.canHaveAsItem(it2));
	}
	
	@Test
	public void testCanHaveAsItemTwice(){
		SquareInventory sqinv1 = new SquareInventory(1);
		Item it1 = new Item();
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
	public void testAddActiveItem3(){
		SquareInventory sqinv = new SquareInventory();
		Item it = new Item();
		LightGrenade lg = new LightGrenade();
		it.activate();
		lg.activate();
		sqinv.addItem(it);
		sqinv.addItem(lg);
	}
	
	
	@Test
	public void testHasLightGrenade(){
		SquareInventory sqinv = new SquareInventory();
		Item it = new Item();
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
	
	@Test
	public void testActivate(){
		SquareInventory sqinv = new SquareInventory();
		LightGrenade lg = new LightGrenade();
		
		sqinv.addItem(lg);
		sqinv.activate(lg);
		assertTrue(lg.isActive());
	}
	
	@Test
	public void testHasActiveLightGrenade(){
		SquareInventory sqinv = new SquareInventory();
		LightGrenade lg = new LightGrenade();

		lg.activate();
		sqinv.addItem(lg);
		assertTrue(sqinv.hasActiveLightGrenade());
		sqinv.take(lg);
		assertFalse(sqinv.hasActiveLightGrenade());
		lg.deactivate();
		sqinv.addItem(lg);
		assertFalse(sqinv.hasActiveLightGrenade());
		sqinv.activate(lg);
		assertTrue(sqinv.hasActiveLightGrenade());		
	}
	
	@Test
	public void testGetItems(){
		SquareInventory sqinv = new SquareInventory();
		
		//Create and add inactive items
		Item it_inactive1 = new Item();
		sqinv.addItem(it_inactive1);
		Item it_inactive2 = new Item();
		sqinv.addItem(it_inactive2);
		LightGrenade it_inactive3 = new LightGrenade();
		sqinv.addItem(it_inactive3);

		
		//Create and add active items
		Item it_active1 = new Item();
		it_active1.activate();
		sqinv.addItem(it_active1);
		Item it_active2 = new Item();
		it_active2.activate();
		sqinv.addItem(it_active2);
		Item it_active3 = new Item();
		it_active3.activate();
		sqinv.addItem(it_active3);

		//Create and add worn out items
		Item it_worn1 = new Item();
		it_worn1.activate();
		it_worn1.wearOut();
		sqinv.addItem(it_worn1);
		Item it_worn2 = new Item();
		it_worn2.activate();
		it_worn2.wearOut();
		sqinv.addItem(it_worn2);
		Item it_worn3 = new Item();
		it_worn3.activate();
		it_worn3.wearOut();
		sqinv.addItem(it_worn3);
		
		
		ArrayList<Item> actives = sqinv.getItems(ItemState.ACTIVE);
		ArrayList<Item> inactives = sqinv.getItems(ItemState.INACTIVE);
		ArrayList<Item> wornouts = sqinv.getItems(ItemState.WORN);

		assertEquals(3, actives.size());
		
		assertTrue(actives.contains(it_active1));
		assertTrue(actives.contains(it_active2));
		assertTrue(actives.contains(it_active3));
		
		assertEquals(3, inactives.size());
		assertTrue(inactives.contains(it_inactive1));
		assertTrue(inactives.contains(it_inactive2));
		assertTrue(inactives.contains(it_inactive3));
		
		assertEquals(3, wornouts.size());
		assertTrue(wornouts.contains(it_worn1));
		assertTrue(wornouts.contains(it_worn2));
		assertTrue(wornouts.contains(it_worn3));
	}
	
}
