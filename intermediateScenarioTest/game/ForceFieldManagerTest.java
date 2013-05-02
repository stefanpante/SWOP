package game;

import grid.Grid;
import grid.GridProvider;
import grid.RandomGridBuilder;
import org.junit.*;
import square.Direction;
import util.Coordinate;

import java.util.ArrayList;

import static org.junit.Assert.*;


/**
 * User: jonas
 * Date: 02/05/13
 * Time: 22:17
 */
public class ForceFieldManagerTest {

    public static final Coordinate co7_7 = new Coordinate(7, 7);
    public static final Coordinate co3_3 = new Coordinate(3, 3);
    private static ArrayList<ArrayList<Coordinate>> walls;
    private static ArrayList<Coordinate> teleports;
    private static ArrayList<Coordinate> lightGrenades;
    private static ArrayList<Coordinate> identityDiscs;
    private static ArrayList<Coordinate> forceFieldGen;


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
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
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
    }

    @Test
    public void detectionTest(){
        Grid grid = GridProvider.getGrid(10, 10, walls, lightGrenades, identityDiscs, teleports, forceFieldGen, new Coordinate(3, 3));

        ForceFieldManager ffm = new ForceFieldManager(grid);
        ffm.update(null,null);
        System.out.println(ffm.getAllForceFields());
    }

    @Test
    public void coordinateTest(){
        Coordinate c1 = new Coordinate(2,6);
        Coordinate c2 = new Coordinate(4,8);
        assertEquals(c1.directionTo(c2), Direction.SOUTHEAST);
    }

    @Test
    public void coordinate2Test(){
        Coordinate c1 = new Coordinate(2,6);
        Coordinate c2 = new Coordinate(4,8);
        assertEquals(c2.directionTo(c1), Direction.NORTHWEST);
    }

    @Test
    public void coordinate3Test(){
        Coordinate c1 = new Coordinate(1,6);
        Coordinate c2 = new Coordinate(2,6);
        assertEquals(c1.directionTo(c2), Direction.EAST);
    }

    @Test
    public void coordinate4Test(){
        Coordinate c1 = new Coordinate(1,6);
        Coordinate c2 = new Coordinate(2,6);
        assertEquals(c2.directionTo(c1), Direction.WEST);
    }


    @Test
    public void coordinate5Test(){
        Coordinate c1 = new Coordinate(1,1);
        Coordinate c2 = new Coordinate(4,4);
        System.out.println(c1.getCoordinatesTo(c2));
    }

}