package items;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Categories.ExcludeCategory;

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
		SquareInventory sqinv1 = new SquareInventory(false);
		assertFalse(sqinv1.isOnlyInactiveItems());
		
		SquareInventory sqinv2 = new SquareInventory(true);
		assertTrue(sqinv2.isOnlyInactiveItems());
	}
	
	@Test 
	public void testConstructor2Args(){
		SquareInventory sqinv1 = new SquareInventory(17, false);
		assertEquals(17, sqinv1.getMaximumSize());
		assertFalse(sqinv1.isOnlyInactiveItems());
		
		SquareInventory sqinv2 = new SquareInventory(1615, true);
		assertEquals(1615, sqinv2.getMaximumSize());
		assertTrue(sqinv2.isOnlyInactiveItems());
	}
	
	@Test
	public void testCanHaveAsItemFull(){
		SquareInventory sqinv1 = new SquareInventory(1, false);
		Item it1 = new Item();
		Item it2 = new Item();
		sqinv1.addItem(it1);
		assertFalse(sqinv1.canHaveAsItem(it2));
	}
	
	@Test
	public void testCanHaveAsItemTwice(){
		SquareInventory sqinv1 = new SquareInventory(1, false);
		Item it1 = new Item();
		sqinv1.addItem(it1);
		assertFalse(sqinv1.canHaveAsItem(it1));
	}
	
	@Test
	public void testAddItemLightGrenade() {
		SquareInventory sqinv = new SquareInventory(false);
		LightGrenade lg = new LightGrenade();
		sqinv.addItem(lg);
		assertTrue(sqinv.hasLightGrenade());
		
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
		SquareInventory sqinv = new SquareInventory(false);
		sqinv.addItem(null);
	}

	@Test
	public void testTakeLightGrenade() {
		SquareInventory sqinv = new SquareInventory(false);
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

	@Test(expected=IllegalStateException.class)
	public void testAddActiveItem(){
		SquareInventory sqinv = new SquareInventory(true);
		LightGrenade lg = new LightGrenade();
		lg.activate();
		sqinv.addItem(lg);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testAddActiveItem2(){
		SquareInventory sqinv = new SquareInventory(true);
		Item it = new Item();
		it.activate();
		sqinv.addItem(it);
	}
	
	@Test
	public void testAddActiveItem3(){
		SquareInventory sqinv = new SquareInventory(false);
		Item it = new Item();
		LightGrenade lg = new LightGrenade();
		it.activate();
		lg.activate();
		sqinv.addItem(it);
		sqinv.addItem(lg);
	}
	
	
	@Test
	public void testHasLightGrenade(){
		SquareInventory sqinv = new SquareInventory(false);
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
		SquareInventory sqinvFalse = new SquareInventory(false);
		LightGrenade lg = new LightGrenade();
		
		sqinvFalse.addItem(lg);
		sqinvFalse.activate(lg);
		assertTrue(lg.isActive());
	}
	
	
	@Test(expected=IllegalStateException.class)
	public void testActivate2(){
		SquareInventory sqinvFalse = new SquareInventory(true);
		LightGrenade lg = new LightGrenade();
		
		sqinvFalse.addItem(lg);
		sqinvFalse.activate(lg);
		assertFalse(lg.isActive());
	}
	
	@Test(expected=IllegalStateException.class)
	public void testActivate3(){
		SquareInventory sqinvFalse = new SquareInventory(false);
		LightGrenade lg = new LightGrenade();
		
		sqinvFalse.activate(lg);
		assertFalse(lg.isActive());
	}
	
	@Test
	public void testHasActiveLightGrenade(){
		SquareInventory sqinvFalse = new SquareInventory(false);
		SquareInventory sqinvTrue = new SquareInventory(true);
		LightGrenade lg = new LightGrenade();

		sqinvTrue.addItem(new LightGrenade());
		assertFalse(sqinvTrue.hasActiveLightGrenade());

		lg.activate();
		sqinvFalse.addItem(lg);
		assertTrue(sqinvFalse.hasActiveLightGrenade());
		sqinvFalse.take(lg);
		assertFalse(sqinvFalse.hasActiveLightGrenade());
		lg.deactivate();
		sqinvFalse.addItem(lg);
		assertFalse(sqinvFalse.hasActiveLightGrenade());
		sqinvFalse.activate(lg);
		assertTrue(sqinvFalse.hasActiveLightGrenade());		
	}
	
	@Test
	public void testActivateAllItems(){
		SquareInventory sqinvFalse = new SquareInventory(false);
		LightGrenade inactive_lg = new LightGrenade();

		Item active_i1 = new Item();
		active_i1.activate();
		Item active_i2 = new Item();
		active_i2.activate();

		Item inactive_i1 = new Item();
		Item inactive_i2 = new Item();
		
		Item wornItem = new Item();
		wornItem.activate();
		wornItem.wearOut();
		
		sqinvFalse.addItem(inactive_lg);
		sqinvFalse.addItem(active_i1);
		sqinvFalse.addItem(active_i2);
		sqinvFalse.addItem(inactive_i1);
		sqinvFalse.addItem(inactive_i2);
		sqinvFalse.addItem(wornItem);
		
		sqinvFalse.activateAllItems();
		
		assertTrue(inactive_lg.isActive());
		assertTrue(active_i1.isActive());
		assertTrue(active_i2.isActive());
		assertTrue(inactive_i1.isActive());
		assertTrue(inactive_i2.isActive());
		assertFalse(wornItem.isActive());
	}

	
	@Test(expected=IllegalStateException.class)
	public void testActivateAllItems2(){
		SquareInventory sqinvFalse = new SquareInventory(true);
		LightGrenade lg = new LightGrenade();
		
		sqinvFalse.addItem(lg);
		sqinvFalse.activateAllItems();
		assertFalse(lg.isActive());
	}
	
	
}
