/**
 * 
 */
package handlers;

import game.Game;
import gui.ApplicationWindow;
import gui.GridCanvas;
import items.Inventory;
import items.Item;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.UIManager;

import player.Player;

import square.Direction;
import square.Square;
import utils.Coordinate2D;

/**
 * @author Jonas Devlieghere
 *
 */
public class GuiHandler implements ActionListener  {

	public StartNewGameHandler applicationHandler;
	private Game game;
	
	private Coordinate2D player1CO;
	private HashMap<Coordinate2D,Square> player1LTCoordinates;
	private Coordinate2D player2CO;
	private HashMap<Coordinate2D,Square> player2LTCoordinates;


	private DefaultListModel<Item> inventoryItems;

	public GuiHandler(StartNewGameHandler applicationHandler){
		inventoryItems = new DefaultListModel<Item>();
		this.applicationHandler = applicationHandler;
		run();
	}

	public void run(){
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			ApplicationWindow window = new ApplicationWindow(this);
			window.setVisible();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setGame(Game game){
		this.game = game;
		player1CO = new Coordinate2D(0, game.getVSize() -1);
		player2CO = new Coordinate2D(game.getHSize() -1, 0);
		ApplicationWindow.GRID_MODEL.setPlayer1(player1CO);
		ApplicationWindow.GRID_MODEL.setPlayer2(player2CO);
		player1LTCoordinates = new HashMap<Coordinate2D,Square>();
		player2LTCoordinates = new HashMap<Coordinate2D,Square>();
	}

	public StartNewGameHandler getApplicationHandler(){
		return this.applicationHandler;
	}

	/**
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		Direction direction = null;
		if(is(e,"N")){
			direction = Direction.NORTH;
		}else if(is(e,"NE")){
			direction = Direction.NORTHEAST;
		}else if(is(e,"E")){
			direction = Direction.EAST;
		}else if(is(e,"SE")){
			direction = Direction.SOUTHEAST;
		}else if(is(e,"S")){
			direction = Direction.SOUTH;
		}else if(is(e,"SW")){
			direction = Direction.SOUTHWEST;
		}else if(is(e,"W")){
			direction = Direction.WEST;			
		}else if(is(e,"NW")){
			direction = Direction.NORTHWEST;
		}

		if(getApplicationHandler().getMoveHandler().checkToProceed()){
			
			try{
				Square prevPosition = game.getCurrentPlayer().getPosition();
				Player currentPlayer = game.getCurrentPlayer();
				getApplicationHandler().getMoveHandler().move(direction);
				if(currentPlayer == game.getPlayer1()){
					player1LTCoordinates.put(player1CO, prevPosition);
					player1CO = player1CO.getNeighbor(direction);
					ApplicationWindow.GRID_MODEL.setPlayer1(player1CO);
					for(Coordinate2D coor: player1LTCoordinates.keySet()){
						  if(!currentPlayer.getLightTrail().contains(player1LTCoordinates.get(coor))){
							  player1LTCoordinates.remove(coor);
						  }
					}
					ApplicationWindow.GRID_MODEL.setLightTrailBlue(getPlayer1LightTrailCoordinates());
				} else {
					player2LTCoordinates.put(player2CO, prevPosition);
					player2CO = player2CO.getNeighbor(direction);
					ApplicationWindow.GRID_MODEL.setPlayer1(player1CO);
					for(Coordinate2D coor: player2LTCoordinates.keySet()){
						  if(!currentPlayer.getLightTrail().contains(player2LTCoordinates.get(coor))){
							  player2LTCoordinates.remove(coor);
						  }
					}
					ApplicationWindow.GRID_MODEL.setLightTrailRed(getPlayer1LightTrailCoordinates());

				}
			}  catch(IllegalStateException e1) {
				System.err.println("Player cannot move the square.");
			} catch(IllegalArgumentException e2) {
				System.err.println("Tried to move to a neighbor that is no square.");
			}
			
			boolean won = getApplicationHandler().getMoveHandler().endAction();
		}else{
			// TOOD: Notifying?
		}
	}

	public ArrayList<Coordinate2D> getPlayer1LightTrailCoordinates(){
		return new ArrayList<Coordinate2D>(player1LTCoordinates.keySet());
	}
	
	public ArrayList<Coordinate2D> getPlayer2LightTrailCoordinates(){
		return new ArrayList<Coordinate2D>(player2LTCoordinates.keySet());
	}
	
	private boolean is(ActionEvent e,String string){
		return e.getActionCommand().equals(string);
	}

	/**
	 * @param player1
	 * @param player2
	 */
	public void setNames(String player1, String player2) {
		game.getPlayer1().setName(player1);
		game.getPlayer2().setName(player2);
	}


	/**
	 * @param hSize
	 * @param vSize
	 */
	public void setDim(int hSize, int vSize) {
		applicationHandler.createGame(hSize,vSize);
	}

	public ArrayList<Coordinate2D> getWallsRepresentation(){

		ArrayList<Coordinate2D> wallCoor = new ArrayList<Coordinate2D>();
		int y = game.getVSize() -1;
		int x = 0;
		Square s = game.getPlayer1().getStartPosition();
		Square n = s;
		while(true){
			while(true){
				if(n.isObstructed())
					wallCoor.add(new Coordinate2D(x,y));
				try{
					n = n.getNeighbor(Direction.EAST);
					x++;

				} catch(Exception e){
					break;
				}
				
			}
			
			try{
				s = s.getNeighbor(Direction.NORTH);
				
				n = s;
				y--;
			}
			catch(Exception e){
				break;
			}
			x = 0;

		}

		return wallCoor;
	}
	
	public Coordinate2D getPlayer1Coordinate(){
		return player1CO;
	}
	
	public Coordinate2D getPlayer2Coordinate(){
		return player2CO;
	}
	
	/**
	 * 
	 * causes a use action.
	 * @param item  the item on which the action is performed
	 */
	public void use(Item item) {
		try{
			Player currentPlayer = game.getCurrentPlayer();
			getApplicationHandler().getUseItemHandler().useItem(item);
			if(currentPlayer == game.getPlayer1()){
				ApplicationWindow.GRID_MODEL.setPlayer1(player1CO);
				for(Coordinate2D coor: player1LTCoordinates.keySet()){
					  if(!currentPlayer.getLightTrail().contains(player1LTCoordinates.get(coor))){
						  player1LTCoordinates.remove(coor);
					  }
				}
				ApplicationWindow.GRID_MODEL.setLightTrailBlue(getPlayer1LightTrailCoordinates());
			} else {
				ApplicationWindow.GRID_MODEL.setPlayer1(player1CO);
				for(Coordinate2D coor: player2LTCoordinates.keySet()){
					  if(!currentPlayer.getLightTrail().contains(player2LTCoordinates.get(coor))){
						  player2LTCoordinates.remove(coor);
					  }
				}
				ApplicationWindow.GRID_MODEL.setLightTrailRed(getPlayer1LightTrailCoordinates());

			}
		}  catch(Exception e1) {
			System.err.println("Cannot use that " + item);
		}
	}

	/**
	 * causes a pickup action.
	 * @param item  the item on which the action is performed
	 */
	public void pickup(Item item) {
		try{
			Player currentPlayer = game.getCurrentPlayer();
			getApplicationHandler().getPickupHandler().pickUp(item);
			if(currentPlayer == game.getPlayer1()){
				ApplicationWindow.GRID_MODEL.setPlayer1(player1CO);
				for(Coordinate2D coor: player1LTCoordinates.keySet()){
					  if(!currentPlayer.getLightTrail().contains(player1LTCoordinates.get(coor))){
						  player1LTCoordinates.remove(coor);
					  }
				}
				ApplicationWindow.GRID_MODEL.setLightTrailBlue(getPlayer1LightTrailCoordinates());
			} else {
				ApplicationWindow.GRID_MODEL.setPlayer1(player1CO);
				for(Coordinate2D coor: player2LTCoordinates.keySet()){
					  if(!currentPlayer.getLightTrail().contains(player2LTCoordinates.get(coor))){
						  player2LTCoordinates.remove(coor);
					  }
				}
				ApplicationWindow.GRID_MODEL.setLightTrailRed(getPlayer1LightTrailCoordinates());

			}
		}  catch(Exception e1) {
			System.err.println("Cannot pick up that " + item);
		}
		
	}

}
