package processing;

import java.util.ArrayList;

import processing.core.PVector;

public class GridGui implements Drawable{
	
	/**
	 * the margin between the cells of the grid.
	 */
	public static int MARGIN = 5;

	/**
	 * The parent object used to draw;
	 */
	private ObjectronGUI objectronGUI;
	
	/**
	 * The coordinates of the top left corner of the grid 
	 */
	private PVector position;
	
	/**
	 * the width in pixels of the grid.
	 */
	private float width;
	
	/**
	 * The height of the grid in pixels.
	 */
	private float height;
	
	/**
	 * Number of horizontal cells
	 */
	private int hCells;
	
	/**
	 * number of vertical cells.
	 */
	private int vCells;
	
	/**
	 * The list of squares to be drawn onto the grid.
	 */
	private ArrayList<SquareGUI> squares;
	
	public GridGui(PVector position, ObjectronGUI objectronGUI, float width, float height, int hCells, int vCells) {
		this.position = position;
		this.objectronGUI = objectronGUI;
		this.height = height;
		this.width = width;
		this.hCells = hCells;
		this.vCells = vCells;
		this.squares = new ArrayList<SquareGUI>();
		this.initGrid();
	}
	

	private void initGrid() {
		float x = position.x;
		float y = position.y;
		
		float swidth = (width - hCells * MARGIN) / hCells;
		float sHeight = (height- vCells * MARGIN) / vCells;
		System.out.println(swidth);
		for(int i = 0; i < vCells; i++){
			for(int j = 0; j < hCells; j++){
				SquareGUI s = new SquareGUI(objectronGUI);
				s.setHeight(sHeight);
				s.setWidth(swidth);
				s.setPosition(x, y);
				squares.add(s);
				x += swidth + MARGIN;
			}
			x = position.x;
			y += sHeight + MARGIN;
		}
		
	}


	/**
	 * draws the grid onto the screen.
	 */
	@Override
	public void draw() {
		for(SquareGUI square : squares){
			square.draw();
		}
		
		
		
	}

	//
	@Override
	public boolean mouseHit(int mouseX, int mouseY) {
		for(SquareGUI square : squares){
			if(square.mouseHit(mouseX, mouseY))
				return true;
		}
		
		return false;
		
		
	}


	@Override
	public void mouseOver(int mouseX, int mouseY) {
		for(SquareGUI square: squares){
			square.mouseOver(mouseX, mouseY);
		}
	}

}
