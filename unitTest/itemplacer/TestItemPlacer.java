package itemplacer;

import game.Player;
import grid.Grid;
import grid.GridProvider;
import org.junit.*;
import util.Coordinate;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestItemPlacer {

    Grid g = GridProvider.getEmptyGrid();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}


	@Before
	public void setUp() throws Exception {
    }

	@After
	public void tearDown() throws Exception {
		
	}

    @Test
    public void testSquaredLocations(){
        ArrayList<Player> playerList = new ArrayList<Player>();
        ItemPlacer idP = new IdentityDiscPlacer(g, playerList);
        //Should not be included.
        Coordinate start = new Coordinate(3,3);

        //Should be included.
        Coordinate co1_1 = new Coordinate(1,1);
        Coordinate co2_1 = new Coordinate(2,1);
        Coordinate co3_1 = new Coordinate(3,1);
        Coordinate co4_1 = new Coordinate(4,1);
        Coordinate co5_1 = new Coordinate(5,1);

        Coordinate co1_2 = new Coordinate(1,2);
        Coordinate co2_2 = new Coordinate(2,2);
        Coordinate co3_2 = new Coordinate(3,2);
        Coordinate co4_2 = new Coordinate(4,2);
        Coordinate co5_2 = new Coordinate(5,2);

        Coordinate co1_3 = new Coordinate(1,3);
        Coordinate co2_3 = new Coordinate(2,3);
        // No 3,3
        Coordinate co4_3 = new Coordinate(4,3);
        Coordinate co5_3 = new Coordinate(5,3);

        Coordinate co1_4 = new Coordinate(1,4);
        Coordinate co2_4 = new Coordinate(2,4);
        Coordinate co3_4 = new Coordinate(3,4);
        Coordinate co4_4 = new Coordinate(4,4);
        Coordinate co5_4 = new Coordinate(5,4);

        Coordinate co1_5 = new Coordinate(1,5);
        Coordinate co2_5 = new Coordinate(2,5);
        Coordinate co3_5 = new Coordinate(3,5);
        Coordinate co4_5 = new Coordinate(4,5);
        Coordinate co5_5 = new Coordinate(5,5);

        // Should not be included.
        Coordinate co0_0 = new Coordinate(0,0);
        Coordinate co0_1 = new Coordinate(0,1);
        Coordinate co0_2 = new Coordinate(0,2);
        Coordinate co0_3 = new Coordinate(0,3);
        Coordinate co0_4 = new Coordinate(0,4);
        Coordinate co0_5 = new Coordinate(0,4);

        ArrayList<Coordinate> result = idP.getSquaredLocation(start, 5);

        assertEquals(24, result.size());

        assertTrue(result.contains(co1_1));
        assertTrue(result.contains(co2_1));
        assertTrue(result.contains(co3_1));
        assertTrue(result.contains(co4_1));
        assertTrue(result.contains(co5_1));

        assertTrue(result.contains(co1_2));
        assertTrue(result.contains(co2_2));
        assertTrue(result.contains(co3_2));
        assertTrue(result.contains(co4_2));
        assertTrue(result.contains(co5_2));

        assertTrue(result.contains(co1_3));
        assertTrue(result.contains(co2_3));
        // No 3,3
        assertTrue(result.contains(co4_3));
        assertTrue(result.contains(co5_3));

        assertTrue(result.contains(co1_4));
        assertTrue(result.contains(co2_4));
        assertTrue(result.contains(co3_4));
        assertTrue(result.contains(co4_4));
        assertTrue(result.contains(co5_4));

        assertTrue(result.contains(co1_5));
        assertTrue(result.contains(co2_5));
        assertTrue(result.contains(co3_5));
        assertTrue(result.contains(co4_5));
        assertTrue(result.contains(co5_5));

        assertFalse(result.contains(start));
        assertFalse(result.contains(co0_0));
        assertFalse(result.contains(co0_1));
        assertFalse(result.contains(co0_2));
        assertFalse(result.contains(co0_3));
        assertFalse(result.contains(co0_4));
        assertFalse(result.contains(co0_5));
    }

    @Test
    public void testPlayerLocations(){
        ArrayList<Player> playerList = new ArrayList<Player>();
        Coordinate c1 = new Coordinate(0,0);
        Player p1 = new Player(g.getSquare(c1),1);
        playerList.add(p1);

        Coordinate c2 = new Coordinate(9,0);
        Player p2 = new Player(g.getSquare(c1),2);
        playerList.add(p2);

        Coordinate c3 = new Coordinate(0,9);
        Player p3 = new Player(g.getSquare(c1),3);
        playerList.add(p3);

        Coordinate c4 = new Coordinate(9,9);
        Player p4 = new Player(g.getSquare(c1),4);
        playerList.add(p4);

        ItemPlacer idP = new IdentityDiscPlacer(g, playerList);
        ArrayList<Coordinate> result = idP.getPlayerCoordinates();
        assertEquals(4,result.size());
        assertTrue(result.contains(c1));
        assertTrue(result.contains(c2));
        assertTrue(result.contains(c3));
        assertTrue(result.contains(c4));
    }
}