package command.action;

import grid.Grid;
import grid.GridProvider;
import game.Game;
import game.Player;
import item.ChargedIdentityDisc;
import item.IdentityDisc;
import org.junit.BeforeClass;
import org.junit.Test;
import square.Direction;
import square.Square;
import util.Coordinate;

import java.util.ArrayList;

import static org.junit.Assert.*;

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

        forceFieldGen = new ArrayList<Coordinate>();
        forceFieldGen.add(new Coordinate(8,8));
    }

    //Normal case no walls no teleports, range 3, NORTH
    @Test
    public void testID1(){
      Grid grid = GridProvider.getGrid(10, 10, walls, lightGrenades, identityDiscs, teleports, forceFieldGen, new Coordinate(3, 3));
      Game g = new Game(grid);
      IdentityDisc id = new IdentityDisc();
      Coordinate co0_9 = new Coordinate(0,9);
      Square startSquare = grid.getSquare(co0_9);
      startSquare.getInventory().addItem(id);
      MoveCommand m = new IdentityDiscMoveCommand(g,id,startSquare, Direction.NORTH);
      Square expectedSquare = grid.getSquare(new Coordinate(0,6));
      assertTrue(expectedSquare.getInventory().getIdentityDiscs().contains(id));
    }

    //Normal case no walls no teleports, range 3, EAST
    @Test
    public void testID2() {
        Grid grid = GridProvider.getGrid(10, 10, walls, lightGrenades, identityDiscs,teleports, forceFieldGen, new Coordinate(3,3));
        Game g = new Game(grid);
        IdentityDisc id = new IdentityDisc();
        Coordinate co0_9 = new Coordinate(0,9);
        Square startSquare = grid.getSquare(co0_9);
        startSquare.getInventory().addItem(id);
        MoveCommand m = new IdentityDiscMoveCommand(g,id,startSquare, Direction.EAST);
        Square expectedSquare = grid.getSquare(new Coordinate(3,9));
        assertTrue(expectedSquare.getInventory().getIdentityDiscs().contains(id));
    }

    //Normal case no walls no teleports, range MAX, NORTH
    @Test
    public void testID3() {
        Grid grid = GridProvider.getGrid(10, 10, walls, lightGrenades, identityDiscs,teleports, forceFieldGen, new Coordinate(3,3));
        Game g = new Game(grid);
        ChargedIdentityDisc cd = new ChargedIdentityDisc();
        Coordinate co0_9 = new Coordinate(0,9);
        Square startSquare = grid.getSquare(co0_9);
        startSquare.getInventory().addItem(cd);
        MoveCommand m = new IdentityDiscMoveCommand(g,cd,startSquare, Direction.NORTH);
        Square expectedSquare = grid.getSquare(new Coordinate(0,0));
        assertTrue(expectedSquare.getInventory().getIdentityDiscs().contains(cd));
    }



    //Normal case no walls no teleports, player move NORTH
    @Test
    public void testPlayer1(){
        Grid grid = GridProvider.getGrid(10, 10, walls, lightGrenades, identityDiscs, teleports, forceFieldGen, new Coordinate(3, 3));
        Game g = new Game(grid);
        Coordinate co0_9 = new Coordinate(0,9);
        Square startSquare = grid.getSquare(co0_9);
        Player player = g.getCurrentPlayer();
        MoveCommand m = new PlayerMoveCommand(g,player,startSquare, Direction.NORTH);
        assertEquals(player, grid.getSquare(new Coordinate(0, 8)).getObstacle());
    }

    //Normal case no walls no teleports, player move NORTHEAST
    @Test
    public void testPlayer2(){
        Grid grid = GridProvider.getGrid(10, 10, walls, lightGrenades, identityDiscs, teleports, forceFieldGen, new Coordinate(3, 3));
        Game g = new Game(grid);
        Coordinate co0_9 = new Coordinate(0,9);
        Square startSquare = grid.getSquare(co0_9);
        Player player = g.getCurrentPlayer();
        MoveCommand m = new PlayerMoveCommand(g,player,startSquare, Direction.NORTHEAST);
        assertEquals(player,grid.getSquare(new Coordinate(1,8)).getObstacle());
    }

    //Normal case no walls no teleports, player move NORTHEAST
    @Test
    public void testPlayer3(){
        Grid grid = GridProvider.getGrid(10, 10, walls, lightGrenades, identityDiscs, teleports, forceFieldGen, new Coordinate(3, 3));
        Game g = new Game(grid);
        Coordinate co0_9 = new Coordinate(0,9);
        Square startSquare = grid.getSquare(co0_9);
        Player player = g.getCurrentPlayer();
        MoveCommand m = new PlayerMoveCommand(g,player,startSquare, Direction.EAST);
        assertEquals(player,grid.getSquare(new Coordinate(1,9)).getObstacle());
    }
}
