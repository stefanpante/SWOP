package manager;

import grid.Grid;
import item.ForceFieldGenerator;
import square.GridElement;
import square.Square;
import square.field.ForceField;
import util.Coordinate;
import util.Direction;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Force field manager makes sure that Generates within eachother's range
 * create a force field.
 * 
 * Each force field in turn is notified after an action.
 * 
 * @author Dieter Castel, Jonas Devlieghere   and Stefan Pante
 */
public class ForceFieldManager extends Manager{
	
	/**
	 * Range in which two generators create a force field.
	 */
    private static final int MAX_DISTANCE = 3;

    /**
     * Collection of all force fields.
     */
    private ArrayList<ForceField> forceFields;

    
    /**
     * Instantiates the Force Field manager with a reference to the grid.
     * 
     * @param grid
     */
    public ForceFieldManager(Grid grid){
        super(grid);
        this.forceFields = new ArrayList<ForceField>();
    }
    
    /**
     * Adds a force field to the collection.
     * 
     * @param	forceField
     * @throws	IllegalArgumentException
     * 			Throws an exception if the force field is already contained.
     */
    void addForceField(ForceField forceField) throws IllegalArgumentException {
        if(!canHaveAsForceField(forceField))
        	throw new IllegalArgumentException("This force field cannot be added.");
        
        this.forceFields.add(forceField);
        forceField.setAllEffects();
    }
    
    /**
     * Checks if the force field can be added to the collection.
     * 
     * @param	forceField
     * @return	False	If the force field is null or already contained.
     * 			True	Otherwise
     */
    boolean canHaveAsForceField(ForceField forceField) {
        if(forceField == null)
            return false;
        return !contains(forceField);
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
            forceField.decreaseActions();
        }
        detectForceFields();
    }
    
    /**
     * Find the locations of force fields.
     * 
     * If there are two generators within eachother's range, we check
     * if there should be a new force field between them.
     */
    void detectForceFields(){
        ArrayList<Coordinate> generatorCoordinates = new ArrayList<Coordinate>();
        
        for (GridElement gridElement: getGrid().getAllGridElements()) {

            if(gridElement.isSameType(new Square())){
                Square square = (Square) gridElement;
                if (square.hasType(new ForceFieldGenerator())) {
                    ForceFieldGenerator forceFieldGenerators = (ForceFieldGenerator)square.filterItemsByType(new ForceFieldGenerator()).get(0);
                    if(forceFieldGenerators.isActive()){
                        Coordinate coordinate = getGrid().getCoordinate(square);
                        generatorCoordinates.add(coordinate);
                    }
                }
            }
        }

        for (Coordinate coordinate : generatorCoordinates) {
            for (Direction direction : Direction.values()) {
                for (int distance = 0;  distance <= MAX_DISTANCE; distance++) {
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
    void createForceFieldBetween(Coordinate coordinate, Coordinate coordinateToCheck) {
        ArrayList<Coordinate> coordinates = coordinate.getCoordinatesTo(coordinateToCheck);
        ForceField forceField = new ForceField();
        for (Coordinate c : coordinates) {
            GridElement gridElement = getGrid().getGridElement(c);
            if(gridElement.isObstacle())
                return;
            if(gridElement.isSameType(new Square())){
                Square square = (Square) gridElement;
                forceField.addGridElement(square);
            }
        }
        try {
        	addForceField(forceField);
        } catch (IllegalArgumentException exc) {
            // Already exists
        }
    }

}
