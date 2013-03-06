package square;

import static org.junit.Assert.*;

import org.junit.Test;

import board.Direction;


public class TestDirection {

	@Test
	public void test() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void TestEquals() {
		assertEquals(Direction.NORTH, Direction.NORTH);
	}
	
	@Test
	public void TestEqualsFalse() {
		assertFalse(Direction.NORTH.equals(Direction.SOUTH));
	}
	
	@Test
	public void TestOpposite(){
		assertEquals(Direction.NORTH, Direction.NORTH.opposite().opposite());
		assertEquals(Direction.NORTHEAST, Direction.NORTHEAST.opposite().opposite());
		assertEquals(Direction.EAST, Direction.EAST.opposite().opposite());
		assertEquals(Direction.SOUTHEAST, Direction.SOUTHEAST.opposite().opposite());
		assertEquals(Direction.SOUTH, Direction.SOUTH.opposite().opposite());
		assertEquals(Direction.SOUTHWEST, Direction.SOUTHWEST.opposite().opposite());
		assertEquals(Direction.WEST, Direction.WEST.opposite().opposite());
		assertEquals(Direction.NORTHWEST, Direction.NORTHWEST.opposite().opposite());
	}
	
	@Test
	public void TestOpposite2(){
		assertEquals(Direction.SOUTH, Direction.NORTH.opposite());
		assertEquals(Direction.SOUTHWEST, Direction.NORTHEAST.opposite());
		assertEquals(Direction.WEST, Direction.EAST.opposite());
		assertEquals(Direction.NORTHWEST, Direction.SOUTHEAST.opposite());
		assertEquals(Direction.NORTH, Direction.SOUTH.opposite());
		assertEquals(Direction.NORTHEAST, Direction.SOUTHWEST.opposite());
		assertEquals(Direction.EAST, Direction.WEST.opposite());
		assertEquals(Direction.SOUTHEAST, Direction.NORTHWEST.opposite());
	}


}
