package game.mode;

import game.Player;
import game.gamebuilder.GameBuilder;

import java.io.IOException;

public class RaceGameMode extends GameMode{

	private static final int MIN_PLAYERS = 2;
	private static final int MAX_PLAYERS = 2;
	
	public RaceGameMode(int hSize, int vSize) {
		super(hSize, vSize);
	}
	
	public RaceGameMode(String filepath) {
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
		gameBuilder.placeLightGrenades();
		gameBuilder.placeTeleports();
		
		getGame().setActivate();
		
	}

	@Override
	public boolean checkWin() {
		if(getGame() == null)
			throw new IllegalStateException("The game needs to be set before wins can be checked");

        Player nextPlayer = getGame().getNextPlayer();
        Player currentPlayer = getGame().getCurrentPlayer();
        return nextPlayer.getStartPosition().equals(currentPlayer.getPosition());
    }

	@Override
	public boolean checkLoss() {
		if(getGame() == null)
			throw new IllegalStateException("The game needs to be set before losses can be checked");
	    return getGame().isCurrentPlayerStuck();
    }



	@Override
	public boolean isValidNumberOfPlayers(int numOfPlayers) {
		return numOfPlayers >= MIN_PLAYERS && numOfPlayers <= MAX_PLAYERS;
	}

	@Override
	public int getMinimumAmountOfPlayers() {
		return MIN_PLAYERS;
	}

	@Override
	public int getMaximumAmountOfPlayers() {
		return MAX_PLAYERS;
	}

}
