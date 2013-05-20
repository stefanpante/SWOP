package game;

import grid.Grid;

public class RaceGame extends Game{

    public static final int MAX_PLAYERS = 2;
    public static final int MIN_PLAYERS = 2;

    public RaceGame() {
		super();
	}

	@Override
	public void checkWinners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkLosers() {
		// TODO Auto-generated method stub
		
	}

    @Override
    public int getMaximumAmountOfPlayers() {
        return MAX_PLAYERS;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getMinimumAmountOfPlayers() {
        return MIN_PLAYERS;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
