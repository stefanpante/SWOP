package grid;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;

import square.Direction;
import square.Square;
import util.Coordinate;

public class TestTrajectoryMediator {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGetEndSquare() {
		//TODO: build grid like you want.
		GridBuilder2 gb = new GridBuilder2(10, 10);
		Grid grid = gb.getGrid();
		TrajectoryMediator tm = new TrajectoryMediator(grid);
		Square startSquare = grid.getSquare(new Coordinate(0,0));
		tm.getEndSquare(startSquare, Direction.SOUTH, 3);
	}
	
	@Test
	public void testGetEndSquare2() {
		fail("UNIMPLEMENTED");
	}
	
	@Test
	public void testGetEndSquare3() {
		fail("UNIMPLEMENTED");
	}
	
	@Test
	public void testGetEndSquare4() {
		fail("UNIMPLEMENTED");
	}


}
