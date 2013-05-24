package grid.itemplacer;

import game.Player;
import grid.Grid;
import grid.GridProvider;
import item.IdentityDisc;
import square.Square;
import util.Coordinate;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestIdentityDiscPlacer {

	public static int RUNS = 10;
	Grid g;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}


	@Before
	public void setUp() throws Exception {
		g = GridProvider.getEmptyGrid();
	}

	@After
	public void tearDown() throws Exception {

	}

	/**
	 * Tests whether there is an identityDisc in the INCLUDED RADIUS of the player.
	 */
	@Test
	public void testPlayerLocations(){
		for(int i = 0; i < 10; i++){
			ArrayList<Player> playerList = new ArrayList<Player>();
			ArrayList<Coordinate> playerCoordinate = new ArrayList<Coordinate>();
			g = GridProvider.getEmptyGrid();
			Coordinate c1 = new Coordinate(0,0);
			playerCoordinate.add(c1);
			Player p1 = new Player(((Square) g.getGridElement(c1)),1);
			playerList.add(p1);

			Coordinate c2 = new Coordinate(9,0);
			playerCoordinate.add(c2);
			Player p2 = new Player(((Square) g.getGridElement(c2)),2);
			playerList.add(p2);

			Coordinate c3 = new Coordinate(0,9);
			playerCoordinate.add(c3);
			Player p3 = new Player(((Square) g.getGridElement(c3)),3);
			playerList.add(p3);

			Coordinate c4 = new Coordinate(9,9);
			playerCoordinate.add(c4);
			Player p4 = new Player(((Square) g.getGridElement(c4)),4);
			playerList.add(p4);

			IdentityDiscPlacer idP = new IdentityDiscPlacer(g, playerList);
			idP.placeItems();

			for(Coordinate coordinate: playerCoordinate){
				ArrayList<Coordinate> locations = idP.getSquaredLocation(coordinate, IdentityDiscPlacer.INCLUDED_RADIUS );
				boolean contains = false;
				for(Coordinate coor: locations){
					contains |= ((Square) g.getGridElement(coor)).hasType(new IdentityDisc());
				}
				assertTrue(contains);
			}
		}
	}


}