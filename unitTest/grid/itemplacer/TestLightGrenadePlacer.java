package grid.itemplacer;

import game.Game;
import game.mode.RaceGameMode;
import item.LightGrenade;
import org.junit.Before;
import org.junit.Test;
import square.Square;
import util.Coordinate;
import util.Direction;

public class TestLightGrenadePlacer {

	private Game game;
	public TestLightGrenadePlacer() {
		// TODO Auto-generated constructor stub
	}

	@Before
	public void setup(){
		this.game = new Game(new RaceGameMode(10,10),2);
	}

	@Test
	public void testLGNearStart(){
		int vSize = game.getGrid().getVSize() -1;
		Coordinate lowerleft = new Coordinate(0, game.getGrid().getVSize() -1);
		for(Coordinate coor = lowerleft; coor.getY() >= vSize-2; coor = coor.getNeighbor(Direction.NORTH)){
			for(Coordinate coor2 = coor; coor2.getX() <= 2  ; coor2 = coor2.getNeighbor(Direction.EAST)){
				if(game.getGrid().getGridElement(coor2) instanceof Square){
					if(((Square) game.getGrid().getGridElement(coor2)).hasType(new LightGrenade())){
						assert(true);
					}
				}
			}
		}
		
		int hSize = game.getGrid().getHSize() - 1;
		Coordinate upperRight = new Coordinate(hSize, 0);
		for(Coordinate coor = upperRight; coor.getY() <= 2; coor = coor.getNeighbor(Direction.SOUTH)){
			for(Coordinate coor2 = coor; coor2.getX() >= hSize - 2  ; coor2 = coor2.getNeighbor(Direction.WEST)){
				if(game.getGrid().getGridElement(coor2) instanceof Square){
					if(((Square) game.getGrid().getGridElement(coor2)).hasType(new LightGrenade())){
						assert(true);
						break;
					}
				}
			}
		}
	}
}
