package grid;

import java.util.ArrayList;

import util.Coordinate;

public class GridProvider {
	
	public GridProvider(){
		
	}
	
	public static Grid getEmptyGrid(){
		return (new RandomGridBuilder()).getGrid();
	}
	
	public static Grid getGrid(int hSize, int vSize, ArrayList<ArrayList<Coordinate>> walls) {
        RandomGridBuilder gridBuilder = new RandomGridBuilder(hSize, vSize, walls);
        return gridBuilder.getGrid();
	}
}
