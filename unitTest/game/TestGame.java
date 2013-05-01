package game;

import static org.junit.Assert.*;
import grid.AbstractGridBuilder;
import grid.Grid;
import grid.RandomGridBuilder;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import game.Player;

import square.Square;

public class TestGame {

	static Square lowerLeft;
	static Square upperRight;
	
	private Game game;
	
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
		RandomGridBuilder builder = new RandomGridBuilder(10,10);
		
		game = new Game(builder.getGrid());
	}

	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void testConstructorGrid() {
		AbstractGridBuilder gridBuilder = new RandomGridBuilder(20, 20);
		Game game = new Game(gridBuilder.getGrid());
		
		game.start();
		assertTrue(game.isActive());
	}
	


	@Test 
	public void switchPlayer(){
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
	
	@Test
	public void testSetCurrentPlayer(){
		
		Player player1 = game.getCurrentPlayer();
		Player player2 = game.getNextPlayer();
		
		game.setCurrentPlayer(player1);
		assertTrue(game.getCurrentPlayer() == player1);
		
		game.setCurrentPlayer(player2);
		assertTrue(game.getCurrentPlayer() == player2);
		Player player = new Player(new Square(), 0);	
		
		try{
			game.setCurrentPlayer(player);
			fail("Player shouldn't be valid, because it is not a player in the game.");
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
		Player player1 = game.getCurrentPlayer();
		Player player2 = game.getNextPlayer();
		
		assertTrue(game.canHaveAsCurrentPlayer(player1));
		assertTrue(game.canHaveAsCurrentPlayer(player2));
	}
	
	@Test
	public void testIsValidCurrentPlayer(){
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
}
