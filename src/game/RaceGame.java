package game;

import java.util.ArrayList;
import java.util.HashMap;

import grid.Grid;

public class RaceGame extends Game{

    public static final int MAX_PLAYERS = 2;
    public static final int MIN_PLAYERS = 2;
    private HashMap<Player, ArrayList<Flag>> capturedFlags;

    public RaceGame() {
		super();
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
        return MIN_PLAYERS;
    }

}
