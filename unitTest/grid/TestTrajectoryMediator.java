package grid;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import square.Direction;
import square.Square;
import util.Coordinate;

public class TestTrajectoryMediator {
	private static ArrayList<ArrayList<Coordinate>> walls; 
	private static ArrayList<Coordinate> teleports; 
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		walls= new ArrayList<ArrayList<Coordinate>>();
		ArrayList<Coordinate> wall1 = new ArrayList<Coordinate>();
		wall1.add(new Coordinate(2, 0));
		wall1.add(new Coordinate(2, 1));
		walls.add(wall1);
		ArrayList<Coordinate> wall2 = new ArrayList<Coordinate>();
		wall2.add(new Coordinate(3, 1));
		wall2.add(new Coordinate(4, 1));
		walls.add(wall2);
		
		teleports = new ArrayList<>();
		teleports.add(new Coordinate(0, 1));
		teleports.add(new Coordinate(5, 5));
	}

	@Test
	public void testGetEndSquare() {
		
		
		
		GridBuilder2 gb = new GridBuilder2(10, 10, );
		Grid grid = gb.getGrid();
		TrajectoryMediator tm = new TrajectoryMediator(grid);
		Square startSquare = grid.getSquare(new Coordinate(0,0));
		Square endSquare = tm.getEndSquare(startSquare, Direction.SOUTH, 3);
		Square expectedSquare = grid.getSquare(new Coordinate(0,3));
		assertEquals(expectedSquare, endSquare);
	}
	
	@Test
	public void testGetEndSquare2() {
		//TODO: build grid like you want.
		GridBuilder2 gb = new GridBuilder2(10, 10);
		Grid grid = gb.getGrid();
		TrajectoryMediator tm = new TrajectoryMediator(grid);
		Square startSquare = grid.getSquare(new Coordinate(0,0));
		Square endSquare = tm.getEndSquare(startSquare, Direction.EAST, 3);
		Square expectedSquare = grid.getSquare(new Coordinate(3,0));
		assertEquals(expectedSquare, endSquare);	}
	
	@Test
	public void testGetEndSquare3() {
		fail("UNIMPLEMENTED");
	}
	
	@Test
	public void testGetEndSquare4() {
		fail("UNIMPLEMENTED");
	}


}
