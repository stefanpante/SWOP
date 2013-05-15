package grid;

import item.ForceFieldGenerator;

import java.util.ArrayList;

import util.Coordinate;

public class ForceFieldGeneratorPlacer extends ItemPlacer {

	public ForceFieldGeneratorPlacer(Grid grid) {
		super(grid);
	}
	
	public void placeItems(){
		ArrayList<Coordinate> coordinates = getRandomLocations();
		for(Coordinate coor: coordinates){
			placeItem(getGrid().getSquare(coor), new ForceFieldGenerator());
		}
	}

}
