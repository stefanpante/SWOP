package gui;

import effect.Effect;
import effect.imp.ForceFieldEffect;
import effect.imp.PowerFailureEffect;
import game.Player;
import gui.button.DirectionalButton;
import item.Flag;
import item.Item;
import item.LightGrenade;
import item.Teleport;
import org.jetbrains.annotations.NotNull;
import processing.core.PApplet;
import processing.core.PVector;
import util.Coordinate;
import util.Direction;
import util.OConstants;

import java.util.ArrayList;
import java.util.HashMap;

public class GridGui extends GUIElement{


	/**
	 * the directionalpad to be drawn onto the grid.
	 */
	@NotNull
    private final DirectionalPad directionalPad;


	/**
	 * The throwpad used to throw an item.
	 */
	@NotNull
    private final ThrowPad throwPad;

	/**
	 * The squares to be drawn onto the screen.
	 */
	@NotNull
    private final HashMap<Coordinate, SquareGUI> squares;
	@NotNull
    private final HashMap<Coordinate, SquareGUI> items;
	@NotNull
    private final HashMap<Coordinate, PVector> positions;

	/**
	 * the walls to be drawn on the screen
	 */
	@NotNull
    private final ArrayList<SquareGUI> walls_squares;
	@NotNull
    private final ArrayList<SquareGUI> forcefields;
	@NotNull
    private final ArrayList<SquareGUI> powerfails;
	@NotNull
    private final ArrayList<SquareGUI> lightTrails_Squares;
	@NotNull
    private final ArrayList<SquareGUI> effects;

	/**
	 * The players to be drawn on the screen
	 */
	@NotNull
    private final ArrayList<SquareGUI> players;

	/**
	 * The position of the current player.
	 */
	private Coordinate currentPlayer;

	/**
	 * The width of a square
	 */
	private final float squareWidth;

	/**
	 * The height of the square.
	 */
	private final float squareHeight;

	@NotNull
    private final Label gridLabel;

	/**
	 * Constructs a new grid representation for the gui
	 * @param position
	 * @param gui
	 * @param width
	 * @param height
	 * @param hCells
	 * @param vCells
	 */
	public GridGui(@NotNull PVector position, PApplet gui, float width, float height, int hCells, int vCells) {
		//float height, float width, PVector position, PApplet gui
		super(width, height, position, gui);

		this.forcefields = new ArrayList<>();
		this.powerfails = new ArrayList<>();
		this.squares = new HashMap<>();
		this.players = new ArrayList<>();
		this.items = new HashMap<>();
		this.effects = new ArrayList<>();
		this.lightTrails_Squares = new ArrayList<>();
		this.walls_squares = new ArrayList<>();
		this.currentPlayer = new Coordinate(0,0);
		this.positions = new HashMap<>();
		this.gridLabel = new Label(width - OConstants.MARGIN, new PVector(position.x, position.y -30), "The grid", gui);
		this.initGrid(hCells, vCells);
		this.squareWidth = (width - hCells * OConstants.MARGIN) / hCells;
		this.squareHeight = (height- vCells * OConstants.MARGIN) / vCells;
		//public DirectionalPad(PVector position, float buttonWidth, float buttonHeight,PApplet gui){
		this.directionalPad = new DirectionalPad(new PVector(25, 55), squareWidth, squareHeight, gui);
		this.throwPad = new ThrowPad(new PVector(25,55), squareWidth, squareHeight, gui);
		throwPad.setVisibility(false);
		this.adjustPad(directionalPad);
		this.adjustPad(throwPad);
	}

	@NotNull
    public Label getLabel(){
		return this.gridLabel;
	}



	/**
	 * Constructs the actual grid representation
	 */
	private void initGrid(int hCells, int vCells) {
		float x = position.x;
		float y = position.y;

		float swidth = (width - hCells * OConstants.MARGIN) / hCells;
		float sHeight = (height- vCells * OConstants.MARGIN) / vCells;
		for(int i = 0; i < vCells; i++){
			for(int j = 0; j < hCells; j++){
				PVector pos = new PVector(x,y);
				SquareGUI s = new SquareGUI( swidth, sHeight,pos, gui);
				squares.put(new Coordinate(j,i),s);
				positions.put(new Coordinate(j,i),pos);
				x += swidth + OConstants.MARGIN;
			}
			x = position.x;
			y += sHeight + OConstants.MARGIN;
		}
	}

	/**
	 * Accepts a list of coordinates which represents the included squares in the grid.
	 * Non-included squares are removed from the grid.
	 * @param includedSquares	the Squares which are part of the grid.
	 */
	public void adjustGrid(@NotNull ArrayList<Coordinate> includedSquares){
		ArrayList<Coordinate> toRemove = new ArrayList<>();

		for(Coordinate coordinate: squares.keySet()){
			if(!includedSquares.contains(coordinate)){
				toRemove.add(coordinate);
			}
		}

		for(Coordinate coordinate: toRemove){
			squares.remove(coordinate);
		}
	}
	
