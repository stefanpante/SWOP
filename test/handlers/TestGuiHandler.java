package handlers;

import game.Game;

import java.util.ArrayList;

import org.junit.Test;

import square.Square;
import utils.Coordinate2D;

public class TestGuiHandler {

	@Test
	public void testGetRepresentationWall(){
		ApplicationHandler h = new ApplicationHandler();
		h.initialize();
		GuiHandler g = h.getGuiHandler();
		g.setGame(new Game(10,10));
		ArrayList<Coordinate2D> coor = g.getWallsRepresentation();
		for(Coordinate2D c: coor){
			System.out.println(c.toString());
		}
		
	}
	
	@Test
	public void removeDuplicates(){
		ArrayList<Square> s = new ArrayList<Square>();
		Square s2 = new Square();
		
		s.add(s2);
		
		ArrayList<Square> r= new ArrayList<Square>();
		r.add(s2);
		r.add(s2);
		
		s.removeAll(r);
	}

}
