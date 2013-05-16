package itemplacer;

import grid.Grid;
import grid.GridConstraint;
import item.ForceFieldGenerator;

import java.util.ArrayList;

import util.Coordinate;

public class ForceFieldGeneratorPlacer extends ItemPlacer {

	public ForceFieldGeneratorPlacer(Grid grid) {
		super(grid);
		ArrayList<Coordinate> excluded = getGrid().getCoordinates(getGrid().getStartPositions());
		this.setItemConstraint(new GridConstraint(Grid.PERCENTAGE_FORCEFIELDGENERATORS, excluded));
	}
	
	public void placeItems(){
		ArrayList<Coordinate> coordinates = getLocations();
		for(Coordinate coor: coordinates){
			placeItem(getGrid().getSquare(coor), new ForceFieldGenerator());
		}
	}

}
