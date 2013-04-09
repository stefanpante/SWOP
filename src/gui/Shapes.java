package gui;

import item.Item;
import item.LightGrenade;
import item.launchable.ChargedIdentityDisc;
import item.launchable.IdentityDisc;
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
	public static PShape powerFailureTeleport;
	public static PShape powerFailureTeleportItem;
	public static PShape teleportItem;
	public static PShape teleport;
	

	public Shapes(PApplet gui) {
		this.gui = gui;
		this.initImages();
	}
	
	private void initImages(){
		Shapes.wall = gui.loadShape(getClass().getResource("/res/wall.svg").getPath());
		Shapes.powerFail = gui.loadShape(getClass().getResource("/res/powerfailure.svg").getPath());
		Shapes.identityDisc = gui.loadShape(getClass().getResource("/res/identitydisc.svg").getPath());
		Shapes.chargedIdentityDisc = gui.loadShape(getClass().getResource("/res/chargedidentitydisc.svg").getPath());
		Shapes.lightgrenade = gui.loadShape(getClass().getResource("/res/lightgrenade.svg").getPath());
		Shapes.powerFailureItem = gui.loadShape(getClass().getResource("/res/powerfailureitems.svg").getPath());
		
		Shapes.south = gui.loadShape(getClass().getResource("/res/directions/south.svg").getPath());
		Shapes.north = gui.loadShape(getClass().getResource("/res/directions/north.svg").getPath());
		Shapes.west  = gui.loadShape(getClass().getResource("/res/directions/west.svg").getPath());
		Shapes.east  = gui.loadShape(getClass().getResource("/res/directions/east.svg").getPath());
		Shapes.southeast =  gui.loadShape(getClass().getResource("/res/directions/southeast.svg").getPath());
		Shapes.southwest =  gui.loadShape(getClass().getResource("/res/directions/southwest.svg").getPath());
		Shapes.northeast =  gui.loadShape(getClass().getResource("/res/directions/northeast.svg").getPath());
		Shapes.northwest =  gui.loadShape(getClass().getResource("/res/directions/northwest.svg").getPath());
		
		Shapes.items = gui.loadShape(getClass().getResource("/res/items.svg").getPath());
		Shapes.powerFailureTeleportItem = gui.loadShape(getClass().getResource("/res//teleportpowerfailitems.svg").getPath());
		Shapes.powerFailureTeleport = gui.loadShape(getClass().getResource("/res/teleportpowerfail.svg").getPath());
		Shapes.teleportItem = gui.loadShape(getClass().getResource("/res/teleportitems.svg").getPath());
		Shapes.teleport = gui.loadShape(getClass().getResource("/res/teleport.svg").getPath());
	}
	
	public static PShape getShape(Item item){
		if(item instanceof IdentityDisc){
			return Shapes.identityDisc;
		}
		
		if(item instanceof ChargedIdentityDisc){
			return Shapes.chargedIdentityDisc;
		}
		
		if(item instanceof LightGrenade){
			return Shapes.lightgrenade;
		}		
		return null;
	}


}
