package util;

import java.util.ArrayList;

import square.Square;

public class SquareContainer {

	private Square square;
	private SquareContainer previous;
	private int distanceFromStart;
	private int distanceFromGoal;
	
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
	
}
