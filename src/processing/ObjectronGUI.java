package processing;

import item.Item;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JOptionPane;

import controlP5.Bang;
import controlP5.ControlP5;
import controlP5.Group;
import controller.GameHandler;
import controller.ProcessingHandler;

import player.Player;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;
import square.Direction;
import util.Coordinate;

public class ObjectronGUI extends PApplet implements PropertyChangeListener{

	/**
	 * SearialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	GridGui grid;
	
	private Shapes shapes;
	ProcessingHandler obj;
	ControlP5 inputController;
	/**
	 * initializes the objectron gui
	 */
	public void setup(){
		// sets the size from the applet to a fourth of the screen.
		size(850, 550);
		shapes = new Shapes(this);
		// sets the framerate to 60 frames per second.
		//frameRate(60);
		PVector position = new PVector(25, 25);
		this.grid = new GridGui(position, this, 500,500, 10, 10);
		obj = new ProcessingHandler(this);
		inputController = new ControlP5(this);
		
		squareInventoryGroup = inputController.addGroup("Square Inventory");
		squareInventoryGroup.setPosition(new PVector(550,35));
		squareInventoryGroup.setWidth(275);
		squareInventoryGroup.setBackgroundHeight(100);
        squareInventoryGroup.setBackgroundColor(OConstants.LIGHTER_GREY);
        squareInventoryGroup.setColorBackground(OConstants.PLAYERBLUE);
		
        playerInventoryGroup = inputController.addGroup("Player Inventory");
        playerInventoryGroup.setPosition(new PVector(550,155));
        playerInventoryGroup.setWidth(275);
        playerInventoryGroup.setBackgroundHeight(100);
        playerInventoryGroup.setBackgroundColor(OConstants.LIGHTER_GREY);
        playerInventoryGroup.setColorBackground(OConstants.PLAYERBLUE);        
        
        directionalpath = new DirectionalPath(new PVector(25, 25), this);
        
        
	}
	
	private DirectionalPath directionalpath;
	public void moveLeft(){
		obj.getMoveHandler().move(Direction.EAST);
	}
	Bang bang;
	Group squareInventoryGroup;
	Group playerInventoryGroup;
	float width = 50;
	float height = 50;
	float margin = 5;

	private Object currentPlayerLabel;

	private ArrayList<Item> squareInventory;

	private ArrayList<Item> playerInventory;
	
	/**
	 * Draws the entire game
	 */
	public void draw(){
		background(color(255));
		grid.draw();
		grid.mouseOver(mouseX, mouseY);
		grid.mouseOver(pmouseX, pmouseY);
		directionalpath.draw();
		directionalpath.mouseOver(mouseX, mouseY);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Object o = evt.getNewValue();
		if(o == null) System.out.println("Event object is null");
        if (evt.getPropertyName().equals(GameHandler.WALLS_PROPERTY)) {
        	this.grid.setWalls((ArrayList<Coordinate>)o);
        }else if(evt.getPropertyName().equals(GameHandler.GRENADES_PROPERTY)){
        	this.grid.setGrenades((ArrayList<Coordinate>)o);
        }else if(evt.getPropertyName().equals(GameHandler.PLAYERS_PROPERTY)){
        	this.grid.setPlayers((ArrayList<Coordinate>)o);
        }else if(evt.getPropertyName().equals(GameHandler.POWER_FAILS_PROPERTY)){
        	this.grid.setPowerFails((ArrayList<Coordinate>)o);
        }else if(evt.getPropertyName().equals(GameHandler.LIGHT_TRAILS_PROPERTY)) {
        	this.grid.setLightTrails((HashMap<Player,ArrayList<Coordinate>>) o);
        }else if(evt.getPropertyName().equals(GameHandler.CURRENT_PLAYER_PROPERTY)){
        	String playerName = (String)o;
        	if(!this.currentPlayerName.equals(playerName)){
        		this.currentPlayerName = playerName;
        		showMessage("It's now "+playerName+ "'s turn.");
        	}
        }else if(evt.getPropertyName().equals(GameHandler.CURRENT_POSITION_PROPERTY)){
        	this.grid.setCurrentPlayer((Coordinate)o);
        }else if(evt.getPropertyName().equals(GameHandler.SQUARE_INVENTORY_PROPERTY)){
        	this.squareInventory = (ArrayList<Item>)o;
        }else if(evt.getPropertyName().equals(GameHandler.PLAYER_INVENTORY_PROPERTY)){
        	this.playerInventory = (ArrayList<Item>)o;
        }else if(evt.getPropertyName().equals(GameHandler.END_TURN_PROPERTY)){
        	confirmEndTurn((String)o);
        }else if(evt.getPropertyName().equals(GameHandler.MESSAGE_PROPERTY)){
        	JOptionPane.showMessageDialog(frame, (String)o);
        }else if(evt.getPropertyName().equals(GameHandler.WIN_PROPERTY)){
        	String player = (String)o;
        	JOptionPane.showMessageDialog(frame, player+ " has won the game!");
        }else if(evt.getPropertyName().equals(GameHandler.LOSE_PROPERTY)){
        	String player = (String)o;
        	JOptionPane.showMessageDialog(frame, player+ " has lost the game...");
        }
		
	}
	private String currentPlayerName = "";

	private void confirmEndTurn(String o) {
		// TODO Auto-generated method stub
		
	}

	private void showMessage(String string) {
		// TODO Auto-generated method stub
		
	}
	
	


}
