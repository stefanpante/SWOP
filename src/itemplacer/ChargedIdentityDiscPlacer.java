package itemplacer;

import game.Player;
import grid.Grid;

import item.ChargedIdentityDisc;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

import square.Square;
import util.AStar;
import util.Coordinate;

/**
 * Places the chargedIdentityDisc on the specified grid.
 * The chargedIdentityDisc is placed at an equal distance (+- 2 squares)
 * from each player
 * 
 * @author Dieter Castel, Jonas Devlieghere and Stefan Pante
 */
public class ChargedIdentityDiscPlacer extends ItemPlacer {

	/**
	 * The players in the game; Needed for the location of the chargedIdentityDisc
	 */
	private ArrayList<Player> players;
	
	public static int MAX_DIFFERENCE = 2;
	
	public ChargedIdentityDiscPlacer(Grid grid, ArrayList<Player> players) {
		super(grid);
		this.players = players;
	}
	
	@Override
	public void placeItems(){
		Square square = getLocation();
		placeItem(square, new ChargedIdentityDisc());
	}
	
	  /**
     * Suggest a coordinate for the Charged Disk Location
     *
     * @return A coordinate equally far away from each player
     */
    protected Square getLocation() {
    	ArrayList<Square> playerSquares = new ArrayList<Square>();
    	for(Player player: players)
    		playerSquares.add(player.getStartPosition());
 
    	for(Square square : getGrid().getAllSquares()){
            if(!square.isObstructed()){
                try{
                    AStar aStar = new AStar(getGrid());
                    ArrayList<Integer> lengths = new ArrayList<Integer>();
                    for(Square playerSquare: playerSquares){
                    	AStar astar = new AStar(getGrid());
                    	lengths.add(astar.shortestPath(playerSquare, square).size());
                    }
                    
                    if(compareLengths(lengths))
                    	return square;
                }catch(Exception e){
                    System.err.println(e.getMessage());
                }
            }
        }
    	
    	return null;
    }
    
    private boolean compareLengths(ArrayList<Integer> lengths){

    	for(int i : lengths){
    		for(int j: lengths){
    			if (Math.abs(i - j) > MAX_DIFFERENCE)
    				return false;
    		}
    	}
    	
    	return true;
    }

}
