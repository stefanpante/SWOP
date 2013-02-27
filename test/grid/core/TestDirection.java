package grid.core;

import static org.junit.Assert.*;

import org.junit.Test;

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


}
