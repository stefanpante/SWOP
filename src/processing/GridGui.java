package processing;

import java.util.ArrayList;
import java.util.HashMap;

import player.Player;
import processing.core.PVector;
import util.Coordinate;

public class GridGui implements Drawable{
	
	/**
	 * the margin between the cells of the grid.
	 */
	public static int MARGIN = 5;

	/**
	 * The parent object used to draw;
	 */
	private ObjectronGUI objectronGUI;
	
	/**
	 * The coordinates of the top left corner of the grid 
	 */
	private PVector position;
	
	/**
	 * the width in pixels of the grid.
	 */
	private float width;
	
	/**
	 * The height of the grid in pixels.
	 */
	private float height;
	
	/**
	 * Number of horizontal cells
	 */
	private int hCells;
	
	/**
	 * number of vertical cells.
	 */
	private int vCells;
	
	/**
	 * The list of squares to be drawn onto the grid.
	 */
	private HashMap<Coordinate, SquareGUI> squares;
	
	/**
	 * 
	 */
	private ArrayList<Coordinate> walls;
	private ArrayList<Coordinate> grenades;
	private ArrayList<Coordinate> players;
	private ArrayList<Coordinate> powerFails;
	
	private HashMap<Player,ArrayList<Coordinate>> lightTrails;
	
	public GridGui(PVector position, ObjectronGUI objectronGUI, float width, float height, int hCells, int vCells) {
		this.position = position;
		this.objectronGUI = objectronGUI;
		this.height = height;
		this.width = width;
		this.hCells = hCells;
		this.vCells = vCells;
		this.squares = new HashMap<Coordinate, SquareGUI>();
		this.initGrid();
	}
	

	private void initGrid() {
		float x = position.x;
		float y = position.y;
		
		float swidth = (width - hCells * MARGIN) / hCells;
		float sHeight = (height- vCells * MARGIN) / vCells;
		System.out.println(swidth);
		for(int i = 0; i < vCells; i++){
			for(int j = 0; j < hCells; j++){
				SquareGUI s = new SquareGUI(objectronGUI);
				s.setHeight(sHeight);
				s.setWidth(swidth);
				s.setPosition(x, y);
				squares.put(new Coordinate(j,i),s);
				x += swidth + MARGIN;
			}
			x = position.x;
			y += sHeight + MARGIN;
		}
		
	}


	/**
	 * draws the grid onto the screen.
	 */
	@Override
	public void draw() {
		for(SquareGUI square : squares.values()){
			square.draw();
		}
		
		
		
	}

	//
	@Override
	public boolean mouseHit(int mouseX, int mouseY) {
		for(SquareGUI square : squares.values()){
			if(square.mouseHit(mouseX, mouseY))
				return true;
		}
		
		return false;
		
		
	}


	@Override
	public void mouseOver(int mouseX, int mouseY) {
		for(SquareGUI square: squares.values()){
			square.mouseOver(mouseX, mouseY);
		}
	}


	public void setWalls(ArrayList<Coordinate> o) {
		// TODO Auto-generated method stub
		
	}


	public void setGrenades(ArrayList<Coordinate> o) {
		// TODO Auto-generated method stub
		
	}


	public void setPlayers(ArrayList<Coordinate> o) {
		// TODO Auto-generated method stub
		
	}


	public void setPowerFails(ArrayList<Coordinate> o) {
		// TODO Auto-generated method stub
		
	}


	public void setLightTrails(HashMap<Player, ArrayList<Coordinate>> o) {
		// TODO Auto-generated method stub
		
	}


	public void setCurrentPlayer(Coordinate o) {
		// TODO Auto-generated method stub
		
	}


	public void setCurrentPlayer(Coordinate o) {
		// TODO Auto-generated method stub
		
	}

	
}
