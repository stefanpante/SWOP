package Board;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import utils.Coordinate2D;

import board.BoardBuilder;
import board.Square;
import board.obstacles.Wall;

public class TestBoardBuilder {

	static BoardBuilder boardBuilder;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		boardBuilder = new BoardBuilder(10,10);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testBoardBuilder(){
		HashMap<Coordinate2D, Square> squares =  boardBuilder.getSquares();
		assertEquals(squares.size(),boardBuilder.getGridSize());
		
		ArrayList<Wall> walls = boardBuilder.getWalls();
		int numberOfBricks = 0;
		for(Wall wall : walls){
			numberOfBricks += wall.getLength();
		}
		
		ArrayList<Coordinate2D> candidates = boardBuilder.constructCandidates();
		
		assertEquals(candidates.size(), squares.size() - numberOfBricks);
	}

}
