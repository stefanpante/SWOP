package handlers;

import java.util.ArrayList;

import org.junit.Test;

import utils.Coordinate2D;
import game.Game;

public class testGuiHandler {

	@Test
	public void testGetRepresentationWall(){
		ApplicationHandler h = new ApplicationHandler();
		h.initialize();
		GuiHandler g = h.getGuiHandler();
		ArrayList<Coordinate2D> coor = g.getWallsRepresentation();
		for(Coordinate2D c: coor){
			System.out.println(c.toString());
		}
		
	}

}
