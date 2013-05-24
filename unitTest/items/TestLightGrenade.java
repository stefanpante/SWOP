package items;

import item.LightGrenade;
import org.junit.*;

import static org.junit.Assert.*;

public class TestLightGrenade {

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
	public void testStateChangeInactive1() {
		LightGrenade it = new LightGrenade();
		it.activate();
		assertTrue(it.isActive());
	}
}
