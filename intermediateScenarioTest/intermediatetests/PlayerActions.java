package intermediatetests;
import static org.junit.Assert.*;
import item.Item;
import item.LightGrenade;
import item.LightGrenadeState;

import org.junit.Before;
import org.junit.Test;

import player.Player;
import square.Square;

/**
 * Tests the interactions of Player with other classes.
 * 
 * @author vincent
 */
public class PlayerActions {

	private Player player;
	
	@Before
	public void constructor() {
		Square square = new Square();
		player = new Player(square, 1);
	}
	
	/**
	 * If a LightGrenade is used by a player, the LightGrenade's state must become dropped.
	 */
	@Test
	public void useItemLightGrenade() {
		assertEquals(player.getRemainingActions(), Player.MAX_ALLOWED_ACTIONS);
		
		LightGrenade lightGrenade = new LightGrenade();
		player.pickUp(lightGrenade);
		
		assertEquals(player.getRemainingActions(), Player.MAX_ALLOWED_ACTIONS - 1);
		assertTrue(lightGrenade.getState().equals(LightGrenadeState.INACTIVE));
		
		player.useItem(lightGrenade);
		
		assertEquals(player.getRemainingActions(), Player.MAX_ALLOWED_ACTIONS - 2);
		assertTrue(lightGrenade.getState().equals(LightGrenadeState.DROPPED));
	}

}
