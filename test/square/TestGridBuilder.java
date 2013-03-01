/**
 * 
 */
package square;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author jonas
 *
 */
public class TestGridBuilder {

	@Test
	public void test2By2() {
		GridBuilder builder = new GridBuilder(2, 2);
		builder.construct();
		Square bottomLeft = builder.getBottomLeft();
		Square topRight = builder.getTopRight();
		assertEquals(bottomLeft.getNeighor(Direction.NORTHEAST), topRight);
		assertEquals(topRight.getNeighor(Direction.SOUTHWEST), bottomLeft);
	}
	
	@Test
	public void test3By3() {
		GridBuilder builder = new GridBuilder(3, 3);
		builder.construct();
		Square bottomLeft = builder.getBottomLeft();
		Square topRight = builder.getTopRight();
		assertEquals(bottomLeft.getNeighor(Direction.NORTHEAST).getNeighor(Direction.NORTHEAST), topRight);
		assertEquals(topRight.getNeighor(Direction.SOUTHWEST).getNeighor(Direction.SOUTHWEST), bottomLeft);
	}
	
	@Test
	public void testExceptions(){
		GridBuilder gridBuilder = new GridBuilder(0, 0);
		try {
			gridBuilder.squareFromGrid(new Square[0][0], 3, 3, Direction.EAST);
			assertTrue(false);
		} catch (IllegalAccessException e) {
			assertTrue(true);
		}
	}
	

}
