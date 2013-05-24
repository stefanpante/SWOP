package manager;

import grid.Grid;
import square.GridElement;
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
    private static final float POWERFAIL_CHANCE = 0.01f;

    /**
     * The list of Power Failures of this Power Manager
     */
    private final ArrayList<PowerFailure> powerFailures;

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
        this.powerFailures = new ArrayList<>();
    }

    @Override
    public void update(Observable o, Object arg) {
        updatePowerFailures();
        createNewPowerFailures();
    }

    /**
     * Update all Power Failures and remove those that became inactive.
     */
    private void updatePowerFailures() {
        ArrayList<PowerFailure> inactivePowerFailures = new ArrayList<>();
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
        for(GridElement gridElement : getGrid().getAllGridElements()){
            if(RANDOM.nextFloat() < POWERFAIL_CHANCE && gridElement.isSameType(new Square())){
                PowerFailure powerFailure = new PowerFailure((Square) gridElement);
                powerFailures.add(powerFailure);
            }
        }
    }
}
