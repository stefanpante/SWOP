package grid;

import util.Coordinate;

import java.util.ArrayList;

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
