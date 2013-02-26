package grid.core;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestCoordinate2D {

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
	
	public TestCoordinate2D() {
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void TestEquals(){
		Coordinate2D c1 = new Coordinate2D(1,1);
		Coordinate2D c2 = new Coordinate2D(1,1);
		
		assertTrue(c1.equals(c2));
		
		c2 = new Coordinate2D(-1,1);
		
		assertFalse(c1.equals(c2));
		
	}

}
