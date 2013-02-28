package handlers;

import java.io.IOException;
import java.io.InputStreamReader;

import game.Game;

/**
 * Handler/Controller for the pick up use case
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class PickUpHandler {

	private Game game;
	public PickUpHandler(Game game) {
		this.game = game;
	}
	
	/**
	 * commandline for now, has to be integrated into the gui.
	 * @throws IOException
	 */
	public void pickUp() throws IOException{
		InputStreamReader cin = new InputStreamReader(System.in);
		int itemIndex = cin.read();
		game.pickUp(itemIndex);
		cin.close();
	}

}
