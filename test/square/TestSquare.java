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

import square.Square;
import square.obstacles.LightTrail;
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
		Item lg2 = new LightGrenade();
		Item lg3 = new LightGrenade();
		Item it = new Item();

		sq.getPickUpInventory().addItem(lg1);
		assertTrue(sq.getPickUpInventory().hasItem(lg1));
		assertTrue(sq.getPickUpInventory().getSize() == 1);
		
		sq.getPickUpInventory().addItem(lg2);
		assertTrue(sq.getPickUpInventory().hasItem(lg1));
		assertTrue(sq.getPickUpInventory().hasItem(lg2));
		assertTrue(sq.getPickUpInventory().getSize() == 2);
		
		sq.getPickUpInventory().addItem(lg3);
		assertTrue(sq.getPickUpInventory().hasItem(lg1));
		assertTrue(sq.getPickUpInventory().hasItem(lg2));
		assertTrue(sq.getPickUpInventory().hasItem(lg3));
		assertTrue(sq.getPickUpInventory().getSize() == 3);

		sq.getPickUpInventory().addItem(it);
		assertTrue(sq.getPickUpInventory().hasItem(lg1));
		assertTrue(sq.getPickUpInventory().hasItem(lg2));
		assertTrue(sq.getPickUpInventory().hasItem(lg3));
		assertTrue(sq.getPickUpInventory().hasItem(it));
		assertTrue(sq.getPickUpInventory().getSize() == 4);
	}
		
	@Test
	public void testGetItems() {
		Square sq = new Square();
		Item lg1 = new LightGrenade();
		Item lg2 = new LightGrenade();
		Item lg3 = new LightGrenade();
		Item it = new Item();

		sq.getPickUpInventory().addItem(lg1);
		sq.getPickUpInventory().addItem(lg2);		
		sq.getPickUpInventory().addItem(lg3);
		sq.getPickUpInventory().addItem(it);
		
		Inventory inv = sq.getPickUpInventory();
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
		
		sq1.getPickUpInventory().addItem(lg1);
		assertTrue(sq1.getPickUpInventory().hasItem((lg1)));
		
		
		sq2.getPickUpInventory().addItem(lg2);
		assertTrue(sq2.getPickUpInventory().hasItem(lg2));
		
		sq3.getPickUpInventory().addItem(lg3);
		sq3.getPickUpInventory().addItem(it);
		assertTrue(sq3.getPickUpInventory().hasItem(lg3));
		assertTrue(sq3.getPickUpInventory().hasItem(it));
	}
	
	/************************************
	 *  Neighbor tests
	 ************************************/
	
	/**
	 * Neighbor not yet set in both squares.
	 */
	@Test
	public void testAddingNeighbor() {
		Square square = new Square();
		Square otherSquare = new Square();
		
		square.setNeighbor(Direction.NORTH, otherSquare);
		
		assertTrue(otherSquare.hasNeighbor(Direction.SOUTH));
		assertTrue(square.hasNeighbor(Direction.NORTH));
	}
	
	/**
	 * Neighbor already set in other square.
	 */
	@Test
	public void testAddingNeigbors() {
		Square square = new Square();
		Square otherSquare = new Square();
		
		square.setNeighbor(Direction.NORTH, otherSquare);
		
		Square thirdSquare = new Square();
		
		square.setNeighbor(Direction.EAST, thirdSquare);
		
		assertTrue(otherSquare.hasNeighbor(Direction.SOUTH));
		assertTrue(otherSquare.hasNeighbor(Direction.SOUTH, square));
		
		assertTrue(thirdSquare.hasNeighbor(Direction.WEST));
		assertTrue(thirdSquare.hasNeighbor(Direction.WEST, square));
		
		assertFalse(thirdSquare.hasNeighbor(Direction.WEST, new Square()));
		assertFalse(thirdSquare.hasNeighbor(Direction.NORTHEAST, square));
	}
	
	/**
	 * Test adding square itself as neighbor must throw exception.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testAddingNeighborOwnSquare() {
		Square square = new Square();
		
		square.setNeighbor(Direction.SOUTH, square);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddingDuplicateNeigbor() {
		Square square = new Square();
		Square otherSquare = new Square();
		
		square.setNeighbor(Direction.NORTH, new Square());
		square.setNeighbor(Direction.NORTH, otherSquare);
	}
	
	@Test
	public void testIsConnectedTo() {
		Square square = new Square();
		Square otherSquare = new Square();
		
		assertFalse(square.isConnectedTo(otherSquare));
		
		square.setNeighbor(Direction.NORTH, otherSquare);
		assertTrue(square.isConnectedTo(otherSquare));
	}
	
	@Test
	public void testCanHaveAsNeighbor() {
		Square square = new Square();
		
		assertTrue(square.canHaveAsNeighbor(Direction.NORTH, new Square()));
	}
	
	@Test
	public void testCanHaveAsNeighborOwnSquare() {
		Square square = new Square();
		
		assertFalse(square.canHaveAsNeighbor(Direction.NORTH, square));
	}
	
	@Test
	public void testSetObstacle() {
		Square square = new Square();
		Square otherSquare = new Square();
		
		square.setNeighbor(Direction.EAST, otherSquare);
		
		Obstacle wall = new Wall(square, otherSquare);
		
		assertEquals(square.getObstacle(), wall);
		assertTrue(square.isObstructed());
	}
	
	@Test
	public void testIsObstructed() {
		Square square = new Square();
		
		assertFalse(square.isObstructed());
	}
	
	
	@Test
	public void testCanMoveTo1(){
		GridBuilder gb = new GridBuilder(10, 10);
		gb.constructSquares();
		Square bl = gb.getBottomLeft();
		Square tr = gb.getTopRight();
		
		//TEST CORNERS.
		//	Bottom Left
		//		Possible
		assertTrue(bl.canMoveTo(Direction.NORTH));
		assertTrue(bl.canMoveTo(Direction.NORTHEAST));
		assertTrue(bl.canMoveTo(Direction.EAST));
		//		Impossible
		assertFalse(bl.canMoveTo(Direction.SOUTHEAST));
		assertFalse(bl.canMoveTo(Direction.SOUTH));
		assertFalse(bl.canMoveTo(Direction.SOUTHWEST));
		assertFalse(bl.canMoveTo(Direction.WEST));
		assertFalse(bl.canMoveTo(Direction.NORTHWEST));
		//	Top Right
		//		Impossible
		assertFalse(tr.canMoveTo(Direction.NORTH));
		assertFalse(tr.canMoveTo(Direction.NORTHEAST));
		assertFalse(tr.canMoveTo(Direction.EAST));
		assertFalse(tr.canMoveTo(Direction.SOUTHEAST));
		//		Possible
		assertTrue(tr.canMoveTo(Direction.SOUTH));
		assertTrue(tr.canMoveTo(Direction.SOUTHWEST));
		assertTrue(tr.canMoveTo(Direction.WEST));
		//		Impossible
		assertFalse(tr.canMoveTo(Direction.NORTHWEST));		
	}
	
	@Test
	public void testCanMoveTo2(){
		GridBuilder gb = new GridBuilder(10, 10);
		gb.constructSquares();
		Square bl = gb.getBottomLeft();
		Square tr = gb.getTopRight();
		
		Wall blWall = new Wall(bl.getNeighbor(Direction.EAST), bl.getNeighbor(Direction.NORTHEAST));
		Wall trWall = new Wall(tr.getNeighbor(Direction.SOUTHWEST), tr.getNeighbor(Direction.SOUTH));

		
		//TEST CORNERS.
		//	Bottem Left
		//		Possible
		assertTrue(bl.canMoveTo(Direction.NORTH));
		//		Impossible
		assertFalse(bl.canMoveTo(Direction.NORTHEAST));
		assertFalse(bl.canMoveTo(Direction.EAST));
		assertFalse(bl.canMoveTo(Direction.SOUTHEAST));
		assertFalse(bl.canMoveTo(Direction.SOUTH));
		assertFalse(bl.canMoveTo(Direction.SOUTHWEST));
		assertFalse(bl.canMoveTo(Direction.WEST));
		assertFalse(bl.canMoveTo(Direction.NORTHWEST));
		//	Top Right
		//		Impossible
		assertFalse(tr.canMoveTo(Direction.NORTH));
		assertFalse(tr.canMoveTo(Direction.NORTHEAST));
		assertFalse(tr.canMoveTo(Direction.EAST));
		assertFalse(tr.canMoveTo(Direction.SOUTHEAST));
		assertFalse(tr.canMoveTo(Direction.SOUTH));
		assertFalse(tr.canMoveTo(Direction.SOUTHWEST));
		//		Possible
		assertTrue(tr.canMoveTo(Direction.WEST));
		//		Impossible
		assertFalse(tr.canMoveTo(Direction.NORTHWEST));	
		
	}
	
	@Test
	public void testCanMoveTo3(){
		GridBuilder gb = new GridBuilder(10, 10);
		gb.constructSquares();
		Square bl = gb.getBottomLeft();
		Square tr = gb.getTopRight();
		
		LightTrail blLightTrail = new LightTrail();
		try {
			blLightTrail.addSquare(bl.getNeighbor(Direction.NORTH));
			blLightTrail.addSquare(bl.getNeighbor(Direction.EAST));
		} catch (Exception e) {
			System.out.println("YES");
		}
		
		assertTrue(blLightTrail.contains(bl.getNeighbor(Direction.NORTH)));

		assertTrue(blLightTrail.contains(bl.getNeighbor(Direction.EAST)));
		
		LightTrail trLightTrail = new LightTrail();
		trLightTrail.addSquare(tr.getNeighbor(Direction.SOUTH));
		trLightTrail.addSquare(tr.getNeighbor(Direction.WEST));

		//TEST CORNERS.
		//	Bottem Left
		//		Impossible
		System.out.println(bl.getNeighbor(Direction.NORTH).isObstructed());
		System.out.println(bl.getNeighbor(Direction.EAST).isObstructed());

		assertFalse(bl.canMoveTo(Direction.NORTH));
		assertFalse(bl.canMoveTo(Direction.NORTHEAST));
		assertFalse(bl.canMoveTo(Direction.EAST));
		assertFalse(bl.canMoveTo(Direction.SOUTHEAST));
		assertFalse(bl.canMoveTo(Direction.SOUTH));
		assertFalse(bl.canMoveTo(Direction.SOUTHWEST));
		assertFalse(bl.canMoveTo(Direction.WEST));
		assertFalse(bl.canMoveTo(Direction.NORTHWEST));
		//	Top Right
		assertFalse(tr.canMoveTo(Direction.NORTH));
		assertFalse(tr.canMoveTo(Direction.NORTHEAST));
		assertFalse(tr.canMoveTo(Direction.EAST));
		assertFalse(tr.canMoveTo(Direction.SOUTHEAST));
		assertFalse(tr.canMoveTo(Direction.SOUTH));
		assertFalse(tr.canMoveTo(Direction.SOUTHWEST));
		assertFalse(tr.canMoveTo(Direction.WEST));
		assertFalse(tr.canMoveTo(Direction.NORTHWEST));		
		
	}
	

}
