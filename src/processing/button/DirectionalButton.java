package processing.button;

import processing.OConstants;
import processing.ObjectronGUI;
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
		this.visible = true;

	}

	@Override
	public void draw(){
		gui.noStroke();
		gui.fill(getColor());
		if(shape != null){
			gui.shape(shape , position.x + OConstants.MARGIN,position.y + OConstants.MARGIN, 
					width -  OConstants.MARGIN*2,height-  OConstants.MARGIN*2);
		}

	}

	@Override
	public void hover(int mouseX, int mouseY){
		if(visible){
			if(mouseHit(mouseX, mouseY)){
				gui.shape(shape , position.x + OConstants.MARGIN,position.y + OConstants.MARGIN, 
						width -  OConstants.MARGIN*2,height-  OConstants.MARGIN*2);
			}
		}
	}

	public Direction getDirection(){
		return this.direction;
	}
	
	public void mousePressed(int mouseX, int mouseY){
		if(visible){
			if(mouseHit(mouseX, mouseY)){
				ObjectronGUI gui2= (ObjectronGUI) gui;
				gui2.move(direction);
			}
		}
	}
}
