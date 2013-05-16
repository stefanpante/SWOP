package itemplacer;

import grid.Grid;
import grid.GridConstraint;
import item.IdentityDisc;
import java.util.ArrayList;

import square.Square;
import util.Coordinate;

public class IdentityDiscPlacer extends ItemPlacer {

	public static int INCLUDED_RADIUS = 7;
	
	public IdentityDiscPlacer(Grid grid) {
		super(grid);
		ArrayList<Coordinate> excluded = new ArrayList<Coordinate>(getGrid().getCoordinates(getGrid().getStartPositions()));
		setItemConstraint(new GridConstraint(Grid.PERCENTAGE_IDENTITY_DISKS, excluded, getIncluded()));
	}
	
	public void placeItems(){
		ArrayList<Coordinate> coordinates = getLocations();
		for(Coordinate coor: coordinates){
			placeItem(getGrid().getSquare(coor), new IdentityDisc());
		}
	}
	
	public ArrayList<ArrayList<Coordinate>> getIncluded(){
		ArrayList<ArrayList<Coordinate>> included = new ArrayList<ArrayList<Coordinate>>();
		for(Coordinate coordinate: getGrid().getCoordinates(getGrid().getStartPositions()))
			included.add(getSquaredLocation(coordinate, INCLUDED_RADIUS));
		
		return included;
	}

}
