package gui.button;

import gui.Shapes;
import processing.core.PApplet;
import processing.core.PVector;
import util.Direction;
import util.OConstants;

public class DirectionalButton extends ShapeButton{

	/**
	 * The direction which this button represents
	 */
	private Direction direction;


	public DirectionalButton(float width, float height, Direction direction, PVector position,
			PApplet gui) {
		super(width, height, Shapes.getDirection(direction), position, gui);
		this.direction = direction;
		this.visible = true;

	}

	@Override
	public void draw(){
		gui.noStroke();
		gui.fill(getColor());
		if(shape != null && position != null){
			gui.shape(shape , position.x + OConstants.MARGIN,position.y + OConstants.MARGIN, 
					width -  OConstants.MARGIN*2,height-  OConstants.MARGIN*2);
		}

	}

	@Override
	public void hover(int mouseX, int mouseY){
		if(visible){
			if(mouseHit(mouseX, mouseY)){
				if(shape != null && position != null)
					gui.shape(shape , position.x + OConstants.MARGIN,position.y + OConstants.MARGIN, 
							width -  OConstants.MARGIN*2,height-  OConstants.MARGIN*2);
			}
		}
	}

	public Direction getDirection(){
		return this.direction;
	}
}
