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
import java.util.Observable;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.UIManager;

import square.Direction;

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
		if(is(e,"N")){
			getApplicationHandler().getMoveHandler().move(Direction.NORTH);
		}else if(is(e,"NE")){
			getApplicationHandler().getMoveHandler().move(Direction.NORTHEAST);
		}else if(is(e,"E")){
			getApplicationHandler().getMoveHandler().move(Direction.EAST);
		}else if(is(e,"SE")){
			getApplicationHandler().getMoveHandler().move(Direction.SOUTHEAST);
		}else if(is(e,"S")){
			getApplicationHandler().getMoveHandler().move(Direction.SOUTH);
		}else if(is(e,"SW")){
			getApplicationHandler().getMoveHandler().move(Direction.SOUTHWEST);
		}else if(is(e,"W")){
			getApplicationHandler().getMoveHandler().move(Direction.WEST);			
		}else if(is(e,"NW")){
			getApplicationHandler().getMoveHandler().move(Direction.NORTHWEST);
		}else{
			inventory.addItem(new LightGrenade());
			updateListModel(inventory);
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
	


}
