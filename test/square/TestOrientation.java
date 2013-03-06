package square;

import static org.junit.Assert.*;

import org.junit.*;

import board.Orientation;

public class TestOrientation {

	public static  double SAMPLESIZE = 2000;
	public static double EPSILON_10P = 0.1d;
	
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
	public TestOrientation() {
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void testRandomOrientation(){
		int estimated = 0;
		for(int i = 0; i < this.SAMPLESIZE; i++){
			Orientation or = Orientation.getRandomOrientation();
			if( or == Orientation.HORIZONTAL) estimated++;
		}
		System.out.println(estimated/SAMPLESIZE);
		assertEquals(0.5, estimated/SAMPLESIZE, EPSILON_10P);
	}

}
