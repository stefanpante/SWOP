package manager;

import grid.Grid;
import org.junit.*;
import square.Square;
import util.Coordinate;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;

/**
 * User: jonas
 * Date: 21/05/13
 * Time: 23:16
 */
public class PowerManagerTest {

    private Grid grid;
    private static final ArrayList<Coordinate> empty = new ArrayList<Coordinate>();


    @Test
    public void test(){
        PowerManager pm = new PowerManager(grid);
        assertFalse(pm.getGrid() == null);
        pm.update(null, null);
        for(Square square : grid.getAllSquares()){
            if(square.isCoveredByField()){
                System.out.println(grid.getCoordinate(square));
            }
        }
    }
}
