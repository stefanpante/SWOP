package gui;

import game.Player;
import gui.button.DirectionalButton;

import item.Item;

import java.util.ArrayList;
import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PVector;
import square.Direction;
import util.Coordinate;
import util.OConstants;

public class GridGui extends GUIElement{


	/**
	 * the directionalpad to be drawn onto the grid.
	 */
	private DirectionalPad directionalPad;

	
	/**
	 * The throwpad used to throw an item.
	 */
	
	private ThrowPad throwPad;
	/**
	 * Number of horizontal cells
	 */
	private int hCells;

	/**
	 * number of vertical cells.
	 */
	private int vCells;

	/**
	 * The squares to be drawn onto the screen.
	 */
	private HashMap<Coordinate, SquareGUI> squares;
	private HashMap<Coordinate, SquareGUI> items;
	private HashMap<Coordinate, SquareGUI> powerfailItems;
	private HashMap<Coordinate, SquareGUI> powerfails;
	private HashMap<Coordinate, SquareGUI> teleports;
	private HashMap<Coordinate, SquareGUI> teleportsItem;
	private HashMap<Coordinate, SquareGUI> teleportPowerfail;
	private HashMap<Coordinate, SquareGUI> teleportsItemPowerfails;



	/**
	 * Coordinates of the items
	 */
	private ArrayList<Coordinate> grenades_coors;
	private ArrayList<Coordinate> discs_coors;
	private ArrayList<Coordinate> chargedDiscs_coors;
	private ArrayList<Coordinate> ff_coors;

	/**
	 * Coordinates of the powerfailures
	 */
	private ArrayList<Coordinate> powerfail_coors;

	/**
	 * Coordinates of teleports
	 */
	private ArrayList<Coordinate> teleport_coors;
	/**
	 * Coordinates of the walls
	 */
	private ArrayList<Coordinate> walls;
	/**
	 * the walls to be drawn on the screen
	 */
	private ArrayList<SquareGUI> walls_squares;
	/**
	 * The players to be drawn on the screen
	 */
	private ArrayList<SquareGUI> players;

	/**
	 * The positions of the lightTrails per player
	 */
	private HashMap<Player,ArrayList<Coordinate>> lightTrails;

	/**
	 * The position of the current player.
	 */
	private Coordinate currentPlayer;


	/**
	 * The width of a square
	 */
	private float squareWidth;

	/**
	 * The height of the square.
	 */
	private float squareHeight;

	/**
	 * 
	 */
	private ArrayList<SquareGUI> lightTrails_Squares;

	/**
	 * Constructs a new grid representation for the gui
	 * @param position
	 * @param gui
	 * @param width
	 * @param height
	 * @param hCells
	 * @param vCells
	 */
	public GridGui(PVector position, PApplet gui, float width, float height, int hCells, int vCells) {
		//float height, float width, PVector position, PApplet gui
		super(width, height, position, gui);

		this.hCells = hCells;
		this.vCells = vCells;
		this.squares = new HashMap<Coordinate, SquareGUI>();

		this.players = new ArrayList<SquareGUI>();
		this.items = new HashMap<Coordinate, SquareGUI>();
		this.powerfailItems = new HashMap<Coordinate, SquareGUI>();
		this.powerfails = new HashMap<Coordinate, SquareGUI>();
		this.teleports  = new HashMap<Coordinate, SquareGUI>();
		this.teleportsItem  = new HashMap<Coordinate, SquareGUI>();
		this.teleportPowerfail = new HashMap<Coordinate, SquareGUI>();
		this.teleportsItemPowerfails = new HashMap<Coordinate, SquareGUI>();
		this.lightTrails_Squares = new ArrayList<SquareGUI>();
		this.walls_squares = new ArrayList<SquareGUI>();
		this.currentPlayer = new Coordinate(0,0);
		this.grenades_coors = new ArrayList<Coordinate>();
		this.discs_coors = new ArrayList<Coordinate>();
		this.ff_coors = new ArrayList<Coordinate>();
		this.chargedDiscs_coors = new ArrayList<Coordinate>();
		this.powerfail_coors = new ArrayList<Coordinate>();
		this.teleport_coors = new ArrayList<Coordinate>();
		this.initGrid();
		float swidth = (width - hCells * OConstants.MARGIN) / hCells;
		float sHeight = (height- vCells * OConstants.MARGIN) / vCells;
		this.directionalPad = new DirectionalPad(new PVector(25, 55), swidth, sHeight, gui);
		this.throwPad = new ThrowPad(new PVector(25,55), swidth, sHeight, gui);
		throwPad.setVisibility(false);
		this.adjustPad2(directionalPad);
		this.adjustPad2(throwPad);
	}
	


