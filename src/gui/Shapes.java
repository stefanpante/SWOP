package gui;

import item.ChargedIdentityDisc;
import item.Flag;
import item.ForceFieldGenerator;
import item.IdentityDisc;
import item.Item;
import item.LightGrenade;
import item.Teleport;
import processing.core.PApplet;
import processing.core.PShape;

public class Shapes {

	private PApplet gui;

	public static PShape wall;
	public static PShape powerFail;
	public static PShape identityDisc;
	public static PShape chargedIdentityDisc;
	public static PShape lightgrenade;
	public static PShape powerFailureItem;
	
	public static PShape north;
	public static PShape south;
	public static PShape east;
	public static PShape west;
	public static PShape northeast;
	public static PShape northwest;
	public static PShape southeast;
	public static PShape southwest;

	public static PShape items;
	public static PShape teleportItem;
	public static PShape teleport;
	public static PShape forcefieldGenerator_on;
	public static PShape forcefieldGenerator_off;
	public static PShape[] flags;
	public static PShape[] flagsItems;
	

	public Shapes(PApplet gui) {
		this.flags = new PShape[9];
		this.flagsItems = new PShape[9];
		this.gui = gui;
		this.initImages();
	}
	
	private void initImages(){
		Shapes.wall = gui.loadShape(getClass().getResource("/res/wall.svg").getPath());

		Shapes.identityDisc = gui.loadShape(getClass().getResource("/res/items/identitydisc.svg").getPath());
		Shapes.chargedIdentityDisc = gui.loadShape(getClass().getResource("/res/items/chargedidentitydisc.svg").getPath());
		Shapes.lightgrenade = gui.loadShape(getClass().getResource("/res/items/lightgrenade.svg").getPath());
		Shapes.items = gui.loadShape(getClass().getResource("/res/items/items.svg").getPath());
		
		Shapes.south = gui.loadShape(getClass().getResource("/res/directions/south.svg").getPath());
		Shapes.north = gui.loadShape(getClass().getResource("/res/directions/north.svg").getPath());
		Shapes.west  = gui.loadShape(getClass().getResource("/res/directions/west.svg").getPath());
		Shapes.east  = gui.loadShape(getClass().getResource("/res/directions/east.svg").getPath());
		Shapes.southeast =  gui.loadShape(getClass().getResource("/res/directions/southeast.svg").getPath());
		Shapes.southwest =  gui.loadShape(getClass().getResource("/res/directions/southwest.svg").getPath());
		Shapes.northeast =  gui.loadShape(getClass().getResource("/res/directions/northeast.svg").getPath());
		Shapes.northwest =  gui.loadShape(getClass().getResource("/res/directions/northwest.svg").getPath());
		
		
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
			if(ffg.isDropped())
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


}
