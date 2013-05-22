package grid.itemplacer;

import game.Player;
import grid.Grid;

import item.ChargedIdentityDisc;

import java.util.ArrayList;

import square.Square;
import util.AStar;

/**
 * Places the chargedIdentityDisc on the specified grid.
 * The chargedIdentityDisc is placed at an equal distance (+- 2 squares)
 * from each player
 * 
 * @author Dieter Castel, Jonas Devlieghere and Stefan Pante
 */
public class ChargedIdentityDiscPlacer extends ItemPlacer {
	
	/**
	 * The max difference between the shortest path of each player to the Charged
	 * IdentityDisc.
	 */
	public static int MAX_DIFFERENCE_LENGTH = 2;
	
	/**
	 * Constructs a new ChargedIdentityDiscPlacer, it should place the ChargedIdentityDisc at 
	 * an equal distance of each player ( + - MAX_DIFFERENCE)
	 * @param grid		the grid on which the chargedIdentityDisc is placed
	 * @param players	the players which the placement should respect.
	 */
	public ChargedIdentityDiscPlacer(Grid grid, ArrayList<Player> players) {
		super(grid, players);
	}
	
	/**
	 * Places the item.
	 */
	@Override
	public void placeItems(){
		Square square = getLocation();
		placeItem(square, new ChargedIdentityDisc());
	}
	
	  /**
     * Suggest a coordinate for the Charged Disk Location
     *
     * @return A coordinate equally ( + - MAX_DIFFERENCE) far away from each player
     */
    protected Square getLocation() {
    	ArrayList<Square> playerSquares = new ArrayList<Square>();
    	for(Player player: getPlayers())
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
    
    /**
     * Method to compare the length of each path.
     * @param lengths 	the lengths to compare
     * @return	true if the lengths are within +- MAX_DIFFERENCE from each other,
     * 			false otherwise
     */
    private boolean compareLengths(ArrayList<Integer> lengths){

    	for(int i : lengths){
    		for(int j: lengths){
    			if (Math.abs(i - j) > MAX_DIFFERENCE_LENGTH)
    				return false;
    		}
    	}
    	
    	return true;
    }

}
