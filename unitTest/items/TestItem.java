package items;

import static org.junit.Assert.*;
import item.Item;
import item.ItemState;

import org.junit.AfterClass;
import org.junit.Test;

public class TestItem {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testStateChangeInactive1() {
		Item it = new Item();
		it.activate();
		assertTrue(it.getState().equals(ItemState.ACTIVE));
	}
	
	@Test(expected = IllegalStateException.class)
	public void testStateChangeInactive2() {
		Item it = new Item();
		it.deactivate();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testStateChangeInactive3() {
		Item it = new Item();
		it.wearOut();
	}
	
	@Test
	public void testStateChangeActive1() {
		Item it = new Item();
		it.activate();
		assertTrue(it.getState().equals(ItemState.ACTIVE));
		assertTrue(it.isActive());
		it.deactivate();
		assertTrue(it.getState().equals(ItemState.INACTIVE));
	}
	
	@Test(expected = IllegalStateException.class)
	public void testStateChangeActive2() {
		Item it = new Item();
		it.activate();
		assertTrue(it.getState().equals(ItemState.ACTIVE));
		it.activate();
	}
	
	@Test
	public void testStateChangeActive3() {
		Item it = new Item();
		it.activate();
		assertTrue(it.getState().equals(ItemState.ACTIVE));
		it.wearOut();
		assertTrue(it.getState().equals(ItemState.WORN));
	}
}
