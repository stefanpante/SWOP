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
		it.drop();
		it.activate();
		assertTrue(it.isActive());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testStateChangeInactive2() {
		LightGrenade it = new LightGrenade();
		it.deactivate();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testStateChangeInactive3() {
		LightGrenade it = new LightGrenade();
		it.wearOut();
	}
	
	@Test
	public void testStateChangeActive1() {
		LightGrenade it = new LightGrenade();
		it.drop();
		it.activate();
		assertTrue(it.isActive());
		it.deactivate();
		assertFalse(it.isActive());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testStateChangeActive2() {
		LightGrenade it = new LightGrenade();
		it.activate();
		assertTrue(it.isActive());
		it.activate();
	}
	
	@Test
	public void testStateChangeActive3() {
		LightGrenade it = new LightGrenade();
		it.drop();
		it.activate();
		assertTrue(it.isActive());
		it.wearOut();
		assertTrue(it.isWornOut());
	}
}
