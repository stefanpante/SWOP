package grid;

import org.junit.Test;
import square.Brick;
import square.GridElement;
import square.Square;
import util.Coordinate;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

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
			assertEquals(33,grid.getAllGridElements().size());
			assertEquals(countWalls(grid), 5);
			ArrayList<Coordinate> starts = new ArrayList<Coordinate>();
			for(Square s: grid.getStartPositions()){
				starts.add(grid.getCoordinate(s));
			}
			assertTrue(starts.contains(new Coordinate(7,0)));
			assertTrue(starts.contains(new Coordinate(0,3)));
;
			
		} catch (IOException e) {
			fail("IOException, test failed");
			e.printStackTrace();
		}
		
	}
	
	
	private int countWalls(Grid grid){
		int i = 0;
		for(GridElement sq: grid.getAllGridElements()){
			if(sq instanceof Brick){
				i++;
			}
		}
		
		return i;
		
	}

	
}
