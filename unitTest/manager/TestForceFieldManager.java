package manager;

import grid.Grid;
import grid.GridProvider;
import square.Square;
import square.field.ForceField;
import util.Coordinate;

import java.util.ArrayList;

import static org.junit.Assert.*;


/**
 * Tests the Force Field Manager.
 */
public class TestForceFieldManager {


	private Grid getGridWith(ArrayList<Coordinate> forceFieldsGen) {

        ArrayList<ArrayList<Coordinate>> walls;
        ArrayList<Coordinate> teleports;
        ArrayList<Coordinate> lightGrenades;
        ArrayList<Coordinate> identityDiscs;


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



        Grid grid =  GridProvider.getGrid(10, 10, walls, lightGrenades, identityDiscs, teleports, forceFieldsGen, new Coordinate(3, 3));

        for(Coordinate c : forceFieldsGen){
            grid.getGridElement(c).getInventory().getForceFieldGenerator().activate();
        }

        return grid;

    }

    /**
     * Two generators are within range.
     */
    @Test
    public void detectionDiagonalTest() {
        Coordinate c1 = new Coordinate(2,6);
        Coordinate c2 = new Coordinate(4,8);

        ArrayList<Coordinate> forceFieldGen = new ArrayList<Coordinate>();
        forceFieldGen.add(c1);
        forceFieldGen.add(c2);

        Grid grid = getGridWith(forceFieldGen);
        ForceFieldManager ffm = new ForceFieldManager(grid);

        assertEquals(0,  ffm.getAllForceFields().size());
        ffm.update(null,null);
        assertEquals(1, ffm.getAllForceFields().size());
    }


    /**
     * Two generators are within range.
     */
    @Test
    public void detectionHorizontalTest() {
        Coordinate c1 = new Coordinate(2,6);
        Coordinate c2 = new Coordinate(4,6);

        ArrayList<Coordinate> forceFieldGen = new ArrayList<Coordinate>();
        forceFieldGen.add(c1);
        forceFieldGen.add(c2);

        Grid grid = getGridWith(forceFieldGen);
        ForceFieldManager ffm = new ForceFieldManager(grid);

        assertEquals(0,  ffm.getAllForceFields().size());
        ffm.update(null,null);
        assertEquals(1, ffm.getAllForceFields().size());
    }

    /**
     * Two generators are within range.
     */
    @Test
    public void detectionVerticalTest() {
        Coordinate c1 = new Coordinate(4,4);
        Coordinate c2 = new Coordinate(4,6);

        ArrayList<Coordinate> forceFieldGen = new ArrayList<Coordinate>();
        forceFieldGen.add(c1);
        forceFieldGen.add(c2);

        Grid grid = getGridWith(forceFieldGen);
        ForceFieldManager ffm = new ForceFieldManager(grid);

        assertEquals(0,  ffm.getAllForceFields().size());
        ffm.update(null,null);
        assertEquals(1, ffm.getAllForceFields().size());
    }


    /**
     * Two generators are within range.
     */
    @Test
    public void detectionWithWallTest() {
        Coordinate c1 = new Coordinate(1,0);
        Coordinate c2 = new Coordinate(3,0);

        ArrayList<Coordinate> forceFieldGen = new ArrayList<Coordinate>();
        forceFieldGen.add(c1);
        forceFieldGen.add(c2);

        Grid grid = getGridWith(forceFieldGen);
        ForceFieldManager ffm = new ForceFieldManager(grid);

        assertEquals(0,  ffm.getAllForceFields().size());
        ffm.update(null,null);
        assertEquals(0, ffm.getAllForceFields().size());
    }

    @Test
    public void onOffTest() {
        Coordinate c1 = new Coordinate(4,0);
        Coordinate c2 = new Coordinate(6,0);

        ArrayList<Coordinate> forceFieldGen = new ArrayList<Coordinate>();
        forceFieldGen.add(c1);
        forceFieldGen.add(c2);

        Grid grid = getGridWith(forceFieldGen);
        ForceFieldManager ffm = new ForceFieldManager(grid);

        assertEquals(0,  ffm.getAllForceFields().size());
        ffm.update(null,null);
        assertEquals(1, ffm.getAllForceFields().size());
        ffm.update(null,null);
        ffm.update(null,null);
        for(ForceField ff : ffm.getAllForceFields()){
            assertFalse(ff.isActive());
            for(Square square : ff.getGridElements()){
                assertFalse(square.isCoveredByField());
            }
        }
    }



}
