package processing.button;

import processing.OConstants;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;
import square.Direction;

public class DirectionalButton extends ShapeButton{

	/**
	 * The direction.
	 */
	private Direction direction;


	public DirectionalButton(float width, float height, PShape shape, Direction direction, PVector position,
			PApplet gui) {
		super(width, height, shape, position, gui);
		this.direction = direction;

	}

	@Override
	public void draw(){
		gui.noStroke();
		gui.fill(getColor());
		System.out.println(width + " \t" + height);
		//gui.rect(position.x, position.y,width, height);
		if(shape != null){
			gui.shape(shape , position.x + OConstants.MARGIN,position.y + OConstants.MARGIN, 
					width -  OConstants.MARGIN*2,height-  OConstants.MARGIN*2);
		}

	}

	public void rollover(){

			gui.shape(shape , position.x + OConstants.MARGIN,position.y + OConstants.MARGIN, 
					width -  OConstants.MARGIN*2,height-  OConstants.MARGIN*2);
	
	}

	public Direction getDirection(){
		return this.direction;
	}
	
	@Override
	public void mouseOver(int mouseX, int mouseY){
		if(hit(mouseX, mouseY)){
			this.rollover();
		}
	}

	@Override
	public boolean mouseHit(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		return false;
	}




}