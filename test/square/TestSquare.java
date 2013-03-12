package square;

import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;

import grid.GridBuilder;
import items.Inventory;
import items.Item;
import items.LightGrenade;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import player.LightTrail;
import square.obstacles.Obstacle;
import square.obstacles.Wall;



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
		Item lg2 = new Item();
		Item lg3 = new Item();
		Item it = new Item();

		sq.getInventory().addItem(lg1);
		assertTrue(sq.getInventory().hasItem(lg1));
		assertTrue(sq.getInventory().getSize() == 1);
		
		sq.getInventory().addItem(lg2);
		assertTrue(sq.getInventory().hasItem(lg1));
		assertTrue(sq.getInventory().hasItem(lg2));
		assertTrue(sq.getInventory().getSize() == 2);
		
		sq.getInventory().addItem(lg3);
		assertTrue(sq.getInventory().hasItem(lg1));
		assertTrue(sq.getInventory().hasItem(lg2));
		assertTrue(sq.getInventory().hasItem(lg3));
		assertTrue(sq.getInventory().getSize() == 3);

		sq.getInventory().addItem(it);
		assertTrue(sq.getInventory().hasItem(lg1));
		assertTrue(sq.getInventory().hasItem(lg2));
		assertTrue(sq.getInventory().hasItem(lg3));
		assertTrue(sq.getInventory().hasItem(it));
		assertTrue(sq.getInventory().getSize() == 4);
	}
		
	@Test
	public void testGetItems() {
		Square sq = new Square();
		Item lg1 = new Item();
		Item lg2 = new Item();
		Item lg3 = new LightGrenade();
		Item it = new Item();

		sq.getInventory().addItem(lg1);
		sq.getInventory().addItem(lg2);		
		sq.getInventory().addItem(lg3);
		sq.getInventory().addItem(it);
		
		Inventory inv = sq.getInventory();
		assertTrue(inv.getSize() == 4);
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
		
		sq1.getInventory().addItem(lg1);
		assertTrue(sq1.getInventory().hasItem((lg1)));
		
		
		sq2.getInventory().addItem(lg2);
		assertTrue(sq2.getInventory().hasItem(lg2));
		
		sq3.getInventory().addItem(lg3);
		sq3.getInventory().addItem(it);
		assertTrue(sq3.getInventory().hasItem(lg3));
		assertTrue(sq3.getInventory().hasItem(it));
	}
	
	/************************************
	 *  Neighbor tests
	 ************************************/

	
//	@Test TODO: move this to TestObstacle?
	public void testSetObstacle() {
		Square square = new Square();
		Square otherSquare = new Square();
		
		
		Obstacle wall = new Wall(square, otherSquare);
		
		assertEquals(square.getObstacle(), wall);
		assertTrue(square.isObstructed());
	}
	
	@Test
	public void testIsObstructed() {
		Square square = new Square();
		assertFalse(square.isObstructed());
	}	

}
