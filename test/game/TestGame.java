package game;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import player.Player;

import square.Square;
import square.state.PowerFailureState;
import square.state.RegularState;

public class TestGame {

	static Square lowerLeft;
	static Square upperRight;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		lowerLeft = new Square();
		upperRight = new Square();
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
	public void switchPlayer(){
		Game game = new Game(10, 10);
		Player player1 = game.getCurrentPlayer();
		Player player2 = game.getNextPlayer();
		
		game.setCurrentPlayer(player1);
		assertTrue(game.getCurrentPlayer() == player1);
		
		game.setCurrentPlayer(player2);
		assertTrue(game.getCurrentPlayer() == player2);
		
		game.switchToNextPlayer();
		
		assertTrue(game.getCurrentPlayer() == player1);
		assertFalse(game.getCurrentPlayer() == player2);
		
		game.switchToNextPlayer();
		assertTrue(game.getCurrentPlayer() == player2);
		assertFalse(game.getCurrentPlayer() == player1);
	}
	
	public void testSetCurrentPlayer(){
		Game game = new Game(10, 10);
		Player player1 = game.getCurrentPlayer();
		Player player2 = game.getNextPlayer();
		
		game.setCurrentPlayer(player1);
		assertTrue(game.getCurrentPlayer() == player1);
		
		game.setCurrentPlayer(player2);
		assertTrue(game.getCurrentPlayer() == player2);
		Player player = new Player(null, 0);
		
		try{
			game.setCurrentPlayer(player);
			fail("Player shouldn't be valid");
		}
		catch(Exception e){}
		
		try{
			game.setCurrentPlayer(null);
			fail("Null can't be the current player!");
		} 
		catch(Exception e){}
		
	}
	@Test
	public void testCanHaveAsPlayer(){
		Game game = new Game(10, 10);
		Player player1 = game.getCurrentPlayer();
		Player player2 = game.getNextPlayer();
		
		assertTrue(game.canHaveAsCurrentPlayer(player1));
		assertTrue(game.canHaveAsCurrentPlayer(player2));
	}
	
	@Test
	public void testIsValidCurrentPlayer(){
		Game game = new Game(10, 10);
		Player player1 = game.getCurrentPlayer();
		Player player2 = game.getNextPlayer();
		Player player3 = new Player(new Square(), 0);
		assertTrue(Game.isValidCurrentPlayer(player1));
		assertTrue(Game.isValidCurrentPlayer(player2));
		assertTrue(Game.isValidCurrentPlayer(player3));
		assertFalse(Game.isValidCurrentPlayer(null));
	}
	
	@Test
	public void testSetPlayer(){
		Game game = new Game(10, 10);
		Player playa = null;
		Player player1 = game.getCurrentPlayer();
		Player player2 = game.getNextPlayer();
		 
		try{
			game.addPlayer(playa);
			fail("A player cant be null");
			
		}
		catch(Exception e){}
		 
		try{
			game.addPlayer(player2);
			fail("Two player objects should not be the same");
		}
		catch(Exception e){ }
		try{
			game.addPlayer(player1);
			fail("The two player objects should not be the same");
		}
		catch(Exception e){}
		
	}
	
	/**
	 * Test if update states eventually convert PowerFailure square into RegularState.
	 */
	@Test
	public void testUpdateStates() {
		Game game = new Game(10,10);
		
		Square square = game.getGrid().getAllSquares().get(0);
		Square squareTwo = game.getGrid().getAllSquares().get(1);
		
		assertTrue(square.getState() instanceof RegularState);
		square.powerFail();
		
		assertTrue(square.getState() instanceof PowerFailureState);
		game.updateStates();
		
		squareTwo.powerFail();
		
		assertTrue(square.getState() instanceof PowerFailureState);
		game.updateStates();
		
		assertTrue(square.getState() instanceof PowerFailureState);
		game.updateStates();
		
		assertTrue(square.getState() instanceof RegularState);
		assertTrue(squareTwo.getState() instanceof PowerFailureState);
	}
}
