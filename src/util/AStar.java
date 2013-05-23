package util;

import grid.Grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.PriorityQueue;

import square.GridElement;

public class AStar {
	
	private ArrayList<GridElementContainer> source;
	private ArrayList<GridElementContainer> closedSet;
	private PriorityQueue<GridElementContainer> openSet;
	private Grid grid;
	
	public AStar(Grid grid){
		this.grid = grid;
		closedSet = new ArrayList<GridElementContainer>();
		openSet = new PriorityQueue<GridElementContainer>();
	}

/**
 * Place all gridelements in a container usable by the A*-Algorithm
 *
 * @param   grid
 *          The grid of the game
 * @param   start
 *          The start gridelement
 * @param   goal
 *          The goal gridelement
 */
	private void boxAllGridElements(Grid grid, GridElement el1, GridElement el2) {
		this.source = new ArrayList<GridElementContainer>();
		for(GridElement element : grid.getAllGridElements()){
			GridElementContainer sq = new GridElementContainer(element);
			sq.setHeuristicDistanceFromGoal(manhattan(el1, el2));
			this.source.add(sq);
		}
	}
	
	



    /**
     * Return the gridelement containers for a list of gridelements
     *
     * @param   gridelements
     *          The gridelements to be boxed
     * @return  A list of gridelement Containers
     */
private ArrayList<GridElementContainer> getGridElementContainers(HashMap<Direction,GridElement> gridElements){
	ArrayList<GridElementContainer> gridElementContainers = new ArrayList<GridElementContainer>();
	for(Entry<Direction,GridElement> entry : gridElements.entrySet()){
		GridElementContainer sq = getGridElementContainer(entry.getValue());
			if(sq != null)
				gridElementContainers.add(sq);
		}
		return gridElementContainers;
	}

/**
 * Return the gridelement container for a given gridelement
 *
 * @param   gridelement
 *          The gridelement
 * @return  The gridelement container for a given gridelement
 */
	private GridElementContainer getGridElementContainer(GridElement gridElement){
		for(GridElementContainer sc : source){
			if(sc.getGridElement().equals(gridElement)){
				return sc;
			}
		}
		return null;
	}


/**
 * Returns a list of coordinates with the shortest path
 * between the given start gridelement and goal gridelement.
 *
 * @param   startgridelement
 *          The gridelement at the start of the path
 * @param   goalgridelement
 *          The gridelement at the end of the path
 * @return  The shortest path between the two gridelements
 */
	public ArrayList<Coordinate> shortestPath(GridElement el1, GridElement el2){	
		boxAllGridElements(grid, el1, el2);
		GridElementContainer start = getGridElementContainer(el1);
		GridElementContainer goal = getGridElementContainer(el2);
 
		openSet.add(start);
		while(!openSet.isEmpty()){
			GridElementContainer current = openSet.remove(); 
			closedSet.add(current);

			if(current.equals(goal)){
				return reconstructPath(current,start);
			}

			
			for(GridElementContainer neighbour : getGridElementContainers(current.getGridElement().getNeighbors())){
				boolean neighbourIsBetter;
				if(neighbour.getGridElement().isObstacle())
					continue;
				if(closedSet.contains(neighbour))
					continue;

				int neighbourDistanceFromStart = current.getDistanceFromStart() + 1; 

				if(!openSet.contains(neighbour)){
					openSet.add(neighbour);
					neighbourIsBetter = true;
				} else {
					neighbourIsBetter = false;
				}

				if(neighbourIsBetter){
					neighbour.setPreviousGridElementContainer(current);
					neighbour.setDistanceFromStart(neighbourDistanceFromStart);
				}
			}

		}
		throw new IllegalStateException("No path from " + grid.getCoordinate(el1) + " to " +grid.getCoordinate(el2));
	}

    /**
     * Calculates the manhattan distance between the two gridelements
     *
     * @param   el1
     *          The start gridelement
     * @param   el2
     *          The goal gridelements
     * @return  The manhattan distance between the two gridelements
     */
	private int manhattan(GridElement el1, GridElement el2){
		Coordinate start = grid.getCoordinate(el1);
		Coordinate goal = grid.getCoordinate(el2);
		return Math.abs(start.getX() - goal.getX()) + Math.abs(start.getY() - (goal.getY()));
	}

	private ArrayList<Coordinate> reconstructPath(GridElementContainer gridElementContainer, GridElementContainer startGridElementContainer){
		ArrayList<Coordinate> path = new ArrayList<Coordinate>();
		while(!(gridElementContainer.getPreviousGridElementContainer() == null)){
			path.add(grid.getCoordinate(gridElementContainer.getGridElement()));
			gridElementContainer = gridElementContainer.getPreviousGridElementContainer();
		}
		path.add(grid.getCoordinate(startGridElementContainer.getGridElement()));
		Collections.reverse(path);
		return path;
	}

	private class GridElementContainer implements Comparable<GridElementContainer> {

		private GridElement gridElement;
		private GridElementContainer previous;
		private int distanceFromStart;
		private int distanceFromGoal;
		
		public GridElementContainer(GridElement gridElement){
			setGridElement(gridElement);
		}
		
		public GridElement getGridElement(){
			return this.gridElement;
		}
		
		public void setGridElement(GridElement gridElement){
			this.gridElement = gridElement;
		}
		
		public GridElementContainer getPreviousGridElementContainer(){
			return this.previous;
		}
		
		public void setPreviousGridElementContainer(GridElementContainer gridElementContainer){
			this.previous = gridElementContainer;
		}
		
		public void setDistanceFromStart(int distance){
			this.distanceFromStart = distance;
		}
		
		public int getDistanceFromStart() {
			return this.distanceFromStart;
		}
		
		public void setHeuristicDistanceFromGoal(int distance){
			this.distanceFromGoal = distance;
		}
		
		public int getHeuristicDistanceFromGoal(){
			return this.distanceFromGoal;
		}
		
		public int compareTo(GridElementContainer other) { 
		    final int BEFORE = -1;
		    final int EQUAL = 0;
		    final int AFTER = 1;

		    if(this.equals(other)) return EQUAL;
		    if(this.getHeuristicDistanceFromGoal() < other.getHeuristicDistanceFromGoal()) return BEFORE;
		    if(this.getHeuristicDistanceFromGoal() > other.getHeuristicDistanceFromGoal()) return AFTER;
		    if(this.getHeuristicDistanceFromGoal() == other.getHeuristicDistanceFromGoal()) return EQUAL;

			return EQUAL;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((gridElement == null) ? 0 : gridElement.hashCode());
			return result;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			GridElementContainer other = (GridElementContainer) obj;
			if (!getOuterType().equals(other.getOuterType())) {
				return false;
			}
			if (gridElement == null) {
				if (other.gridElement != null) {
					return false;
				}
			} else if (!gridElement.equals(other.gridElement)) {
				return false;
			}
			return true;
		}

		private AStar getOuterType() {
			return AStar.this;
		}
	}
}
