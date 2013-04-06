package processing;

import java.util.ArrayList;
import java.util.HashMap;

import player.Player;
import processing.core.PVector;
import util.Coordinate;

public class GridGui implements Drawable{


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
	private Coordinate currentPlayer;

	public GridGui(PVector position, ObjectronGUI objectronGUI, float width, float height, int hCells, int vCells) {
		this.position = position;
		this.objectronGUI = objectronGUI;
		this.height = height;
		this.width = width;
		this.hCells = hCells;
		this.vCells = vCells;
		this.squares = new HashMap<Coordinate, SquareGUI>();
		walls = new ArrayList<Coordinate>();
		grenades = new ArrayList<Coordinate>();
		players = new ArrayList<Coordinate>();
		powerFails = new ArrayList<Coordinate>();
		lightTrails = new HashMap<Player, ArrayList<Coordinate>>();
		this.initGrid();
	}


	private void initGrid() {
		float x = position.x;
		float y = position.y;

		float swidth = (width - hCells * OConstants.MARGIN) / hCells;
		float sHeight = (height- vCells * OConstants.MARGIN) / vCells;
		for(int i = 0; i < vCells; i++){
			for(int j = 0; j < hCells; j++){
				SquareGUI s = new SquareGUI(objectronGUI);
				s.setHeight(sHeight);
				s.setWidth(swidth);
				s.setPosition(x, y);
				squares.put(new Coordinate(j,i),s);
				x += swidth + OConstants.MARGIN;
			}
			x = position.x;
			y += sHeight + OConstants.MARGIN;
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
		this.walls = o;
		resetGrid();

	}


	public void setGrenades(ArrayList<Coordinate> o) {
		this.grenades = o;
		resetGrid();

	}


	public void setPlayers(ArrayList<Coordinate> o) {
		this.players = o;
		resetGrid();

	}


	public void setPowerFails(ArrayList<Coordinate> o) {
		this.powerFails = o;
		resetGrid();

	}


	public void setLightTrails(HashMap<Player, ArrayList<Coordinate>> o) {
		this.lightTrails = o;
		resetGrid();

	}


	public void setCurrentPlayer(Coordinate coordinate) {
		this.currentPlayer = coordinate;
		resetGrid();

	}

	public void resetGrid(){
		for(SquareGUI s : squares.values()){
			s.reset();
		}



		for(Coordinate coor: grenades){
			SquareGUI s = squares.get(coor);
			squares.get(coor).setShape(Shapes.lightgrenade);

		}

		for(Coordinate coor: powerFails){
			SquareGUI s = squares.get(coor);
			if(s.hasShape()){
				s.setShape(Shapes.powerFailureItem);
			}
			else s.setShape(Shapes.powerFail);

		}



		for(Coordinate coor: walls){
			squares.get(coor).setShape(Shapes.wall);
		}

		try{
			SquareGUI s = squares.get(players.get(0));
			if(s != null)
				s.setColor(OConstants.PLAYERBLUE);
		} catch(Exception jonas){}

		try{
			SquareGUI s = squares.get(players.get(1));
			if(s != null)
				s.setColor(OConstants.PLAYERRED);
		}
		catch (Exception dieter){}




	}

}