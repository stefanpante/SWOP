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
		for(Direction direction : Direction.values()){
			if(bottomLeft.getNeighor(direction) != null)
				System.out.println(direction);
		}
		assertEquals(bottomLeft.getNeighor(Direction.NORTHEAST), topRight);
		assertEquals(topRight.getNeighor(Direction.SOUTHWEST), bottomLeft);
	}

}
