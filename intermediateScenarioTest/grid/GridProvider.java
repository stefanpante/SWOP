package grid;

import java.util.ArrayList;

import util.Coordinate;

public class GridProvider {
	
	public GridProvider(){
		
	}
	
	public static Grid getEmptyGrid(){
		return (new RandomGridBuilder()).getGrid();
	}
	
	public static Grid getGrid(int hSize, int vSize, ArrayList<ArrayList<Coordinate>> walls, 
			ArrayList<Coordinate> lightGrenades, 
			ArrayList<Coordinate> identityDisks, 
			ArrayList<Coordinate> teleports, 
			Coordinate chargedIdentityDisk) {
		
		RandomGridBuilder gridBuilder = new RandomGridBuilder(hSize, vSize, walls, lightGrenades, identityDisks, teleports, null, chargedIdentityDisk);
		
		return gridBuilder.getGrid();
	}
}
