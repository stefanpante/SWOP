package items;

import static org.junit.Assert.*;

import item.LightGrenadeState;
import item.LightGrenade;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
		assertTrue(it.getState().equals(LightGrenadeState.ACTIVE));
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
		it.activate();
		assertTrue(it.getState().equals(LightGrenadeState.ACTIVE));
		assertTrue(it.isActive());
		it.deactivate();
		assertTrue(it.getState().equals(LightGrenadeState.INACTIVE));
	}
	
	@Test(expected = IllegalStateException.class)
	public void testStateChangeActive2() {
		LightGrenade it = new LightGrenade();
		it.activate();
		assertTrue(it.getState().equals(LightGrenadeState.ACTIVE));
		it.activate();
	}
	
	@Test
	public void testStateChangeActive3() {
		LightGrenade it = new LightGrenade();
		it.activate();
		assertTrue(it.getState().equals(LightGrenadeState.ACTIVE));
		it.wearOut();
		assertTrue(it.getState().equals(LightGrenadeState.WORN));
	}
}
