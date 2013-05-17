package game;

import grid.Grid;

public class CTFGame extends Game {

    public static final int MAX_PLAYERS = 9;
    public static final int MIN_PLAYERS = 2;

    public CTFGame(Grid grid) {
		super(grid);
		// TODO Auto-generated constructor stub
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
        return MAX_PLAYERS;
    }

    @Override
    public int getMinimumAmountOfPlayers() {
        return MIN_PLAYERS;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
