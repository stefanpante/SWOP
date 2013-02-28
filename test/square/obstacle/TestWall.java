package square.obstacle;

import static org.junit.Assert.*;

import org.junit.*;

import square.Square;
import square.obstacles.Wall;

public class TestWall {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
	public void testToString() {
		fail("Not yet implemented");
	}
	
	public TestWall() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Test for constructing with two duplicate squares.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalArgumentException() {
		Square squareOne = new Square();
		
		Wall wall = new Wall(squareOne, squareOne);
	}
	
	/**
	 * Test for constructing and adding a duplicate square.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalArgumentExceptionDuplicate() {
		Square squareOne = new Square();
		Square squareTwo = new Square();
		
		Wall wall = new Wall(squareOne, squareOne);
		wall.addSquare(squareOne);
	}
	
	/**
	 * Test if validSquare for duplicate is false.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testValidSquareDuplicate() {
		Square squareOne = new Square();
		Square squareTwo = new Square();
		
		Wall wall = new Wall(squareOne, squareOne);
		
		assertFalse(wall.isValidSquare(squareOne));
	}
	
	/**
	 * Test if validSquare is true for a new square.
	 */
	public  void testValidSquareNewSquare() {
		Square squareOne = new Square();
		Square squareTwo = new Square();
		
		Wall wall = new Wall(squareOne, squareOne);
		
		Square squareThree = new Square();
		
		assertTrue(wall.isValidSquare(squareThree));
	}


}
