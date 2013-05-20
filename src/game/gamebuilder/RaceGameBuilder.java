package game.gamebuilder;

import game.RaceGame;

import java.io.IOException;



public class RaceGameBuilder extends GameBuilder {

	public RaceGameBuilder(int hSize, int vSize, int numOfPlayers) {
		super(hSize, vSize, numOfPlayers);
		this.game = new RaceGame();
		
	}
	
	public RaceGameBuilder(String filename, int numOfPlayers) throws IOException{
		super(filename, numOfPlayers);
	}


	@Override
	public boolean isValidNumberOfPlayers(int numOfPlayers) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
