package command.action;

import game.Game;
import game.Player;
import game.mode.CTFGameMode;
import grid.Grid;
import grid.GridProvider;
import item.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import square.Square;
import util.Coordinate;
import util.Direction;

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
    private Grid grid;
    private Game g;
    private ArrayList<Teleport>  teleportObjs;
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
     * see MoveCommandTest.txt
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
        forceFieldGen.add(new Coordinate(8, 8));
    }

    @Before
    public void setUp() throws Exception {
        // Set the grid.
        g = new Game(new CTFGameMode(10,10), 2);
        grid = GridProvider.getGrid(10, 10, walls);

        for(Coordinate co : lightGrenades){
            ((Square)grid.getGridElement(co)).addItem(new LightGrenade());
        }

        for(Coordinate co : identityDiscs){
            ((Square)grid.getGridElement(co)).addItem(new IdentityDisc());
        }

        //Place tele
        teleportObjs = new ArrayList<>();
        for(Coordinate co : teleports){
            Teleport t = new Teleport();
            teleportObjs.add(t);
            ((Square)grid.getGridElement(co)).addItem(t);
        }

        // Link tele
        for(int i=0; i<teleportObjs.size(); i++){
            teleportObjs.get(i).setDestination((Square)grid.getGridElement(teleports.get((i + 1) % teleportObjs.size())));
        }

        for(Coordinate co : forceFieldGen){
            ((Square)grid.getGridElement(co)).addItem(new ForceFieldGenerator());
        }

        ((Square)grid.getGridElement(new Coordinate(0,0))).addItem(new ChargedIdentityDisc());
//        grid.addStartPosition((Square)grid.getGridElement(new Coordinate(0,0)));
//        grid.addStartPosition((Square)grid.getGridElement(new Coordinate(9,9)));
//        grid.addStartPosition((Square)grid.getGridElement(new Coordinate(0,9)));
//        grid.addStartPosition((Square)grid.getGridElement(new Coordinate(9,0)));
        g.setGrid(grid);

    }

    //Normal case no walls no teleports, range 3, NORTH
    @Test
    public void testID1(){
      IdentityDisc id = new IdentityDisc();
      Coordinate co0_9 = new Coordinate(0,9);
      Square startSquare = (Square) grid.getGridElement(co0_9);
      startSquare.addItem(id);

      MoveCommand m = new IdentityDiscMoveCommand(g,id,startSquare, Direction.NORTH);
      try {
		m.execute();
	} catch (Exception e) {
		e.printStackTrace();
		fail("Move Command shouldn't throw an exception");
		
	}
      Square expectedSquare = (Square) grid.getGridElement(new Coordinate(0,6));
      assertFalse(m.getCurrentPosition() == startSquare);
      assertFalse(startSquare.hasItem(id));
      assertTrue(expectedSquare.hasItem(id));
    }

    //Normal case no walls no teleports, range 3, EAST
    @Test
    public void testID2() {
        IdentityDisc id = new IdentityDisc();
        Coordinate co0_9 = new Coordinate(0,9);
        Square startSquare = (Square) grid.getGridElement(co0_9);
        startSquare.addItem(id);
        MoveCommand m = new IdentityDiscMoveCommand(g,id,startSquare, Direction.EAST);
        try {
    		m.execute();
    	} catch (Exception e) {
    		e.printStackTrace();
    		fail("Move Command shouldn't throw an exception");
    		
    	}
        Square expectedSquare = (Square) grid.getGridElement(new Coordinate(3,9));
        assertTrue(expectedSquare.hasItem(id));
    }

    //Normal case no walls no teleports, range MAX, NORTH
    @Test
    public void testID3() {
        ChargedIdentityDisc cd = new ChargedIdentityDisc();
        Coordinate co0_9 = new Coordinate(0,9);
        Square startSquare = (Square) grid.getGridElement(co0_9);
        startSquare.addItem(cd);
        MoveCommand m = new IdentityDiscMoveCommand(g,cd,startSquare, Direction.NORTH);
        try {
    		m.execute();
    	} catch (Exception e) {
    		e.printStackTrace();
    		fail("Move Command shouldn't throw an exception");

    	}
        Square expectedSquare = (Square) grid.getGridElement(new Coordinate(0,0));
        assertTrue(expectedSquare.hasItem(cd));
    }

    //Normal case no walls no teleports, range MAX, NORTH
    @Test
    public void testID4() {
        ChargedIdentityDisc cd = new ChargedIdentityDisc();
        Coordinate co0_9 = new Coordinate(0,9);
        Square startSquare = (Square) grid.getGridElement(co0_9);
        startSquare.addItem(cd);
        MoveCommand m = new IdentityDiscMoveCommand(g,cd,startSquare, Direction.EAST);
        try {
            m.execute();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Move Command shouldn't throw an exception");

        }
        Square expectedSquare = (Square) grid.getGridElement(new Coordinate(9,9));
        assertTrue(expectedSquare.hasItem(cd));
    }

    //Case with walls and no teleports, range 3, EAST
    @Test
    public void testID5() {
        IdentityDisc id = new IdentityDisc();
        Coordinate co0_0 = new Coordinate(0,0);
        Square startSquare = (Square) grid.getGridElement(co0_0);
        startSquare.addItem(id);
        MoveCommand m = new IdentityDiscMoveCommand(g,id,startSquare, Direction.EAST);
        try {
            m.execute();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Move Command shouldn't throw an exception");
        }
        Square expectedSquare = (Square) grid.getGridElement(new Coordinate(1,0));
        assertTrue(expectedSquare.hasItem(id));
    }

    //Case with walls and no teleports, range 3, NORTH
    @Test
    public void testID6() {
        IdentityDisc id = new IdentityDisc();
        Coordinate co3_4 = new Coordinate(3,4);
        Square startSquare = (Square) grid.getGridElement(co3_4);
        startSquare.addItem(id);
        MoveCommand m = new IdentityDiscMoveCommand(g,id,startSquare, Direction.NORTH);
        try {
            m.execute();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Move Command shouldn't throw an exception");
        }
        Square expectedSquare = (Square) grid.getGridElement(new Coordinate(3,2));
        assertTrue(expectedSquare.hasItem(id));
    }


    //Case with walls and no teleports, range 3, NORTH
    @Test
    public void testID7() {
        IdentityDisc id = new IdentityDisc();
        Coordinate co5_0 = new Coordinate(5,0);
        Square startSquare = (Square) grid.getGridElement(co5_0);
        startSquare.addItem(id);
        MoveCommand m = new IdentityDiscMoveCommand(g,id,startSquare, Direction.WEST);
        try {
            m.execute();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Move Command shouldn't throw an exception");
        }
        Square expectedSquare = (Square) grid.getGridElement(new Coordinate(3,0));
        assertTrue(expectedSquare.hasItem(id));
    }

    //Case with walls and no teleports, range MAX, NORTH
    @Test
    public void testID8() {
        ChargedIdentityDisc cd = new ChargedIdentityDisc();
        Coordinate co3_9 = new Coordinate(3,9);
        Square startSquare = (Square) grid.getGridElement(co3_9);
        startSquare.addItem(cd);
        MoveCommand m = new IdentityDiscMoveCommand(g,cd,startSquare, Direction.NORTH);
        try {
            m.execute();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Move Command shouldn't throw an exception");
        }
        Square expectedSquare = (Square) grid.getGridElement(new Coordinate(3,2));
        assertTrue(expectedSquare.hasItem(cd));
    }

    //Case with walls and no teleports, range MAX, WEST
    @Test
    public void testID9() {
        ChargedIdentityDisc cd = new ChargedIdentityDisc();
        Coordinate co5_0 = new Coordinate(5,0);
        Square startSquare = (Square) grid.getGridElement(co5_0);
        startSquare.addItem(cd);
        MoveCommand m = new IdentityDiscMoveCommand(g,cd,startSquare, Direction.WEST);
        try {
            m.execute();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Move Command shouldn't throw an exception");
        }
        Square expectedSquare = (Square) grid.getGridElement(new Coordinate(3,0));
        assertTrue(expectedSquare.hasItem(cd));
    }


    //Case with walls and teleports, range 3, NORTH
    @Test
    public void testID10() {
        //Assure the teleports have a proper destination set.
        Teleport t0 = teleportObjs.get(0);
        Teleport t1 = teleportObjs.get(1);
        assertTrue(t0.getDestination().equals((Square) grid.getGridElement(teleports.get(1))));
        assertTrue(t1.getDestination().equals((Square) grid.getGridElement(teleports.get(0))));

        IdentityDisc id = new IdentityDisc();
        Coordinate co2_5 = new Coordinate(2,5);
        Square startSquare = (Square) grid.getGridElement(co2_5);
        startSquare.addItem(id);
        MoveCommand m = new IdentityDiscMoveCommand(g,id,startSquare, Direction.NORTH);
        try {
            m.execute();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Move Command shouldn't throw an exception");
        }
        Square expectedSquare = (Square) grid.getGridElement(new Coordinate(4,3));
        assertTrue(expectedSquare.hasItem(id));
    }

    //Normal case no walls no teleports, player move NORTH
    @Test
    public void testPlayer1(){
        Coordinate co0_9 = new Coordinate(0,9);
        Square startSquare = (Square) grid.getGridElement(co0_9);
        Player player = g.getCurrentPlayer();
        MoveCommand m = new PlayerMoveCommand(g,player,startSquare, Direction.NORTH);
        try {
    		m.execute();
    	} catch (Exception e) {
    		e.printStackTrace();
    		fail("Move Command shouldn't throw an exception");
    		
    	}
        assertEquals(((Square) grid.getGridElement(new Coordinate(0,8))), player.getPosition());
    }

    //Normal case no walls no teleports, player move NORTHEAST
    @Test
    public void testPlayer2(){
        Coordinate co0_9 = new Coordinate(0,9);
        Square startSquare = (Square) grid.getGridElement(co0_9);
        Player player = g.getCurrentPlayer();
        MoveCommand m = new PlayerMoveCommand(g,player,startSquare, Direction.NORTHEAST);
        try {
    		m.execute();
    	} catch (Exception e) {
    		e.printStackTrace();
    		fail("Move Command shouldn't throw an exception");
    		
    	}
        assertEquals(((Square) grid.getGridElement(new Coordinate(1,8))), player.getPosition());
    }

    //Normal case no walls no teleports, player move NORTHEAST
    @Test
    public void testPlayer3(){
        Coordinate co0_9 = new Coordinate(0,9);
        Square startSquare = (Square) grid.getGridElement(co0_9);
        Player player = g.getCurrentPlayer();
        MoveCommand m = new PlayerMoveCommand(g,player,startSquare, Direction.EAST);
        try {
    		m.execute();
    	} catch (Exception e) {
    		e.printStackTrace();
    		fail("Move Command shouldn't throw an exception");
    		
    	}
        assertEquals(((Square) grid.getGridElement(new Coordinate(1,9))), player.getPosition());
    }

    // teleports, player move NORTH
    @Test
    public void testPlayer4(){
        Coordinate co2_5 = new Coordinate(2,5);
        Square startSquare = (Square) grid.getGridElement(co2_5);
        Player player = g.getCurrentPlayer();
        MoveCommand m = new PlayerMoveCommand(g,player,startSquare, Direction.NORTH);
        try {
            m.execute();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Move Command shouldn't throw an exception");

        }
        assertEquals(((Square) grid.getGridElement(new Coordinate(4,4))), player.getPosition());
    }


    // teleports, player move WEST
    @Test
    public void testPlayer5(){
        Coordinate co5_4 = new Coordinate(5,4);
        Square startSquare = (Square) grid.getGridElement(co5_4);
        Player player = g.getCurrentPlayer();
        MoveCommand m = new PlayerMoveCommand(g,player,startSquare, Direction.WEST);
        try {
            m.execute();
            System.out.println(grid.getCoordinate(m.getCurrentPosition()));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Move Command shouldn't throw an exception");

        }
        assertEquals(((Square) grid.getGridElement(new Coordinate(2,4))), player.getPosition());
    }
}
