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

    /**
     * The chance a Square has a Power Failure
     */
    public static final float POWERFAIL_CHANCE = 0.01f;

    /**
     * The list of Power Failures of this Power Manager
     */
    private ArrayList<PowerFailure> powerFailures;

    /**
     * Random generator for this Power Manager
     */
    private static final Random RANDOM = new Random();

    /**
     * Create a new Power Manager
     *
     * @param   grid
     *          The Grid
     */
    public PowerManager(Grid grid){
        super(grid);
        this.powerFailures = new ArrayList<PowerFailure>();
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println(o);
        updatePowerFailures();
        createNewPowerFailures();
    }

    /**
     * Update all Power Failures and remove those that became inactive.
     */
    private void updatePowerFailures() {
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

    /**
     * Iterate over every square and create a Power Failure with a
     * POWERFAIL_CHANCE chance.
     */
    private void createNewPowerFailures() {
        for(Square square : getGrid().getAllSquares()){
            if(RANDOM.nextFloat() < POWERFAIL_CHANCE){
                PowerFailure powerFailure = new PowerFailure(square);
                powerFailures.add(powerFailure);
            }
        }
    }
}
