package grid;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import square.Square;
import util.Coordinate;

public class TestFileGridBuilder {

	private FileGridBuilder builder;
	
	
	@Test(expected = IllegalStateException.class)
	public void testUnreachableIslands(){
		String filepath = getClass().getResource("/res/grids/unreachableisland.txt").getPath();
		try {
			builder = new FileGridBuilder(filepath);
		} catch (IOException e) {
			fail("IOException, test failed");
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalStateException.class)
	public void testIllegalNumberOfPlayers(){
		
		try {
			String filepath = getClass().getResource("/res/grids/illegalnumberofplayers.txt").getPath();
			builder = new FileGridBuilder(filepath);
		} catch (IOException e) {
			fail("IOException, test failed");
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalStateException.class)
	public void testPlayerOnIsland(){
		String filepath = getClass().getResource("/res/grids/playeronisland.txt").getPath();
		try {
			builder = new FileGridBuilder(filepath);
		} catch (IOException e) {
			fail("IOException, test failed");
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalStateException.class)
	public void noPathBetweenStartLocations(){
		String filepath = getClass().getResource("/res/grids/nopathbetweenstartlocations.txt").getPath();
		try {
			builder = new FileGridBuilder(filepath);
		} catch (IOException e) {
			fail("IOException, test failed");
			e.printStackTrace();
		}
	}

	@Test
	public void testSucceededGrid(){
		String filepath = getClass().getResource("/res/grids/one.txt").getPath();
		System.out.println(filepath == null);
		try {
			builder = new FileGridBuilder(filepath);
			Grid grid = builder.getGrid();
			assertEquals(33,grid.getAllSquares().size());
			assertEquals(countWalls(grid), 5);
			assertEquals(new Coordinate(7,0), grid.getCoordinate(grid.getStartPlayerOne()));
			assertEquals(new Coordinate(0,3), grid.getCoordinate(grid.getStartPlayerTwo()));
			
		} catch (IOException e) {
			fail("IOException, test failed");
			e.printStackTrace();
		}
		
	}
	
	
	private int countWalls(Grid grid){
		int i = 0;
		for(Square sq: grid.getAllSquares()){
			if(sq.isObstructed()){
				i++;
			}
		}
		
		return i;
		
	}

	
}
