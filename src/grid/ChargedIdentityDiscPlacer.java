package grid;

import game.Player;

import item.ChargedIdentityDisc;

import java.util.ArrayList;

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
	
	public ChargedIdentityDiscPlacer(Grid grid, ArrayList<Player> players) {
		super(grid);
		this.players = players;
	}
	
	@Override
	public void placeItems(){
		Coordinate coor = getLocation();
		placeItem(getGrid().getSquare(coor), new ChargedIdentityDisc());
	}
	
	  /**
     * Suggest a coordinate for the Charged Disk Location
     *
     * @return A coordinate equally far away from each player
     */
    //FIXME: 	Fix for the support of multiple players.
    protected Coordinate getLocation() {
//        Square player1Square = getGrid().getSquare(getPlayerOneCoordinate());
//        Square player2Square = getGrid().getSquare(getPlayerTwoCoordinate());
//        Map.Entry<Coordinate,Integer> shortest = new AbstractMap.SimpleEntry<Coordinate,Integer>(null,Integer.MAX_VALUE);
//        for(Square square : getGrid().getAllSquares()){
//            if(!square.isObstructed()){
//                Coordinate thisCoordinate = getGrid().getCoordinate(square);
//                try{
//                    AStar aStar = new AStar(getGrid());
//                    int player1Length = aStar.shortestPath(player1Square, square).size();
//                    AStar aStar2 = new AStar(getGrid());
//                    int player2Length = aStar2.shortestPath(player2Square, square).size();
//                    if(Math.abs(player2Length - player1Length) <= 2){
//                        int longest = Math.max(player1Length, player2Length);
//                        if(longest < shortest.getValue()){
//                            shortest = new AbstractMap.SimpleEntry<Coordinate,Integer>(thisCoordinate, longest);
//                        }
//                    }
//                }catch(Exception e){
//                    System.err.println(e.getMessage());
//                }
//            }
//        }
//        return shortest.getKey();
    	
    	return null;
    }

}
