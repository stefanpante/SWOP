package game;

import static org.junit.Assert.*;

import java.util.ArrayList;

import grid.Grid;
import grid.GridProvider;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import util.Coordinate;

public class TestGameSituations {
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testStuck() {
		ArrayList<Coordinate> teleports = new ArrayList<Coordinate>();
		teleports.add(new Coordinate(3, 8));
		teleports.add(new Coordinate(8, 0));
		
		Grid grid = GridProvider.getGrid(10, 10, null, null, null, teleports);
		Game game = new Game(grid);
		assertFalse(game.isCurrentPlayerStuck());
	}

}
