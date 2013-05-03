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
 * Force field manager makes sure that Generates within eachother's range
 * create a force field.
 * 
 * Each force field in turn is notified after an action.
 */
public class ForceFieldManager implements Observer {
	
	/**
	 * Range in which two generators create a force field.
	 */
    private static final int MAX_DISTANCE = 3;

    /**
     * Collection of all force fields.
     */
    private ArrayList<ForceField> forceFields;

    /**
     * The grid the power manager is working on.
     */
    private final Grid grid;
    
    /**
     * Instantiates the Force Field manager with a reference to the grid.
     * 
     * @param grid
     */
    public ForceFieldManager(Grid grid){
        this.grid = grid;
        this.forceFields = new ArrayList<ForceField>();
    }
    
    /**
     * Adds a force field to the collection.
     * 
     * @param	forceField
     * @throws	IllegalArgumentException
     * 			Throws an exception if the force field is already contained.
     */
    private void addForceField(ForceField forceField) throws IllegalArgumentException {
        if(!canHaveAsForceField(forceField))
        	throw new IllegalArgumentException("This force field cannot be added.");
        
        this.forceFields.add(forceField);
    }
    
    /**
     * Checks if the force field can be added to the collection.
     * 
     * @param	forceField
     * @return	False	If the force field is null or already contained.
     * 			True	Otherwise
     */
    protected boolean canHaveAsForceField(ForceField forceField) {
        if(forceField == null)
            return false;
        if(contains(forceField))
            return false;
        return true;
    }
    
    /**
     * Checks if the forcefield is contained.
     * 
     * @param forceField
     * @return
     */
    private boolean contains(ForceField forceField){
        return forceFields.contains(forceField);
    }
    
    /**
     * Returns a new arrayList with the references to the force fields.
     * 
     * @return
     */
    protected ArrayList<ForceField> getAllForceFields(){
        return new ArrayList<ForceField>(this.forceFields);
    }

    @Override
    public void update(Observable o, Object arg) {
        for(ForceField forceField : forceFields){
            forceField.increaseActions();
        }
        detectForceFields();
    }
    
    /**
     * Find the locations of force fields.
     * 
     * If there are two generators within eachother's range, we check
     * if there should be a new force field between them.
     */
    protected void detectForceFields(){   
        ArrayList<Coordinate> generatorCoordinates = new ArrayList<Coordinate>();
        
        for (Coordinate coordinate: grid.getAllCoordinates()) {
            Square square = grid.getSquare(coordinate);
            
            if (square.getInventory().containsSameType(new ForceFieldGenerator()))
                generatorCoordinates.add(coordinate);
        }

        for (Coordinate coordinate : generatorCoordinates) {
            for (Direction direction : Direction.values()) {
                for (int distance = 0;  distance < MAX_DISTANCE; distance++) {
                    Coordinate coordinateToCheck = coordinate.getCoordinate(direction, distance);
                    
                    if (generatorCoordinates.contains(coordinateToCheck) && !coordinate.equals(coordinateToCheck))
                        createForceFieldBetween(coordinate, coordinateToCheck);
                }
            }
        }

    }

    /**
     * Creats a forceField between two given coordinates.
     * 
     * @param coordinate
     * @param coordinateToCheck
     */
    protected void createForceFieldBetween(Coordinate coordinate, Coordinate coordinateToCheck) {
        ArrayList<Coordinate> coordinates = coordinate.getCoordinatesTo(coordinateToCheck);
        ForceField forceField = new ForceField();
        
        for (Coordinate c : coordinates) {
            Square square = grid.getSquare(c);
            forceField.addSquare(square);
        }
        
        try {
        	addForceField(forceField);
        } catch (IllegalArgumentException exc) {
            // Already exists
        }
    }

}
