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
public class GuiHandler extends Observable implements ActionListener, MouseListener  {

	public ApplicationHandler applicationHandler;
	private Game game;
	Inventory inventory = new Inventory();
	
	private Coordinate2D player1CO;
	private HashMap<Coordinate2D,Square> player1LTCoordinates;
	private Coordinate2D player2CO;
	private HashMap<Coordinate2D,Square> player2LTCoordinates;


	private DefaultListModel<Item> inventoryItems;

	public GuiHandler(ApplicationHandler applicationHandler){
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
		player1CO = new Coordinate2D(game.getVSize() -1, 0);
		player2CO = new Coordinate2D(0, game.getHSize() -1);
		player1LTCoordinates = new HashMap<Coordinate2D,Square>();
		player2LTCoordinates = new HashMap<Coordinate2D,Square>();
	}

	public ApplicationHandler getApplicationHandler(){
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
					GridCanvas.GRID_MODEL.setPlayer1(player1CO);
					for(Coordinate2D coor: player1LTCoordinates.keySet()){
						  if(!currentPlayer.getLightTrail().contains(player1LTCoordinates.get(coor))){
							  player1LTCoordinates.remove(coor);
						  }
					}
					GridCanvas.GRID_MODEL.setLightTrailBlue(getPlayer1LightTrailCoordinates());
				} else {
					player2LTCoordinates.put(player2CO, prevPosition);
					player2CO = player2CO.getNeighbor(direction);
					GridCanvas.GRID_MODEL.setPlayer1(player1CO);
					for(Coordinate2D coor: player2LTCoordinates.keySet()){
						  if(!currentPlayer.getLightTrail().contains(player2LTCoordinates.get(coor))){
							  player2LTCoordinates.remove(coor);
						  }
					}
					GridCanvas.GRID_MODEL.setLightTrailRed(getPlayer1LightTrailCoordinates()));

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
	 * @param inventory
	 */
	public void updateListModel(Inventory inventory) {
		DefaultListModel<Item> listModel = getListModel(); 
		listModel.clear();
		getListModel().clear();
		for(Item item : inventory.getAllItems()){
			listModel.addElement(item);
		}
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
	 * @return
	 */
	public DefaultListModel getListModel() {
		return this.inventoryItems;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		JList list = (JList) e.getSource();
		int index = list.locationToIndex(e.getPoint());
		System.out.println(index);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param input
	 */
	public void use(Item item) {
		getApplicationHandler().getUseItemHandler().useItem(item);
	}

	/**
	 * @param input
	 */
	public void pickup(Item item) {
		getApplicationHandler().getPickupHandler().pickUp(item);
		
	}

}
