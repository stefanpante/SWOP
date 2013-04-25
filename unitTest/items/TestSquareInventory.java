package items;

import static org.junit.Assert.*;

import item.Item;
import item.LightGrenadeState;
import item.LightGrenade;
import item.Teleport;
import item.inventory.SquareInventory;
import item.launchable.ChargedIdentityDisc;
import item.launchable.IdentityDisc;
import item.launchable.LaunchableItem;

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
	public void testCanHaveAsItemTwice(){
		SquareInventory sqinv1 = new SquareInventory();
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
		sqinv.removeItem(lg);
		assertFalse(sqinv.hasLightGrenade());
		assertFalse(sqinv.hasItem(lg));		
		LightGrenade lg2 = new LightGrenade();
		try {
			sqinv.removeItem(lg2);
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
		sqinv.removeItem(it);
		assertTrue(sqinv.hasLightGrenade());
		sqinv.removeItem(lg);
		assertFalse(sqinv.hasLightGrenade());
	}
	
	@Test
	public void testAddLaunchable(){
		SquareInventory sqinv = new SquareInventory();
		Item it = new IdentityDisc();
		sqinv.addItem(it);
		assertTrue(sqinv.hasLaunchable());
		Item it2 = new ChargedIdentityDisc();
		sqinv.addItem(it2);
		assertTrue(sqinv.hasLaunchable());
		ArrayList<LaunchableItem> launchableList = sqinv.getLaunchables();
		assertTrue(launchableList.contains(it));
		assertTrue(launchableList.contains(it2));
		assertEquals(2,launchableList.size());
		sqinv.removeItem(it);
		assertTrue(sqinv.hasLaunchable());
		launchableList = sqinv.getLaunchables();
		assertFalse(launchableList.contains(it));
		assertTrue(launchableList.contains(it2));
		assertEquals(1,launchableList.size());
		sqinv.removeItem(it2);
		launchableList = sqinv.getLaunchables();
		assertFalse(launchableList.contains(it));
		assertFalse(launchableList.contains(it2));
		assertEquals(0,launchableList.size());
	}
}
