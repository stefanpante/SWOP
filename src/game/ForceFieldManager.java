package game;

import grid.Grid;
import item.ForceFieldGenerator;
import square.Direction;
import square.Square;
import square.field.ForceField;
import util.Coordinate;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * User: jonas
 * Date: 02/05/13
 * Time: 15:17
 */
public class ForceFieldManager implements Observer {

    private static final int MAX_DISTANCE = 3;

    private ArrayList<ForceField> forceFields;

    /**
     * The grid the power manager is working on.
     */
    private final Grid grid;

    public ForceFieldManager(Grid grid){
        this.grid = grid;
    }

    private void addForceField(ForceField forceField){
        if(canHaveAsForceField(forceField))
            this.forceFields.add(forceField);
    }

    private boolean canHaveAsForceField(ForceField forceField) {
        if(forceField == null)
            return false;
        if(contains(forceField))
            return false;
        return true;
    }

    public boolean contains(ForceField forceField){
        return forceFields.contains(forceField);
    }

    @Override
    public void update(Observable o, Object arg) {
        for(ForceField forceField : forceFields){
            forceField.increaseActions();
        }
        detectForceFields();
    }

    private void detectForceFields(){
        /*
         * Find the locations of force fields
         */
        ArrayList<Coordinate> forceFieldCoordinates = new ArrayList<Coordinate>();
        for(Coordinate coordinate: grid.getAllCoordinates()){
            Square square = grid.getSquare(coordinate);
            if(square.getInventory().containsSameType(new ForceFieldGenerator())){
                forceFieldCoordinates.add(coordinate);
            }
        }

        for(Coordinate coordinate : forceFieldCoordinates){
            for(Direction direction : Direction.values()){
                for(int distance = 0;  distance < MAX_DISTANCE; distance++){
                    Coordinate coordinateToCheck = coordinate.getCoordinate(direction, distance);
                    if(forceFieldCoordinates.contains(coordinateToCheck))
                        createForceFieldBetween(coordinate, coordinateToCheck);
                }
            }
        }

    }

    private void createForceFieldBetween(Coordinate coordinate, Coordinate coordinateToCheck) {
        ArrayList<Coordinate> coordinates = coordinate.getCoordinatesTo(coordinateToCheck);
        ForceField forceField = new ForceField();
        for(Coordinate c : coordinates){
            Square square = grid.getSquare(c);
            forceField.addSquare(square);
        }
        addForceField(forceField);
    }


}
