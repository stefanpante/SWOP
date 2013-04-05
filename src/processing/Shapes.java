package processing;

import processing.core.PShape;

public class Shapes {

	private ObjectronGUI gui;

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
	

	public Shapes(ObjectronGUI gui) {
		this.gui = gui;
		this.initImages();
	}
	
	private void initImages(){
		this.wall = gui.loadShape(getClass().getResource("/res/wall.svg").getPath());
		this.powerFail = gui.loadShape(getClass().getResource("/res/powerfailure.svg").getPath());
		this.identityDisc = gui.loadShape(getClass().getResource("/res/identitydisc.svg").getPath());
		this.chargedIdentityDisc = gui.loadShape(getClass().getResource("/res/chargedidentitydisc.svg").getPath());
		this.lightgrenade = gui.loadShape(getClass().getResource("/res/lightgrenade.svg").getPath());
		this.powerFailureItem = gui.loadShape(getClass().getResource("/res/powerfailureitems.svg").getPath());
		
		this.south = gui.loadShape(getClass().getResource("/res/directions/south.svg").getPath());
		this.north = gui.loadShape(getClass().getResource("/res/directions/north.svg").getPath());
		this.west  = gui.loadShape(getClass().getResource("/res/directions/west.svg").getPath());
		this.east  = gui.loadShape(getClass().getResource("/res/directions/east.svg").getPath());
		this.southeast =  gui.loadShape(getClass().getResource("/res/directions/southeast.svg").getPath());
		this.southwest =  gui.loadShape(getClass().getResource("/res/directions/southwest.svg").getPath());
		this.northeast =  gui.loadShape(getClass().getResource("/res/directions/northeast.svg").getPath());
		this.northwest =  gui.loadShape(getClass().getResource("/res/directions/northwest.svg").getPath());
	}


}
