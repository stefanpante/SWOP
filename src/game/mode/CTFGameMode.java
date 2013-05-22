package game.mode;

import game.gamebuilder.GameBuilder;

import java.io.IOException;


public class CTFGameMode extends GameMode{

	public static int MIN_PLAYERS = 2;
	public static int MAX_PLAYERS = 9;
	
	public CTFGameMode(int hSize, int vSize) {
		super(hSize, vSize);
	}
	
	public CTFGameMode(String filepath) throws IOException{
		super(filepath);
	}

	@Override
	public void build() {
		if(getGame() == null){
			throw new IllegalStateException("The game needs to be set before the build process can start");
		}
		getGame().setGrid(gridBuilder.getGrid());
		GameBuilder gameBuilder = new GameBuilder(getGame(), getGame().getNumberOfPlayers());
		gameBuilder.constructPlayers();
		gameBuilder.placeIdentityDiscs();
		gameBuilder.placeForceFieldGenerators();
		gameBuilder.placeChargedIdentityDisc();
		gameBuilder.placeTeleports();
		gameBuilder.placeFlags();
		
		getGame().setActivate(true);
		
	}

	@Override
	public void checkWin() {
		if(getGame() == null){
			throw new IllegalStateException("The game needs to be set before wins can be checked");
		}
		
	}

	@Override
	public void checkLoss() {
		if(getGame() == null){
			throw new IllegalStateException("The game needs to be set before losses can be checked");
		}
		
	}

	@Override
	public boolean isValidNumberOfPlayers(int numOfPlayers) {
		return numOfPlayers >= MIN_PLAYERS && numOfPlayers <= MAX_PLAYERS;
	}

	@Override
	public int getMinimumAmountOfPlayers() {
		// TODO Auto-generated method stub
		return  MIN_PLAYERS;
	}

	@Override
	public int getMaximumAmountOfPlayers() {
		return MAX_PLAYERS;
	}

}
