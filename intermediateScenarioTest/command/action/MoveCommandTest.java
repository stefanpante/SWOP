package command.action;

import grid.Grid;
import grid.GridProvider;
import grid.action.IdentityDiscMoveCommand;
import grid.action.MoveCommand;
import game.Game;
import item.IdentityDisc;

import org.junit.BeforeClass;
import org.junit.Test;
import square.Direction;
import square.Square;
import util.Coordinate;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Dieter
 * Date: 2/05/13
 * Time: 17:49
 * To change this template use File | Settings | File Templates.
 */
public class MoveCommandTest {

    public static final Coordinate co7_7 = new Coordinate(7, 7);
    public static final Coordinate co3_3 = new Coordinate(3, 3);
    private static ArrayList<ArrayList<Coordinate>> walls;
    private static ArrayList<Coordinate> teleports;
    private static ArrayList<Coordinate> lightGrenades;
    private static ArrayList<Coordinate> identityDiscs;

    /**
     * SITUATION:
     * 		__0___1___2___3___4___5___6___7___8___9__
     * 	0	|	| 	| W	|	|	|	|	|	|	|	|
     *	1 	| 	|	| W	| W	| W	|	|	|	|	|	|
     * 	2	|	|	|	|	|	|	|	|	|	|	|
     *	3 	|	|	|	| CD|	|	|	|	|	|	|
     * 	4	|	|	| T	|	| T	|	|	|	|	|	|
     *	5 	|	|	|	|	| 	|	|	|	|	|	|
     * 	6	|	|	|	|	|	|	|	|	|	|	|
     *	7 	|	|	|	|	|	|	|	| ID|	|	|
     * 	8	|	|	|	|	|	|	|	|	|	|	|
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
    }

    //Normal case no walls no teleports, range 3, NORTH
    @Test
    public void testID1(){
      Grid grid = GridProvider.getGrid(10, 10, walls, lightGrenades, identityDiscs, teleports, null, new Coordinate(3, 3));
      Game g = new Game(grid);
      IdentityDisc id = new IdentityDisc();
      Coordinate co0_9 = new Coordinate(0,9);
      Square startSquare = grid.getSquare(co0_9);
      startSquare.getInventory().addItem(id);
      MoveCommand m = new IdentityDiscMoveCommand(g,id,startSquare, Direction.NORTH);
      Square expectedSquare = grid.getSquare(new Coordinate(0,6));
      assertEquals(endSquare);
    }

    //Normal case no walls no teleports, range 3, EAST
    @Test
    public void testID2() {
        Grid grid = GridProvider.getGrid(10, 10, walls, lightGrenades, identityDiscs,teleports, null, new Coordinate(3,3));
        Game g = new Game(grid);
        IdentityDisc id = new IdentityDisc();
        Coordinate co0_9 = new Coordinate(0,9);
        Square startSquare = grid.getSquare(co0_9);
        startSquare.getInventory().addItem(id);
        MoveCommand m = new IdentityDiscMoveCommand(g,id,startSquare, Direction.EAST);

        Square expectedSquare = grid.getSquare(new Coordinate(3,9));
        assertEquals(expectedSquare, endSquare);
    }
}
