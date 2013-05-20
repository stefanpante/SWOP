package game;

import item.Flag;

import java.util.ArrayList;
import java.util.HashMap;

import grid.Grid;

public class CTFGame extends Game {

    public static final int MAX_PLAYERS = 9;
    public static final int MIN_PLAYERS = 2;
    private HashMap<Player, ArrayList<Flag>> capturedFlags;

    public CTFGame() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Player checkWinners() {
		return null;
		
	}

	@Override
	public Player checkLosers() {
		return null;
		
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