	/**
	 * Constructs the actual grid representation
	 */
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

		squareWidth = swidth;
		squareHeight = sHeight;
	}
	
	/**
	 * Accepts a list of coordinates which represents the included squares in the grid.
	 * Non-included squares are removed from the grid.
	 * @param includedSquares	the Squares which are part of the grid.
	 */
	public void adjustGrid(ArrayList<Coordinate> includedSquares){
		ArrayList<Coordinate> toRemove = new ArrayList<Coordinate>();
		for(Coordinate coordinate: squares.keySet()){
			if(!includedSquares.contains(coordinate)){
				toRemove.add(coordinate);
			}
		}
		
		for(Coordinate coordinate: toRemove){
			squares.remove(coordinate);
		}
	}


	/**
	 * draws the grid onto the screen.
	 */
	@Override
	public void draw() {
		for(SquareGUI square : squares.values()){
			square.draw();
			directionalPad.draw();
			throwPad.draw();
		}

		

		
		for(SquareGUI item: items.values()){
			item.draw();
		}
		
		for(SquareGUI item: powerfailItems.values()){
			item.draw();
		}
		
		for(SquareGUI powerfail: powerfails.values()){
			powerfail.draw();
		}
		
		for(SquareGUI teleport: teleports.values()){
			teleport.draw();
		}
		
		for(SquareGUI teleport: teleportsItem.values()){
			teleport.draw();
		}
		
		for(SquareGUI teleport: teleportPowerfail.values()){
			teleport.draw();
		}
		
		for(SquareGUI teleport: teleportsItemPowerfails.values()){
			teleport.draw();
		}
		
		for(SquareGUI player: players){
			player.draw();
		}
		
		for(SquareGUI light: lightTrails_Squares){
			light.draw();
		}

		for(SquareGUI wall: walls_squares){
			wall.draw();
		}
		

	}

	/**
	 * The Grid representation itself is not hittable, so this method
	 * always returns false
	 */
	@Override
	public boolean mouseHit(int mouseX, int mouseY) {
		return false;

	}


	/**
	 * Hovers over the grid.
	 * @param mouseX	the x coordinate of the mouse
	 * @param mouseY	the y coordinate of the mouse
	 */
	public void hover(int mouseX, int mouseY) {
		for(SquareGUI square: squares.values()){
			square.hover(mouseX, mouseY);
		}
		directionalPad.hover(mouseX, mouseY);
		throwPad.hover(mouseX, mouseY);
	}


	/**
	 * Sets the coordinates of the walls.
	 * @param o
	 */
	public void setWalls(ArrayList<Coordinate> o) {
		this.walls = o;
		updateWalls();

	}


	private void updateWalls(){
		walls_squares.clear();
		for(Coordinate wall: walls){
			SquareGUI s = new SquareGUI(squareWidth, squareHeight, getPixels(wall), gui);
			s.setVisibility(true);
			s.setShape(Shapes.wall);
			walls_squares.add(s);
		}
	}


	/**
	 * Sets the positions of the grenades.
	 * @param o
	 */
	public void setGrenades(ArrayList<Coordinate> o) {
		this.grenades_coors = o;
	}

	/**
	 * Changes the player positions.
	 * @param o
	 */
	public void setPlayers(HashMap<Player,Coordinate> players) {
		this.players.clear();
		for(Player player : players.keySet() ){
			if(player.getID() == 1){
				SquareGUI s = new SquareGUI(squareWidth, squareHeight, getPixels(players.get(player)), gui);
				s.setColor(OConstants.PLAYERBLUE);
				this.players.add(s);
			}
			if(player.getID() == 2){
				SquareGUI s = new SquareGUI(squareWidth, squareHeight, getPixels(players.get(player)), gui);
				s.setColor(OConstants.PLAYERRED);
				this.players.add(s);
			}
		}



	}


	/**
	 * sets the position of powerfailures
	 * @param o
	 */
	public void setPowerFails(ArrayList<Coordinate> o) {
		this.powerfail_coors = o;

	}



	/**
	 * Sets the light trails of the players.
	 * @param o
	 */
	public void setLightTrails(HashMap<Player, ArrayList<Coordinate>> o) {
		this.lightTrails = o;
		updateLightTrails();
	}

	private void updateLightTrails(){
		lightTrails_Squares.clear();
		for(Player player: lightTrails.keySet()){
			ArrayList<Coordinate> playercoor = lightTrails.get(player);
			try{
				PVector pos = getPixels(playercoor.get(0));
				SquareGUI s = new SquareGUI(squareWidth, squareHeight, pos, gui);
				s.setColor(OConstants.LIGHTTRAILCOLORS[player.getID() -1][0]);
				lightTrails_Squares.add(s);

			}catch(Exception e){}
			try{
				PVector pos = getPixels(playercoor.get(1));
				SquareGUI s = new SquareGUI(squareWidth, squareHeight, pos, gui);
				s.setColor(OConstants.LIGHTTRAILCOLORS[player.getID() -1][1]);
				lightTrails_Squares.add(s);
			}catch(Exception e){}
			try{
				PVector pos = getPixels(playercoor.get(2));
				SquareGUI s = new SquareGUI(squareWidth, squareHeight, pos, gui);
				s.setColor(OConstants.LIGHTTRAILCOLORS[player.getID() -1][2]);
				lightTrails_Squares.add(s);
			}catch(Exception e){}

		}
	}


	public void setCurrentPlayer(Coordinate coordinate) {
		this.currentPlayer = coordinate;

		adjustPad2(directionalPad);
		adjustPad2(throwPad);

	}

	private void adjustPad2(DirectionalPad pad){
		pad.setPosition(getPixels(currentPlayer));
		HashMap<Direction, DirectionalButton> buttons = pad.getButtons();
		
		for(Direction direction: buttons.keySet()){
			Coordinate coor = currentPlayer.getNeighbor(direction);
			DirectionalButton b = buttons.get(direction);
			b.setPosition(getPixels(coor));
			if(!squares.containsKey(coor))
				b.setVisibility(false);
		}
	}
	
	
	private PVector getPixels(Coordinate coor) {
		if(squares.containsKey(coor)){
			return squares.get(coor).getPosition();
		}

		return new PVector();
	}

	/**
	 * Checks if the press of a mouse influences the directionalPad.
	 * @param mouseX	the x coordinate of the mouse
	 * @param mouseY	the y coordinate of the mouse.
	 */
	public void mousePressed(int mouseX, int mouseY) {
		directionalPad.mousePressed(mouseX, mouseY);
		throwPad.mousePressed(mouseX, mouseY);

	}

	private HashMap<Coordinate, ArrayList<Item>> items_ = new HashMap<Coordinate, ArrayList<Item>>();
	
	public void setItems(HashMap<Coordinate,ArrayList<Item>> items){
		this.items_ = items;
	}
	
	public void resetGrid(){
		items.clear();
		for(Coordinate coor: items_.keySet()){
			ArrayList<Item> it = items_.get(coor);
			if(it.size() == 0){
				continue;
			}
			if(it.size() == 1){
				SquareGUI s = new SquareGUI(squareWidth, squareHeight, getPixels(coor), gui);
				s.setShape(Shapes.getShape(it.get(0)));
				items.put(coor, s);
			}
			
			if(it.size() >= 2){
				SquareGUI s = new SquareGUI(squareWidth, squareHeight, getPixels(coor), gui);
				s.setShape(Shapes.items);
				items.put(coor, s);
			}
		}
		
		for(Coordinate coor: powerfail_coors){
			if(items.containsKey(coor)){
				SquareGUI s = items.get(coor);
				items.remove(coor);
				s.setShape(Shapes.powerFailureItem);
			}
			
			else{
				SquareGUI s = new SquareGUI(squareWidth, squareHeight, getPixels(coor), gui);
				s.setShape(Shapes.powerFail);
				powerfails.put(coor, s);
			}
			
		}
	}
	
	public DirectionalPad getDirectionalPad(){
		return this.directionalPad;
	}



	public ThrowPad getThrowPad() {
		return this.throwPad;
	}

}
