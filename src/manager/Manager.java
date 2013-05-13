package manager;

import grid.Grid;

import java.util.Observer;

/**
 * User: jonas
 * Date: 13/05/13
 * Time: 11:10
 */
public abstract class Manager implements Observer {

    /**
     * The grid the power manager is working on.
     */
    private final Grid grid;

    public Manager(Grid grid){
        this.grid = grid;
    }

    public Grid getGrid(){
        return this.grid;
    }
}
