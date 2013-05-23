package util;

import grid.Grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.PriorityQueue;

import square.Square;

public class AStar {
	
	private ArrayList<SquareContainer> source;
	
	private ArrayList<SquareContainer> closedSet;
	private PriorityQueue<SquareContainer> openSet;
	private Grid grid;
	
	public AStar(Grid grid){
		this.grid = grid;
		closedSet = new ArrayList<SquareContainer>();
		openSet = new PriorityQueue<SquareContainer>();
	}
	
	private void boxAllSquares(Grid grid, Square start, Square goal) {
		this.source = new ArrayList<SquareContainer>();
		for(Square square : grid.getAllSquares()){
			SquareContainer sq = new SquareContainer(square);
			sq.setHeuristicDistanceFromGoal(manhattan(start, goal));
			this.source.add(sq);
		}
	}
	
	private ArrayList<SquareContainer> getSquareContainers(HashMap<Direction,Square> squares){
		ArrayList<SquareContainer> squareContainers = new ArrayList<SquareContainer>();
		for(Entry<Direction,Square> entry : squares.entrySet()){
			SquareContainer sq = getSquareContainer(entry.getValue());
			if(sq != null)
				squareContainers.add(sq);
		}
		return squareContainers;
	}
	
	private SquareContainer getSquareContainer(Square square){
		for(SquareContainer sc : source){
			if(sc.getSquare().equals(square)){
				return sc;
			}
		}
		return null;
	}

	public ArrayList<Coordinate> shortestPath(Square startSquare, Square goalSquare){	
		boxAllSquares(grid, startSquare, goalSquare);
		SquareContainer start = getSquareContainer(startSquare);
		SquareContainer goal = getSquareContainer(goalSquare);
		
		openSet.add(start);
		while(!openSet.isEmpty()){
			SquareContainer current = openSet.remove(); 
			closedSet.add(current);

			if(current.equals(goal)){
				return reconstructPath(current,start);
			}

			
			for(SquareContainer neighbour : getSquareContainers(current.getSquare().getNeighbors())){
				boolean neighbourIsBetter;
				if(neighbour.getSquare().isObstacle())
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
					neighbour.setPreviousSquareContainer(current);
					neighbour.setDistanceFromStart(neighbourDistanceFromStart);
				}
			}

		}
		throw new IllegalStateException("No path from " + grid.getCoordinate(startSquare) + " to " +grid.getCoordinate(goalSquare));
	}
	
	private int manhattan(Square startSquare, Square goalSquare){
		Coordinate start = grid.getCoordinate(startSquare);
		Coordinate goal = grid.getCoordinate(goalSquare);
		return Math.abs(start.getX() - goal.getX()) + Math.abs(start.getY() - (goal.getY()));
	}

	private ArrayList<Coordinate> reconstructPath(SquareContainer squareContainer, SquareContainer startSquareContainer){
		ArrayList<Coordinate> path = new ArrayList<Coordinate>();
		while(!(squareContainer.getPreviousSquareContainer() == null)){
			path.add(grid.getCoordinate(squareContainer.getSquare()));
			squareContainer = squareContainer.getPreviousSquareContainer();
		}
		path.add(grid.getCoordinate(startSquareContainer.getSquare()));
		Collections.reverse(path);
		return path;
	}

	private class SquareContainer implements Comparable<SquareContainer> {

		private Square square;
		private SquareContainer previous;
		private int distanceFromStart;
		private int distanceFromGoal;
		
		public SquareContainer(Square square){
			setSquare(square);
		}
		
		public Square getSquare(){
			return this.square;
		}
		
		public void setSquare(Square square){
			this.square = square;
		}
		
		public SquareContainer getPreviousSquareContainer(){
			return this.previous;
		}
		
		public void setPreviousSquareContainer(SquareContainer squareContainer){
			this.previous = squareContainer;
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
		
		public int compareTo(SquareContainer other) { 
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
					+ ((square == null) ? 0 : square.hashCode());
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
			SquareContainer other = (SquareContainer) obj;
			if (!getOuterType().equals(other.getOuterType())) {
				return false;
			}
			if (square == null) {
				if (other.square != null) {
					return false;
				}
			} else if (!square.equals(other.square)) {
				return false;
			}
			return true;
		}

		private AStar getOuterType() {
			return AStar.this;
		}
		
		

		
		
	}
	

	
}
