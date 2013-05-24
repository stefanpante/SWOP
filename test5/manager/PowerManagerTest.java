package manager;

import effect.Effect;
import grid.Grid;
import grid.RandomGridBuilder;
import org.junit.*;
import square.Square;
import square.field.PowerFailure;

import static org.junit.Assert.assertFalse;

/**
 * User: jonas
 * Date: 21/05/13
 * Time: 23:16
 */
public class PowerManagerTest {

    private static Grid getGrid(){
        RandomGridBuilder rgb = new RandomGridBuilder();
        return rgb.getGrid();
    }

    @Test
    public void test(){
        Grid grid = getGrid();

        PowerManager pm = new PowerManager(grid);

        for(int i = 0; i < 10; i++){
            pm.update(null, null);
            for(Square square : grid.getAllGridElements()){
                assertFalse(square.getNeighbors() == null);
                for(Effect effect : square.getAllSquareEffects()){
                    if(effect instanceof PowerFailure)
                        System.out.println(grid.getCoordinate(square));
                }
            }
        }


    }
}
