package util;

import grid.Grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

import square.Square;

public class AStar {
	
	private ArrayList<SquareContainer> closedSet;
	private PriorityQueue<SquareContainer> openSet;
	private Grid grid;
	
	public AStar(Grid grid){
		this.grid = grid;
		closedSet = new ArrayList<SquareContainer>();
		openSet = new PriorityQueue<SquareContainer>();
		boxAllSquares(grid);
	}
	
	private void boxAllSquares(Grid grid2) {
		// TODO: Morgen
		// squareContainer.setHeuristicDistanceFromGoal(manhattan(grid.getCoordinate(square), grid.getCoordinate(square)));
	}

	public ArrayList<Coordinate> shortestPath(SquareContainer start, SquareContainer goal){			
		openSet.add(start);

		while(!openSet.isEmpty()){
			SquareContainer current = openSet.remove(); 
			closedSet.add(current);

			if(current.equals(goal)){
				return reconstructPath(current,start);
			}

			for(SquareContainer neighbour : current.getNeighbors()){
				boolean neighbourIsBetter;

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
		throw new IllegalStateException();
	}
	
	private int manhattan(Coordinate start, Coordinate goal){
		return 0;
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


}
