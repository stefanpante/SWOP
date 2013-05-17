package itemplacer;

import game.Player;
import grid.Grid;
import grid.GridProvider;
import org.junit.*;
import util.Coordinate;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestIdentityDiscPlacer {

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
    public void testPlayerLocations(){
        ArrayList<Player> playerList = new ArrayList<Player>();
        Coordinate c1 = new Coordinate(0,0);
        Player p1 = new Player(g.getSquare(c1),1);
        playerList.add(p1);

        Coordinate c2 = new Coordinate(9,0);
        Player p2 = new Player(g.getSquare(c2),2);
        playerList.add(p2);

        Coordinate c3 = new Coordinate(0,9);
        Player p3 = new Player(g.getSquare(c3),3);
        playerList.add(p3);

        Coordinate c4 = new Coordinate(9,9);
        Player p4 = new Player(g.getSquare(c4),4);
        playerList.add(p4);

        IdentityDiscPlacer idP = new IdentityDiscPlacer(g, playerList);
        idP.placeItems();

        //Assert placement around startplocation etc.
    }
}