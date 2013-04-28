package grid;

import static org.junit.Assert.*;

import item.Teleport;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import square.Direction;
import square.Square;
import square.power.failure.PrimaryPowerFail;
import util.Coordinate;

public class TestTrajectoryMediator {
	private static ArrayList<ArrayList<Coordinate>> walls; 
	private static ArrayList<Coordinate> teleports; 
	private static ArrayList<Coordinate> lightGrenades;
	private static ArrayList<Coordinate> identityDiscs;
	
	/**
	 * SITUATION:
	 * 		__0___1___2___3___4___5___6___7___8___9__
	 * 	0	|	| 	| W	|	|	|	|	|	|	|	|
	 *	1 	| 	|	| W	| W	| W	|	|	|	|	|	|
	 * 	2	|	|	|	|	|	|	|	|	|	|	|
	 *	3 	|	|	|	|	|	|	|	|	|	|	|
	 * 	4	|	|	| T	|	| T	|	|	|	|	|	|
	 *	5 	|	|	|	|	| 	|	|	|	|	|	|
	 * 	6	|	|	|	|	|	|	|	|	|	|	|
	 *	7 	|	|	|	|	|	|	|	|	|	|	|
	 * 	8	|	|	|	|	|	|	|	|	|	|	|
	 *	9 	|	|	|	|	|	|	|	|	|	|	|
	 *
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		walls = new ArrayList<ArrayList<Coordinate>>();
		ArrayList<Coordinate> wall1 = new ArrayList<Coordinate>();
		wall1.add(new Coordinate(2, 0));
		wall1.add(new Coordinate(2, 1));
		walls.add(wall1);
		ArrayList<Coordinate> wall2 = new ArrayList<Coordinate>();
		wall2.add(new Coordinate(3, 1));
		wall2.add(new Coordinate(4, 1));
		walls.add(wall2);
		
		
		lightGrenades = new ArrayList<Coordinate>();
		lightGrenades.add(new Coordinate(7, 7));
		lightGrenades.add(new Coordinate(0, 8));
		lightGrenades.add(new Coordinate(8, 0));

		
		identityDiscs = new ArrayList<Coordinate>();
		identityDiscs.add(new Coordinate(7, 7));
		
		
		teleports = new ArrayList<Coordinate>();
		teleports.add(new Coordinate(2, 4));
		teleports.add(new Coordinate(4, 4));
	}

	//Normal case no walls no teleports, range 3, NORTH
	@Test
	public void testGetEndSquare() {		
		RandomGridBuilder gb = new RandomGridBuilder(10, 10, walls, lightGrenades, identityDiscs, teleports, null);
		Grid grid = gb.getGrid();
		TrajectoryMediator tm = new TrajectoryMediator(grid);
		Square startSquare = grid.getSquare(new Coordinate(0,9));
		Square endSquare = tm.getEndSquare(startSquare, Direction.NORTH, 3);
		Square expectedSquare = grid.getSquare(new Coordinate(0,6));
		assertEquals(expectedSquare, endSquare);
	}
		
	//Normal case no walls no teleports, range 3, EAST
	@Test
	public void testGetEndSquare2() {		
		RandomGridBuilder gb = new RandomGridBuilder(10, 10, walls, lightGrenades, identityDiscs, teleports, null);
		Grid grid = gb.getGrid();
		TrajectoryMediator tm = new TrajectoryMediator(grid);
		Square startSquare = grid.getSquare(new Coordinate(0,9));
		Square endSquare = tm.getEndSquare(startSquare, Direction.EAST, 3);
		Square expectedSquare = grid.getSquare(new Coordinate(3,9));
		assertEquals(expectedSquare, endSquare);
	}
	
	
	//Normal case no walls no teleports, range MAX, NORTH
	@Test
	public void testGetEndSquare3() {		
		RandomGridBuilder gb = new RandomGridBuilder(10, 10, walls, lightGrenades, identityDiscs, teleports, null);
		Grid grid = gb.getGrid();
		TrajectoryMediator tm = new TrajectoryMediator(grid);
		Square startSquare = grid.getSquare(new Coordinate(0,9));
		Square endSquare = tm.getEndSquare(startSquare, Direction.NORTH, Integer.MAX_VALUE);
		Square expectedSquare = grid.getSquare(new Coordinate(0,0));
		assertEquals(expectedSquare, endSquare);
	}
	
	//Normal case no walls no teleports, range MAX, EAST
	@Test
	public void testGetEndSquare4() {		
		RandomGridBuilder gb = new RandomGridBuilder(10, 10, walls, lightGrenades, identityDiscs, teleports, null);
		Grid grid = gb.getGrid();
		TrajectoryMediator tm = new TrajectoryMediator(grid);
		Square startSquare = grid.getSquare(new Coordinate(0,9));
		Square endSquare = tm.getEndSquare(startSquare, Direction.EAST, Integer.MAX_VALUE);
		Square expectedSquare = grid.getSquare(new Coordinate(9,9));
		assertEquals(expectedSquare, endSquare);
	}
	
	
	//Case with walls and no teleports, range 3, EAST
	@Test
	public void testGetEndSquare5() {
		RandomGridBuilder gb = new RandomGridBuilder(10, 10, walls, lightGrenades, identityDiscs, teleports, null);
		Grid grid = gb.getGrid();
		TrajectoryMediator tm = new TrajectoryMediator(grid);
		Square startSquare = grid.getSquare(new Coordinate(0,0));
		Square endSquare = tm.getEndSquare(startSquare, Direction.EAST, 3);
		Square expectedSquare = grid.getSquare(new Coordinate(1,0));
		assertEquals(expectedSquare, endSquare);
	}
	
	//Case with walls and no teleports, range 3, NORTH
	@Test
	public void testGetEndSquare6() {
		RandomGridBuilder gb = new RandomGridBuilder(10, 10, walls, lightGrenades, identityDiscs, teleports, null);
		Grid grid = gb.getGrid();
		TrajectoryMediator tm = new TrajectoryMediator(grid);
		Square startSquare = grid.getSquare(new Coordinate(3,4));
		Square endSquare = tm.getEndSquare(startSquare, Direction.NORTH, 3);
		Square expectedSquare = grid.getSquare(new Coordinate(3,2));
		assertEquals(expectedSquare, endSquare);
	}
	
	//Case with walls and no teleports, range 3, WEST
	@Test
	public void testGetEndSquare7() {
		RandomGridBuilder gb = new RandomGridBuilder(10, 10, walls, lightGrenades, identityDiscs, teleports, null);
		Grid grid = gb.getGrid();
		TrajectoryMediator tm = new TrajectoryMediator(grid);
		Square startSquare = grid.getSquare(new Coordinate(5,0));
		Square endSquare = tm.getEndSquare(startSquare, Direction.WEST, 3);
		Square expectedSquare = grid.getSquare(new Coordinate(3,0));
		assertEquals(expectedSquare, endSquare);
	}
	
	//Case with walls and no teleports, range MAX, NORTH
	@Test
	public void testGetEndSquare8() {
		RandomGridBuilder gb = new RandomGridBuilder(10, 10, walls, lightGrenades, identityDiscs, teleports, null);
		Grid grid = gb.getGrid();
		TrajectoryMediator tm = new TrajectoryMediator(grid);
		Square startSquare = grid.getSquare(new Coordinate(3,9));
		Square endSquare = tm.getEndSquare(startSquare, Direction.NORTH, Integer.MAX_VALUE);
		Square expectedSquare = grid.getSquare(new Coordinate(3,2));
		assertEquals(expectedSquare, endSquare);
	}
	
	//Case with walls and no teleports, range MAX, WEST
	@Test
	public void testGetEndSquare9() {
		RandomGridBuilder gb = new RandomGridBuilder(10, 10, walls, lightGrenades, identityDiscs, teleports, null);
		Grid grid = gb.getGrid();
		TrajectoryMediator tm = new TrajectoryMediator(grid);
		Square startSquare = grid.getSquare(new Coordinate(5,0));
		Square endSquare = tm.getEndSquare(startSquare, Direction.WEST, Integer.MAX_VALUE);
		Square expectedSquare = grid.getSquare(new Coordinate(3,0));
		assertEquals(expectedSquare, endSquare);
	}
	
	//Case with walls and teleports, range 3, NORTH
	@Test
	public void testGetEndSquare10() {
		RandomGridBuilder gb = new RandomGridBuilder(10, 10, walls, lightGrenades, identityDiscs, teleports, null);
		Grid grid = gb.getGrid();
		//Assure the teleports have a proper destination set.
		Teleport t0 = grid.getSquare(teleports.get(0)).getInventory().getTeleport();
		Teleport t1 = grid.getSquare(teleports.get(1)).getInventory().getTeleport();
		assertTrue(t0.getDestination().equals(t1));
		assertTrue(t1.getDestination().equals(t0));

		
		TrajectoryMediator tm = new TrajectoryMediator(grid);
		Square startSquare = grid.getSquare(new Coordinate(2,5));
		Square endSquare = tm.getEndSquare(startSquare, Direction.NORTH, 3);
		Square expectedSquare = grid.getSquare(new Coordinate(4,3));
		System.out.println(grid.getCoordinate(endSquare));
		assertEquals(expectedSquare, endSquare);
	}
	
	//Case with walls and teleports, range 3, NORTH
	@Test
	public void testGetEndSquare11() {
		RandomGridBuilder gb = new RandomGridBuilder(10, 10, walls, lightGrenades, identityDiscs, teleports, null);
		Grid grid = gb.getGrid();
		//Assure the teleports have a proper destination set.
		Teleport t0 = grid.getSquare(teleports.get(0)).getInventory().getTeleport();
		Teleport t1 = grid.getSquare(teleports.get(1)).getInventory().getTeleport();
		assertTrue(t0.getDestination().equals(t1));
		assertTrue(t1.getDestination().equals(t0));

		
		TrajectoryMediator tm = new TrajectoryMediator(grid);
		Square startSquare = grid.getSquare(new Coordinate(1,4));
		Square endSquare = tm.getEndSquare(startSquare, Direction.EAST, 3);
		Square expectedSquare = grid.getSquare(new Coordinate(5,4));
		System.out.println(grid.getCoordinate(endSquare));
		assertEquals(expectedSquare, endSquare);
	}
	
	//Case with walls and teleports, range MAX, NORTH
	@Test
	public void testGetEndSquare12() {
		RandomGridBuilder gb = new RandomGridBuilder(10, 10, walls, lightGrenades, identityDiscs, teleports, null);
		Grid grid = gb.getGrid();
		//Assure the teleports have a proper destination set.
		Teleport t0 = grid.getSquare(teleports.get(0)).getInventory().getTeleport();
		Teleport t1 = grid.getSquare(teleports.get(1)).getInventory().getTeleport();
		assertTrue(t0.getDestination().equals(t1));
		assertTrue(t1.getDestination().equals(t0));

		
		TrajectoryMediator tm = new TrajectoryMediator(grid);
		Square startSquare = grid.getSquare(new Coordinate(2,7));
		Square endSquare = tm.getEndSquare(startSquare, Direction.NORTH, Integer.MAX_VALUE);
		Square expectedSquare = grid.getSquare(new Coordinate(4,2));
		System.out.println(grid.getCoordinate(endSquare));
		assertEquals(expectedSquare, endSquare);
	}
	
	//Case with walls and teleports, range MAX, NORTH
	@Test
	public void testGetEndSquare13() {
		RandomGridBuilder gb = new RandomGridBuilder(10, 10, walls, lightGrenades, identityDiscs, teleports, null);
		Grid grid = gb.getGrid();
		//Assure the teleports have a proper destination set.
		Teleport t0 = grid.getSquare(teleports.get(0)).getInventory().getTeleport();
		Teleport t1 = grid.getSquare(teleports.get(1)).getInventory().getTeleport();
		assertTrue(t0.getDestination().equals(t1));
		assertTrue(t1.getDestination().equals(t0));

		
		TrajectoryMediator tm = new TrajectoryMediator(grid);
		Square startSquare = grid.getSquare(new Coordinate(1,4));
		Square endSquare = tm.getEndSquare(startSquare, Direction.EAST, Integer.MAX_VALUE);
		Square expectedSquare = grid.getSquare(new Coordinate(9,4));
		System.out.println(grid.getCoordinate(endSquare));
		assertEquals(expectedSquare, endSquare);
	}
	
	
	//Case with powerfails and teleports, range 3, NORTH
	@Test
	public void testGetEndSquare14() {
		RandomGridBuilder gb = new RandomGridBuilder(10, 10, walls, lightGrenades, identityDiscs, teleports, null);
		Grid grid = gb.getGrid();
		
		TrajectoryMediator tm = new TrajectoryMediator(grid);
		Square startSquare = grid.getSquare(new Coordinate(2,5));
		grid.getSquare(new Coordinate(2,4)).setPower(new PrimaryPowerFail());
		Square endSquare = tm.getEndSquare(startSquare, Direction.NORTH, 3);
		Square expectedSquare = grid.getSquare(new Coordinate(4,4));
		System.out.println(grid.getCoordinate(endSquare));
		assertEquals(expectedSquare, endSquare);
	}
	
	//Case with powerfail between and no teleport, range 3, EAST.
	@Test
	public void testGetEndSquare15() {
		RandomGridBuilder gb = new RandomGridBuilder(10, 10, walls, lightGrenades, identityDiscs, teleports, null);
		Grid grid = gb.getGrid();
		//Assure the teleports have a proper destination set.
		Teleport t0 = grid.getSquare(teleports.get(0)).getInventory().getTeleport();
		Teleport t1 = grid.getSquare(teleports.get(1)).getInventory().getTeleport();
		assertTrue(t0.getDestination().equals(t1));
		assertTrue(t1.getDestination().equals(t0));

		
		TrajectoryMediator tm = new TrajectoryMediator(grid);
		Square startSquare = grid.getSquare(new Coordinate(5,7));
		grid.getSquare(new Coordinate(6,7)).setPower(new PrimaryPowerFail());
		Square endSquare = tm.getEndSquare(startSquare, Direction.EAST, 3);
		Square expectedSquare = grid.getSquare(new Coordinate(7,7));
		System.out.println(grid.getCoordinate(endSquare));
		assertEquals(expectedSquare, endSquare);
	}
	
	
	//Case with powerfail on the starting square and no teleport, range 3, EAST.
	@Test
	public void testGetEndSquare16() {
		RandomGridBuilder gb = new RandomGridBuilder(10, 10, walls, lightGrenades, identityDiscs, teleports, null);
		Grid grid = gb.getGrid();
		//Assure the teleports have a proper destination set.
		Teleport t0 = grid.getSquare(teleports.get(0)).getInventory().getTeleport();
		Teleport t1 = grid.getSquare(teleports.get(1)).getInventory().getTeleport();
		assertTrue(t0.getDestination().equals(t1));
		assertTrue(t1.getDestination().equals(t0));

		
		TrajectoryMediator tm = new TrajectoryMediator(grid);
		Square startSquare = grid.getSquare(new Coordinate(5,7));
		startSquare.setPower(new PrimaryPowerFail());
		Square endSquare = tm.getEndSquare(startSquare, Direction.EAST, 3);
		Square expectedSquare = grid.getSquare(new Coordinate(7,7));
		System.out.println(grid.getCoordinate(endSquare));
		assertEquals(expectedSquare, endSquare);
	}
	
	//Case with powerfail on the starting square and no teleport, range 3, EAST.
	@Test
	public void testGetEndSquare17() {
		RandomGridBuilder gb = new RandomGridBuilder(10, 10, walls, lightGrenades, identityDiscs, teleports, null);
		Grid grid = gb.getGrid();
		//Assure the teleports have a proper destination set.
		Teleport t0 = grid.getSquare(teleports.get(0)).getInventory().getTeleport();
		Teleport t1 = grid.getSquare(teleports.get(1)).getInventory().getTeleport();
		assertTrue(t0.getDestination().equals(t1));
		assertTrue(t1.getDestination().equals(t0));

		
		TrajectoryMediator tm = new TrajectoryMediator(grid);
		Square startSquare = grid.getSquare(new Coordinate(5,7));
		startSquare.setPower(new PrimaryPowerFail());
		grid.getSquare(new Coordinate(6,7)).setPower(new PrimaryPowerFail());
		Square endSquare = tm.getEndSquare(startSquare, Direction.EAST, 3);
		Square expectedSquare = grid.getSquare(new Coordinate(6,7));
		System.out.println(grid.getCoordinate(endSquare));
		assertEquals(expectedSquare, endSquare);
	}
}
