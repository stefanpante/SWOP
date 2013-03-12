package game;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import player.Player;

import square.Square;

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
		Player player = new Player(null, null);
		
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
		Player player3 = new Player(new Square(), null);
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
		try{
			playa = new Player(null,null);
			game.addPlayer(playa);
			game.addPlayer(player1);
			game.addPlayer(playa);
		}
		
		catch(Exception e){
			fail("A new player object should be able to become the new player 1 or 2");
		}
		
	}
}
