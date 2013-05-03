package game;

import grid.Grid;
import grid.GridProvider;
import grid.RandomGridBuilder;
import org.junit.*;
import square.Direction;
import square.Square;
import util.Coordinate;

import item.ForceFieldGenerator;

import java.util.ArrayList;

import static org.junit.Assert.*;


/**
 * Tests the Force Field Manager.
 */
public class TestForceFieldManager {

    public static final Coordinate co7_7 = new Coordinate(7, 7);
    public static final Coordinate co3_3 = new Coordinate(3, 3);
    private static ArrayList<ArrayList<Coordinate>> walls;
    private static ArrayList<Coordinate> teleports;
    private static ArrayList<Coordinate> lightGrenades;
    private static ArrayList<Coordinate> identityDiscs;
    private static ArrayList<Coordinate> forceFieldGen;
    
    private Grid grid;
    
    private ForceFieldManager manager;
    
    private ForceFieldGenerator generator;

    /**
     * SITUATION:
     * 		__0___1___2___3___4___5___6___7___8___9__
     * 	0	|	| 	| W	|	|	|	|	|	|	|	|
     *	1 	| 	|	| W	| W	| W	|	|	|	|	|	|
     * 	2	|	|	|	|	|	|	|	|	|	|	|
     *	3 	|	|	|	| CD|	|	|	|	|	|	|
     * 	4	|	|	| T	|	| T	|	|	|	|	|	|
     *	5 	|	|	|	|	| 	|	|	|	|	|	|
     * 	6	|	|	| FF|	|	|	|	|	|	|	|
     *	7 	|	|	|	|	|	|	|	| ID|	|	|
     * 	8	|	|	|	|	| FF|	|	|	|	|	|
     *	9 	|	|	|	|	|	|	|	|	|	|	|
     *
     */
    @Before
    public void setUpBefore() throws Exception {
        createGrid();
        manager = new ForceFieldManager(grid);
        
        generator = grid.getSquare(new Coordinate(2,6)).getInventory().getForceFieldGenerator();
    }

	private void createGrid() {
		walls = new ArrayList<ArrayList<Coordinate>>();
        ArrayList<Coordinate> wall1 = new ArrayList<Coordinate>();
        wall1.add(new Coordinate(2, 0));
        wall1.add(new Coordinate(2, 1));
        walls.add(wall1);
        ArrayList<Coordinate> wall2 = new ArrayList<Coordinate>();
        wall2.add(new Coordinate(3, 1));
        wall2.add(new Coordinate(4, 1));
        walls.add(wall2);
        
        lightGrenades = new ArrayList<Coordinate>();
        lightGrenades.add(new Coordinate(7, 7));
        lightGrenades.add(new Coordinate(0, 8));
        lightGrenades.add(new Coordinate(8, 0));

        identityDiscs = new ArrayList<Coordinate>();
        identityDiscs.add(new Coordinate(7, 7));

        teleports = new ArrayList<Coordinate>();
        teleports.add(new Coordinate(2, 4));
        teleports.add(new Coordinate(4, 4));

        forceFieldGen = new ArrayList<Coordinate>();
        forceFieldGen.add(new Coordinate(2,6));
        forceFieldGen.add(new Coordinate(4,8));
        
        grid = GridProvider.getGrid(10, 10, walls, lightGrenades, identityDiscs, teleports, forceFieldGen, new Coordinate(3, 3));
	}
    
    @Test
    public void testConstructing() {
    	new ForceFieldManager(GridProvider.getEmptyGrid());
    }
    
    /**
     * Two generators are within range.
     */
    @Test
    public void detectionTest() {
    	assertEquals(manager.getAllForceFields().size(), 0);
    	
        manager.update(null,null);
        
        assertEquals(manager.getAllForceFields().size(), 1);
    }
    
    @Test
    public void canHaveForceField() {
    	assertFalse(manager.canHaveAsForceField(null));
    }
    
    
    @Test
    public void placeGeneratorOutRange() {
    	ForceFieldGenerator generator = new ForceFieldGenerator();
    	
    	manager.update(null, null);
    	assertEquals(manager.getAllForceFields().size(), 1);
    	
    	Square square = grid.getSquare(new Coordinate(2,2));
    	square.getInventory().addForceFieldGenerator(generator);
    	
    	manager.update(null, null);
    	assertEquals(manager.getAllForceFields().size(), 1);
    }
    
    @Test
    public void placeNewGeneratorsInRange() {
    	ForceFieldGenerator generator = new ForceFieldGenerator();
    	ForceFieldGenerator generatorTwo = new ForceFieldGenerator();
    	
    	manager.update(null, null);
    	assertEquals(manager.getAllForceFields().size(), 1);
    	
    	Square square = grid.getSquare(new Coordinate(2,2));
    	square.getInventory().addForceFieldGenerator(generator);
    	
    	Square squareTwo = grid.getSquare(new Coordinate(2,3));
    	squareTwo.getInventory().addForceFieldGenerator(generatorTwo);
    	
    	System.out.println(grid.getAllCoordinates().size());
    	
    	manager.update(null, null);
    	assertEquals(2, manager.getAllForceFields().size());
    }
    
    @Test
    public void placeGeneratorNearExistingField() {
    	
    }
    

}
