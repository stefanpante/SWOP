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
		this.chargedDiscs_coors = new ArrayList<Coordinate>();
		this.powerfail_coors = new ArrayList<Coordinate>();
		this.teleport_coors = new ArrayList<Coordinate>();
		this.initGrid();
		this.directionalPad = new DirectionalPad(new PVector(25, 55), gui);
		this.adjustDirectionalPad();
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
	 * draws the grid onto the screen.
	 */
	@Override
	public void draw() {
		for(SquareGUI square : squares.values()){
			square.draw();
			directionalPad.draw();
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
		this.updateGrenades();

	}

	/**
	 * updates the grenades
	 */
	private void updateGrenades(){
		for(Coordinate coor: grenades_coors){
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


	/**
	 * Changes the player positions.
	 * @param o
	 */
	public void setPlayers(ArrayList<Coordinate> o) {
		players.get(0).setPosition(getPixels(o.get(0)));
		players.get(0).setColor(OConstants.PLAYERBLUE);
		players.get(1).setPosition(getPixels(o.get(1)));



	}


	/**
	 * sets the position of powerfailures
	 * @param o
	 */
	public void setPowerFails(ArrayList<Coordinate> o) {
		this.powerfail_coors = o;
		updatePowerFailures();

	}

	/**
	 * Updates the powerfailures.
	 */
	private void updatePowerFailures(){
		for(Coordinate coor: powerfail_coors){
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


	/**
	 * Sets the light trails of the players.
	 * @param o
	 */
	public void setLightTrails(HashMap<Player, ArrayList<Coordinate>> o) {
		this.lightTrails = o;
		updateLightTrails();
	}

	private void updateLightTrails(){
		int i = 0;
		lightTrails_Squares.clear();
		for(Player player: lightTrails.keySet()){
			ArrayList<Coordinate> playercoor = lightTrails.get(player);
			try{
				PVector pos = getPixels(playercoor.get(0));
				// float width, float height, PVector position, PApplet gui
				SquareGUI s = new SquareGUI(squareWidth, squareHeight, pos, gui);
				s.setColor(OConstants.LIGHTTRAILCOLORS[i][0]);
				lightTrails_Squares.add(s);

			}catch(Exception e){}
			try{
				PVector pos = getPixels(playercoor.get(1));
				// float width, float height, PVector position, PApplet gui
				SquareGUI s = new SquareGUI(squareWidth, squareHeight, pos, gui);
				s.setColor(OConstants.LIGHTTRAILCOLORS[i][1]);
				lightTrails_Squares.add(s);
			}catch(Exception e){}
			try{
				PVector pos = getPixels(playercoor.get(2));
				// float width, float height, PVector position, PApplet gui
				SquareGUI s = new SquareGUI(squareWidth, squareHeight, pos, gui);
				s.setColor(OConstants.LIGHTTRAILCOLORS[i][2]);
				lightTrails_Squares.add(s);
			}catch(Exception e){}

			i++;
		}
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

	/**
	 * Checks if the press of a mouse influences the directionalPad.
	 * @param mouseX	the x coordinate of the mouse
	 * @param mouseY	the y coordinate of the mouse.
	 */
	public void mousePressed(int mouseX, int mouseY) {
		directionalPad.mousePressed(mouseX, mouseY);

	}

	public void resetGrid(){
		items.clear();
		for(Coordinate coor : grenades_coors){
			SquareGUI s = new SquareGUI(squareWidth, squareHeight, getPixels(coor), gui);
			s.setShape(Shapes.lightgrenade);
			items.put(coor, s);
		}
		
		
		for(Coordinate coor: discs_coors){
			if(items.containsKey(coor)){
				items.get(coor).setShape(Shapes.items);
			}
			else{
				SquareGUI s = new SquareGUI(squareWidth, squareHeight, getPixels(coor), gui);
				s.setShape(Shapes.identityDisc);
				items.put(coor, s);
			}
		}
		
		for(Coordinate coor: chargedDiscs_coors){
			if(items.containsKey(coor)){
				items.get(coor).setShape(Shapes.items);
			}
			else{
				SquareGUI s = new SquareGUI(squareWidth, squareHeight, getPixels(coor), gui);
				s.setShape(Shapes.chargedIdentityDisc);
				items.put(coor, s);
			}
		}
		
		powerfailItems.clear();
		powerfails.clear();
		for(Coordinate coor: powerfail_coors){
			if(items.containsKey(coor)){
				SquareGUI s = items.get(coor);
				items.remove(coor);
				s.setShape(Shapes.powerFailureItem);
				powerfailItems.put(coor, s);
			}
			else{
				SquareGUI s = new SquareGUI(squareWidth, squareHeight, getPixels(coor), gui);
				s.setShape(Shapes.powerFail);
				powerfails.put(coor, s);
			}
			
		}
		
		teleports.clear();
		teleportsItem.clear();
		teleportsItemPowerfails.clear();
		for(Coordinate coor: teleport_coors){
			if(items.containsKey(coor)){
				SquareGUI s = items.get(coor);
				items.remove(coor);
				s.setShape(Shapes.teleportItem);
				teleports.put(coor, s);
			}
			
			if(powerfails.containsKey(coor)){
				SquareGUI s = powerfails.get(coor);
				powerfails.remove(coor);
				s.setShape(Shapes.powerFailureTeleport);
				teleportPowerfail.put(coor,s);
			}
			
			if(powerfailItems.containsKey(coor)){
				SquareGUI s = powerfailItems.get(coor);
				powerfailItems.remove(coor);
				s.setShape(Shapes.powerFailureTeleportItem);
				teleportsItemPowerfails.put(coor, s);
			}
		}





	}


}
