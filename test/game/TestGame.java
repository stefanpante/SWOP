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
		Game game = new Game(lowerLeft, upperRight);
		Player player1 = game.getPlayer1();
		Player player2 = game.getPlayer2();
		
		game.setCurrentPlayer(player1);
		assertTrue(game.getCurrentPlayer() == player1);
		
		game.setCurrentPlayer(player2);
		assertTrue(game.getCurrentPlayer() == player2);
		
		game.switchPlayer();
		
		assertTrue(game.getCurrentPlayer() == player1);
		assertFalse(game.getCurrentPlayer() == player2);
		
		game.switchPlayer();
		assertTrue(game.getCurrentPlayer() == player2);
		assertFalse(game.getCurrentPlayer() == player1);
	}
	
	public void testSetCurrentPlayer(){
		Game game = new Game(lowerLeft, upperRight);
		Player player1 = game.getPlayer1();
		Player player2 = game.getPlayer2();
		
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
		
	}
}
