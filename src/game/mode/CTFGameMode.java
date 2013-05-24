package game.mode;

import game.Player;
import game.gamebuilder.GameBuilder;
import item.Flag;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class CTFGameMode extends GameMode{

    public static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 9;
    @NotNull
    private final HashMap<Player,Set<Flag>> winMap;


    public CTFGameMode(int hSize, int vSize) {
        super(hSize, vSize);
        this.winMap = new HashMap<>();
    }

    public CTFGameMode(String filepath) {
        super(filepath);
        this.winMap = new HashMap<>();
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
        gameBuilder.placeLightGrenades();
        gameBuilder.placeFlags();

        for(Player p : getGame().getPlayers()){
            winMap.put(p,new HashSet<Flag>());
        }

        getGame().setActivate();

    }

    @Override
    public boolean checkWin() {
        if(getGame() == null)
            throw new IllegalStateException("The game needs to be set before wins can be checked");
        Player currPlayer = getGame().getCurrentPlayer();
        System.out.println("CHECKING WIN");
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
        return MIN_PLAYERS;
    }

    @Override
    public int getMaximumAmountOfPlayers() {
        return MAX_PLAYERS;
    }

}