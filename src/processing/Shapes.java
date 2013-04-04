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
		this.powerFailureItem = gui.loadShape(getClass().getResource("/res/lightgrenade.svg").getPath());
	}


}
