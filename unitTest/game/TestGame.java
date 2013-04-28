package game;

import static org.junit.Assert.*;
import grid.Grid;
import grid.RandomGridBuilder;

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
		game = new Game(10,10);
	}

	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void testConstructorGrid() {
		RandomGridBuilder gridBuilder = new RandomGridBuilder(20, 20);
		Game game = new Game(gridBuilder.getGrid());
		
		game.start();
		assertTrue(game.isActive());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorIllegalDimensions() {
		Game game = new Game(5,5);
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
	
	@Test
	public void testSetCurrentPlayer(){
		Game game = new Game(10, 10);
		
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
	
	@Test
	public void testGetLightTrail() {
		Player player = game.getCurrentPlayer();
		game.getLightTrail(player);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testGetLightTrailNullPlayer() {
		game.getLightTrail(null);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testGetLightTrailInvalidPlayer() {
		Player player = new Player(new Square(), 3);
		game.getLightTrail(player);
	}
}
