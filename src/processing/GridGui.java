package processing;

import java.util.ArrayList;
import java.util.HashMap;

import player.Player;
import processing.button.DirectionalButton;
import processing.core.PApplet;
import processing.core.PVector;
import square.Direction;
import util.Coordinate;

public class GridGui extends GUIElement{


	/**
	 * the directionalpad to be drawn onto the grid.
	 */
	private DirectionalPad directionalPad;

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
	private ArrayList<SquareGUI> players;

	private ArrayList<Coordinate> walls;
	private ArrayList<Coordinate> grenades;
	//private ArrayList<Coordinate> players;
	private ArrayList<Coordinate> powerFails;
	private HashMap<Player,ArrayList<Coordinate>> lightTrails;
	private Coordinate currentPlayer;

	public GridGui(PVector position, PApplet gui, float width, float height, int hCells, int vCells) {
		//float height, float width, PVector position, PApplet gui
		super(width, height, position, gui);

		this.hCells = hCells;
		this.vCells = vCells;
		this.squares = new HashMap<Coordinate, SquareGUI>();
		this.directionalPad = new DirectionalPad(new PVector(25, 55), gui);

		this.walls = new ArrayList<Coordinate>();
		this.grenades = new ArrayList<Coordinate>();
		this.currentPlayer = new Coordinate(0, 0);
		this.players = new ArrayList<SquareGUI>();
		this.powerFails = new ArrayList<Coordinate>();
		this.lightTrails = new HashMap<Player, ArrayList<Coordinate>>();
		this.initGrid();
		this.adjustDirectionalPad();
	}


	private void initGrid() {
		float x = position.x;
		float y = position.y;

		float swidth = (width - hCells * OConstants.MARGIN) / hCells;
		float sHeight = (height- vCells * OConstants.MARGIN) / vCells;
		for(int i = 0; i < vCells; i++){
			for(int j = 0; j < hCells; j++){
				PVector pos = new PVector(x,y);
				// PVector position, float width, float height, PApplet gui
				SquareGUI s = new SquareGUI( swidth, sHeight,pos, gui);
				squares.put(new Coordinate(j,i),s);
				x += swidth + OConstants.MARGIN;
			}
			x = position.x;
			y += sHeight + OConstants.MARGIN;
		}
		SquareGUI player1 = new SquareGUI(swidth, sHeight, new PVector(), gui);
		player1.setColor(OConstants.PLAYERBLUE);
		players.add(player1);
		
		SquareGUI player2 = new SquareGUI(swidth, sHeight, new PVector(), gui);
		player2.setColor(OConstants.PLAYERRED);
		players.add(player2);

	}


	/**
	 * draws the grid onto the screen.
	 */
	public void draw() {
		for(SquareGUI square : squares.values()){
			square.draw();
			directionalPad.draw();
		}
		
		for(SquareGUI player: players){
			player.draw();
		}



	}

	public boolean mouseHit(int mouseX, int mouseY) {
		return false;

	}


	public void hover(int mouseX, int mouseY) {
		for(SquareGUI square: squares.values()){
			square.hover(mouseX, mouseY);
		}
		directionalPad.hover(mouseX, mouseY);
	}


	public void setWalls(ArrayList<Coordinate> o) {
		this.walls = o;

	}
	
	private void updateWalls(){
		for(Coordinate coor: walls){
			squares.get(coor).setShape(Shapes.wall);
		}
	}


	public void setGrenades(ArrayList<Coordinate> o) {
		this.grenades = o;
		this.updateGrenades();

	}
	
	private void updateGrenades(){
		for(Coordinate coor: grenades){
			if(squares.get(coor).getShape() == null){
				squares.get(coor).setShape(Shapes.lightgrenade);
			}
			else if(squares.get(coor).getShape() == Shapes.powerFail){
				squares.get(coor).setShape(Shapes.powerFailureItem);
			}
			else{
				//TODO: set multiple items.
				//squares.get(coor).setShape(Shapes.)
			}
		}
	}