	public void updateLightTrails(@NotNull HashMap<Player, ArrayList<Coordinate>> o){
		lightTrails_Squares.clear();
		for(Player player: o.keySet()){
			ArrayList<Coordinate> playercoor = o.get(player);
			int id = player.getID();
			int alpha = 150;
			for(Coordinate coor: playercoor){
				SquareGUI s = new SquareGUI(squareWidth, squareHeight, getPixels(coor), gui);
				int color = OConstants.PLAYERCOLORS[id -1].getTransparantIntColor(alpha);
				s.setColor(color);
				lightTrails_Squares.add(s);
				alpha -= 30;
			}
		}
	}


	/**
	 * draws the grid onto the screen.
	 */
	@Override
	public void draw() {
		gridLabel.draw();
		for(SquareGUI square : squares.values())
			square.draw();

		for(SquareGUI ff: forcefields)
			ff.draw();

		for(SquareGUI pf: powerfails)
			pf.draw();

		for(SquareGUI item: items.values())
			item.draw();

		for(SquareGUI light: lightTrails_Squares)
			light.draw();

		for(SquareGUI wall: walls_squares)
			wall.draw();
		
		for(SquareGUI effect: effects){
			effect.draw();
		}
		
		for(SquareGUI light: lightTrails_Squares)
			light.draw();
		for(SquareGUI player: players)
			player.draw();


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

	public void updateWalls(@NotNull ArrayList<Coordinate> o ){
		walls_squares.clear();
		for(Coordinate wall: o){
			SquareGUI s = new SquareGUI(squareWidth, squareHeight, getPixels(wall), gui);
			s.setVisibility(true);
			s.setShape(Shapes.wall);
			walls_squares.add(s);
		}
	}

	/**
	 * Changes the player positions.
	 */
	public void updatePlayers(@NotNull HashMap<Player,Coordinate> players) {
		this.players.clear();
		for(Player player : players.keySet() ){
			int id  = player.getID();
			SquareGUI s = new SquareGUI(squareWidth, squareHeight, getPixels(players.get(player)), gui);
			s.setColor(OConstants.PLAYERCOLORS[id -1].getIntColor());
			this.players.add(s);
		}
	}

	public void updatePowerFailures(@NotNull ArrayList<Coordinate> o){
		powerfails.clear();
		for(Coordinate coor: o){
			SquareGUI s = new SquareGUI(squareWidth, squareHeight, getPixels(coor), gui);
			s.setColor(OConstants.POWERFAIL_COLOR.getTransparantIntColor(128));
			powerfails.add(s);
		}
	}

	public void setCurrentPlayer(Coordinate coordinate) {
		this.currentPlayer = coordinate;

		adjustPad(directionalPad);
		adjustPad(throwPad);

	}

	private void adjustPad(@NotNull DirectionalPad pad){
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
		if(positions.containsKey(coor)){
			return positions.get(coor);
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



	public void updateItems(@NotNull HashMap<Coordinate,ArrayList<Item>> o){
		items.clear();

		for(Coordinate coor: o.keySet()){
			SquareGUI s = new SquareGUI(squareWidth, squareHeight, getPixels(coor), gui);
			s.setColor(gui.color(255,0));
			ArrayList<Item> it = o.get(coor);

			if(it.size() == 1){
				if(it.get(0) instanceof LightGrenade){
					LightGrenade lg = (LightGrenade) it.get(0);
					if(!lg.isActive())
						s.setShape(Shapes.getShape(lg));
				}
				else{
					s.setShape(Shapes.getShape(it.get(0)));
				}
			}

			if(it.size() >= 2){
				if(containsTeleport(it)){
					s.setShape(Shapes.teleportItem);
				}

				if(containsFlag(it)){
					s.setShape(Shapes.getFlagItem(it));
				}
			}

			items.put(coor,s);
		}
	}

	private boolean containsTeleport(@NotNull ArrayList<Item> items){
		for(Item item: items){
			if(item instanceof Teleport)
				return true;
		}

		return false;
	}

	private boolean containsFlag(@NotNull ArrayList<Item> items){
		for(Item item: items){
			if(item instanceof Flag)
				return true;
		}
		return false;
	}

	@NotNull
    public DirectionalPad getDirectionalPad(){
		return this.directionalPad;
	}

	@NotNull
    public ThrowPad getThrowPad() {
		return this.throwPad;
	}

	public void updateEffects(@NotNull HashMap<Coordinate, ArrayList<Effect>> o) {
		effects.clear();
		for(Coordinate coordinate: o.keySet()){
			ArrayList<Effect> effects = o.get(coordinate);
			PVector position = getPixels(coordinate);
			for(Effect effect: effects){
				
				SquareGUI s = new SquareGUI(squareWidth, squareHeight, position, gui);
				int color = getEffectColor(effect);
				if(color != -1){
					s.setColor(color);
					this.effects.add(s);
				}
			}
		}
	}
	
	// TODO this needs to be implemented when i know how the effects are named
	private int getEffectColor(Effect effect){
		if(effect instanceof ForceFieldEffect){
			System.out.println("Forcefield color applied");
			return OConstants.FORCEFIELD_COLOR.getTransparantIntColor(70);
		}
		if(effect instanceof PowerFailureEffect){
			return OConstants.POWERFAIL_COLOR.getTransparantIntColor(70);
		}
		return -1;
	}

}
