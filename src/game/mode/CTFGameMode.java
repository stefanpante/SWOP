package game.mode;

import game.gamebuilder.GameBuilder;

import java.io.IOException;


public class CTFGameMode extends GameMode{

	public static int MIN_PLAYERS = 2;
	public static int MAX_PLAYERS = 9;
	
	public CTFGameMode(int hSize, int vSize) {
		super(hSize, vSize);
        this.winMap = new HashMap<>();
        for(Player p : getGame().getPlayers()){
            winMap.put(p,new HashSet<Flag>());
        }
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
	public boolean checkWin() {
		if(getGame() == null)
			throw new IllegalStateException("The game needs to be set before wins can be checked");
        Player currPlayer = getGame().getCurrentPlayer();
        if(currPlayer.getStartPosition().equals(currPlayer.getPosition())){
            if(currPlayer.hasType(new Flag())){
                Flag flag = (Flag)currPlayer.filterItemsByType(new Flag()).get(0);
                winMap.get(currPlayer).add(flag);
                flag.setContainer(flag.getPlayer().getStartPosition());
            }
        }
		return winMap.get(currPlayer).size() == getGame().getPlayers().size()-1;
	}

	@Override
	public boolean checkLoss() {
		if(getGame() == null)
			throw new IllegalStateException("The game needs to be set before losses can be checked");
        return false;
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
