/**
 * 
 */
package handlers;

import game.Game;
import gui.ApplicationWindow;
import gui.InventoryListModel;
import items.Inventory;
import items.Item;
import items.LightGrenade;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.UIManager;

import square.Direction;
import square.Square;
import utils.Coordinate2D;

/**
 * @author Jonas Devlieghere
 *
 */
public class GuiHandler extends Observable implements ActionListener, MouseListener  {

	public ApplicationHandler applicationHandler;
	public static InventoryListModel listModel = new InventoryListModel();
	private Game game;
	Inventory inventory = new Inventory();

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
	}

	public ApplicationHandler getApplicationHandler(){
		return this.applicationHandler;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
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
			getApplicationHandler().getMoveHandler().move(direction);
			boolean won = getApplicationHandler().getMoveHandler().endAction();
		}else{
			// TOOD: Notifying?
		}
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
		int y = game.getVSize() - 1 ;
		int x = 0;
		Square s = game.getPlayer1().getStartPosition();
		Square n = s;
		while(s.hasNeighbor(Direction.NORTH)){
			while(n.hasNeighbor(Direction.EAST)){

				try{
					n = n.getNeighbor(Direction.EAST);
					y--;
				} catch(Exception e){
					break;
				}
			}
			try{
				s = s.getNeighbor(Direction.NORTH);
				y = game.getVSize() - 1;
				n = s;
				x++;
			}
			catch(Exception e){
				break;
			}
			
		}

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
<<<<<<< HEAD


=======
	
	
>>>>>>> branch 'master' of https://github.com/stefanpante/SWOP.git

}
