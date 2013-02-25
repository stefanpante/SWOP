package grid.core;

import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;

import grid.core.Square;
import items.Item;
import items.LightGrenade;

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
		Item lg2 = new LightGrenade();
		Item lg3 = new LightGrenade();
		Item it = new Item();

		sq.addItem(lg1);
		assertTrue(sq.hasItem(lg1));
		assertTrue(sq.getItems().size() == 1);
		
		sq.addItem(lg2);
		assertTrue(sq.hasItem(lg1));
		assertTrue(sq.hasItem(lg2));
		assertTrue(sq.getItems().size() == 2);
		
		sq.addItem(lg3);
		assertTrue(sq.hasItem(lg1));
		assertTrue(sq.hasItem(lg2));
		assertTrue(sq.hasItem(lg3));
		assertTrue(sq.getItems().size() == 3);

		sq.addItem(it);
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

		sq.addItem(lg1);
		sq.addItem(lg2);		
		sq.addItem(lg3);
		sq.addItem(it);
		
		List<Item> list = sq.getItems();
		assertTrue(list.size() == 4);
		assertTrue(list.contains(lg1));
		assertTrue(list.contains(lg2));
		assertTrue(list.contains(lg3));
		assertTrue(list.contains(it));

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
		
		sq1.addItem(lg1);
		assertTrue(sq1.hasItem(lg1));
		
		
		sq2.addItem(lg2);
		assertTrue(sq2.hasItem(lg2));
		
		sq3.addItem(lg3);
		sq3.addItem(it);
		assertTrue(sq3.hasItem(lg3));
		assertTrue(sq3.hasItem(it));
	}

	@Test
	public void testHasActiveItem() {
		fail("Not yet implemented");
	}

}
