package square;

import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;

import items.Inventory;
import items.Item;
import items.LightGrenade;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import square.Square;


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
		Item lg2 = new LightGrenade();
		Item lg3 = new LightGrenade();
		Item it = new Item();

		sq.addItemToInventory(lg1);
		assertTrue(sq.hasItem(lg1));
		assertTrue(sq.getItems().size() == 1);
		
		sq.addItemToInventory(lg2);
		assertTrue(sq.hasItem(lg1));
		assertTrue(sq.hasItem(lg2));
		assertTrue(sq.getItems().size() == 2);
		
		sq.addItemToInventory(lg3);
		assertTrue(sq.hasItem(lg1));
		assertTrue(sq.hasItem(lg2));
		assertTrue(sq.hasItem(lg3));
		assertTrue(sq.getItems().size() == 3);

		sq.addItemToInventory(it);
		assertTrue(sq.hasItem(lg1));
		assertTrue(sq.hasItem(lg2));
		assertTrue(sq.hasItem(lg3));
		assertTrue(sq.hasItem(it));
		assertTrue(sq.getItems().size() == 4);
	}
		
	@Test
	public void testGetItems() {
		Square sq = new Square();
		Item lg1 = new LightGrenade();
		Item lg2 = new LightGrenade();
		Item lg3 = new LightGrenade();
		Item it = new Item();

		sq.addItemToInventory(lg1);
		sq.addItemToInventory(lg2);		
		sq.addItemToInventory(lg3);
		sq.addItemToInventory(it);
		
		Inventory inv = sq.getInventory();
		assertTrue(inv.getAmountOfUsedSpaces() == 4);
		assertTrue(inv.hasItem(lg1));
		assertTrue(inv.hasItem(lg2));
		assertTrue(inv.hasItem(lg3));
		assertTrue(inv.hasItem(it));

	}

	@Test
	public void testHasItem() {
		Square sq1 = new Square();
		Square sq2 = new Square();
		Square sq3 = new Square();

		Item lg1 = new LightGrenade();
		Item lg2 = new LightGrenade();
		Item lg3 = new LightGrenade();
		Item it = new Item();
		
		sq1.addItemToInventory(lg1);
		assertTrue(sq1.hasItem(lg1));
		
		
		sq2.addItemToInventory(lg2);
		assertTrue(sq2.hasItem(lg2));
		
		sq3.addItemToInventory(lg3);
		sq3.addItemToInventory(it);
		assertTrue(sq3.hasItem(lg3));
		assertTrue(sq3.hasItem(it));
	}

	@Test
	public void testHasActiveItem() {
		fail("Not yet implemented");
	}

}
