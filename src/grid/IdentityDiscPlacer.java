package grid;

import item.IdentityDisc;
import java.util.ArrayList;
import util.Coordinate;

public class IdentityDiscPlacer extends ItemPlacer {

	public IdentityDiscPlacer(Grid grid) {
		super(grid);
	}
	
	public void placeItems(){
		ArrayList<Coordinate> coordinates = getLocations();
		for(Coordinate coor: coordinates){
			placeItem(getGrid().getSquare(coor), new IdentityDisc());
		}
	}

}
