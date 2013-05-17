package game;

import grid.Grid;

public class RaceGame extends Game{

    public static final int MAXIMUM_PLAYERS = 2;
    public static final int MINIMUM_PLAYERS = 2;

    public RaceGame(Grid grid) {
		super(grid);
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
        return MAXIMUM_PLAYERS;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getMinimumAmountOfPlayers() {
        return MINIMUM_PLAYERS;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