	public void setPlayers(ArrayList<Coordinate> o) {
		players.get(0).setPosition(getPixels(o.get(0)));
		players.get(0).setColor(OConstants.PLAYERBLUE);
		players.get(1).setPosition(getPixels(o.get(1)));
		


	}


	public void setPowerFails(ArrayList<Coordinate> o) {
		this.powerFails = o;
		updatePowerFailures();

	}
	
	private void updatePowerFailures(){
		for(Coordinate coor: powerFails){
			if(squares.get(coor).getShape() == null){
				squares.get(coor).setShape(Shapes.powerFail);
			}
			else if(squares.get(coor).getShape() == Shapes.wall){
				
			}
			else if(squares.get(coor).getShape() != Shapes.powerFail){
				squares.get(coor).setShape(Shapes.powerFailureItem);
			}
			else{
				//TODO: set multiple items.
				//squares.get(coor).setShape(Shapes.)
			}
		}
	}


	public void setLightTrails(HashMap<Player, ArrayList<Coordinate>> o) {
		this.lightTrails = o;

	}


	public void setCurrentPlayer(Coordinate coordinate) {
		this.currentPlayer = coordinate;
		
		adjustDirectionalPad();

	}

	/**
	 * Sets directions that are not applicable to false.
	 */
	private void adjustDirectionalPad(){
		directionalPad.setPosition(getPixels(currentPlayer));
		HashMap<Direction, DirectionalButton> buttons = directionalPad.getButtons();

		Coordinate coor = new Coordinate(currentPlayer.getX()-1, currentPlayer.getY());
		DirectionalButton b = buttons.get(Direction.WEST);
		b.setPosition(getPixels(coor));
		if(!squares.containsKey(coor))
			b.setVisibility(false);

		coor = new Coordinate(currentPlayer.getX()+1, currentPlayer.getY());
		b = buttons.get(Direction.EAST);
		b.setPosition(getPixels(coor));
		if(!squares.containsKey(coor))
			b.setVisibility(false);

		coor = new Coordinate(currentPlayer.getX(), currentPlayer.getY()-1);
		b = buttons.get(Direction.NORTH);
		b.setPosition(getPixels(coor));
		if(!squares.containsKey(coor))
			b.setVisibility(false);

		coor = new Coordinate(currentPlayer.getX(), currentPlayer.getY()+1);
		b = buttons.get(Direction.SOUTH);
		b.setPosition(getPixels(coor));
		if(!squares.containsKey(coor))
			b.setVisibility(false);

		coor = new Coordinate(currentPlayer.getX()-1, currentPlayer.getY()-1);
		b = buttons.get(Direction.NORTHWEST);
		b.setPosition(getPixels(coor));
		if(!squares.containsKey(coor))
			b.setVisibility(false);

		coor = new Coordinate(currentPlayer.getX()-1, currentPlayer.getY() +1);
		b = buttons.get(Direction.SOUTHWEST);
		b.setPosition(getPixels(coor));
		if(!squares.containsKey(coor))
			b.setVisibility(false);

		coor = new Coordinate(currentPlayer.getX()+1, currentPlayer.getY()-1);
		b = buttons.get(Direction.NORTHEAST);
		b.setPosition(getPixels(coor));
		if(!squares.containsKey(coor))
			b.setVisibility(false);

		coor = new Coordinate(currentPlayer.getX()+1, currentPlayer.getY() +1);
		b = buttons.get(Direction.SOUTHEAST);
		b.setPosition(getPixels(coor));
		if(!squares.containsKey(coor))
			b.setVisibility(false);
	}

	private PVector getPixels(Coordinate coor) {
		if(squares.containsKey(coor)){
			return squares.get(coor).getPosition();
		}

		return new PVector();
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





	}


	public void mousePressed(int mouseX, int mouseY) {
		directionalPad.mousePressed(mouseX, mouseY);
		
	}

}
