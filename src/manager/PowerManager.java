package manager;

import grid.Grid;
import square.Square;
import square.field.PowerFailure;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

/**
 * User: jonas
 * Date: 13/05/13
 * Time: 11:18
 */
public class PowerManager extends Manager {

    public static final float POWERFAIL_CHANCE = 0.01f;

    private ArrayList<PowerFailure> powerFailures;
    private static final Random random = new Random();


    public PowerManager(Grid grid){
        super(grid);
        this.powerFailures = new ArrayList<PowerFailure>();
    }

    @Override
    public void update(Observable o, Object arg) {
        createNewPowerFailures();

        ArrayList<PowerFailure> inactivePowerFailures = new ArrayList<PowerFailure>();
        for(PowerFailure powerFailure : powerFailures){
            if(powerFailure.isActive()){
                powerFailure.increaseActions();
            }else{
                inactivePowerFailures.add(powerFailure);
            }
        }
        powerFailures.removeAll(inactivePowerFailures);
    }

    private void createNewPowerFailures() {
        for(Square square : getGrid().getAllSquares()){
            if(random.nextFloat() < POWERFAIL_CHANCE){
                powerFailures.add(new PowerFailure(square));
            }
        }
    }
}
