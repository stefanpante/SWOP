package grid.itemplacer;

import game.Game;
import game.mode.RaceGameMode;
import item.Item;
import item.Teleport;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import square.GridElement;
import square.Square;

public class TestTeleportPlacer {

	private Game game;

	@Before
	public void setup(){
		this.game = new Game(new RaceGameMode(10,10), 2);
	}
	
	/**
	 * Gets all the teleports on the grid and checks if there is a corresponding 
	 * square for each destination.
	 */
	@Test
	public void testTeleportsCoupling() {
		ArrayList<Item> teleportItems = new ArrayList<Item>();
				
		for(GridElement gridElement: game.getGrid().getAllGridElements()){
			if(gridElement instanceof Square){
				Square s = (Square) gridElement;
				s.hasType(new Teleport());
				ArrayList<Item> t = s.filterItemsByType(new Teleport());
				teleportItems.addAll(t);
			}
		}
		
		for(Item item: teleportItems) {
			try{
				Teleport teleport = (Teleport) item;
				game.getGrid().contains(teleport.getDestination());
			}catch(Exception exc){
				assert(false);
			}
		}
	}

}
