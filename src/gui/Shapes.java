package gui;

import item.*;
import processing.core.PApplet;
import processing.core.PShape;
import util.Direction;

import java.util.ArrayList;
import java.util.HashMap;

public class Shapes {

	private PApplet gui;

	public static PShape wall;
	public static PShape powerFail;
	private static PShape identityDisc;
	private static PShape chargedIdentityDisc;
	private static PShape lightgrenade;
	public static PShape powerFailureItem;

	private static HashMap<Direction, PShape> directions;
	
	private static PShape items;
	public static PShape teleportItem;
	private static PShape teleport;
	private static PShape forcefieldGenerator_on;
	private static PShape forcefieldGenerator_off;
	private static PShape[] flags;
	private static PShape[] flagsItems;
	

	public Shapes(PApplet gui) {
		this.flags = new PShape[9];
		this.flagsItems = new PShape[9];
		this.directions = new HashMap<Direction, PShape>();
		this.gui = gui;
		this.initImages();
	}
	
	private void initImages(){
		Shapes.wall = gui.loadShape(getClass().getResource("/res/wall.svg").getPath());

		Shapes.identityDisc = gui.loadShape(getClass().getResource("/res/items/identitydisc.svg").getPath());
		Shapes.chargedIdentityDisc = gui.loadShape(getClass().getResource("/res/items/chargedidentitydisc.svg").getPath());
		Shapes.lightgrenade = gui.loadShape(getClass().getResource("/res/items/lightgrenade.svg").getPath());
		Shapes.items = gui.loadShape(getClass().getResource("/res/items/items.svg").getPath());
		
		directions.put(Direction.SOUTH, gui.loadShape(getClass().getResource("/res/directions/south.svg").getPath()));
		directions.put(Direction.NORTH, gui.loadShape(getClass().getResource("/res/directions/north.svg").getPath()));
		directions.put(Direction.WEST, gui.loadShape(getClass().getResource("/res/directions/west.svg").getPath()));
		directions.put(Direction.EAST, gui.loadShape(getClass().getResource("/res/directions/east.svg").getPath()));
		directions.put(Direction.SOUTHEAST, gui.loadShape(getClass().getResource("/res/directions/southeast.svg").getPath()));
		directions.put(Direction.SOUTHWEST, gui.loadShape(getClass().getResource("/res/directions/southwest.svg").getPath()));
		directions.put(Direction.NORTHEAST, gui.loadShape(getClass().getResource("/res/directions/northeast.svg").getPath()));
		directions.put(Direction.NORTHWEST, gui.loadShape(getClass().getResource("/res/directions/northwest.svg").getPath()));
		
		
		Shapes.teleportItem = gui.loadShape(getClass().getResource("/res/items/teleportitems.svg").getPath());
		Shapes.teleport = gui.loadShape(getClass().getResource("/res/items/teleport.svg").getPath());
		Shapes.forcefieldGenerator_on = gui.loadShape(getClass().getResource("/res/items/forcefield_on.svg").getPath());
		Shapes.forcefieldGenerator_off = gui.loadShape(getClass().getResource("/res/items/forcefield_off.svg").getPath());
		
		// load in all the flags
		String basicurl = "/res/items/flags/flag";
		for(int i = 1; i <= 9; i++){
			Shapes.flags[i-1] = gui.loadShape(getClass().getResource(basicurl + "_" + i + ".svg").getPath());
			Shapes.flagsItems[i-1]  = gui.loadShape(getClass().getResource(basicurl + "_" + i + "_items.svg").getPath());
		}
	}
	
	public static PShape getShape(Item item){
		if(item instanceof IdentityDisc){
			if(item instanceof ChargedIdentityDisc){
				return Shapes.chargedIdentityDisc;
			}
			
			return Shapes.identityDisc;
		}
		
		if(item instanceof Teleport){
			return Shapes.teleport;
		}
		
		
		if(item instanceof LightGrenade){
			return Shapes.lightgrenade;
		}		
		
		
		if(item instanceof ForceFieldGenerator){
			ForceFieldGenerator ffg = (ForceFieldGenerator) item;
			if(ffg.isActive())
				return Shapes.forcefieldGenerator_on;
			else{
				return Shapes.forcefieldGenerator_off;
			}
		}
		
		if(item instanceof Flag){
			Flag flag = (Flag) item;
			int id = flag.getPlayer().getID();
			return Shapes.flags[id - 1];
		}
		
		return null;
	}

	public static PShape getFlagItem(ArrayList<Item> it) {
		for(Item item: it){
			if(item instanceof Flag){
				Flag f = (Flag) item;
				int id = f.getPlayer().getID();
				return flagsItems[id-1];
			}
		}
		return null;
	}
	
	public static PShape getDirection(Direction direction){
		return directions.get(direction);
	}


}
