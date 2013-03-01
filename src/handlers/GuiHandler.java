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

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.UIManager;

/**
 * @author Jonas Devlieghere
 *
 */
public class GuiHandler extends Observable implements ActionListener {
	
	public ApplicationHandler applicationHandler;
	public static InventoryListModel listModel = new InventoryListModel();
	private Game game;
    Inventory inventory = new Inventory();

	
	public GuiHandler(ApplicationHandler applicationHandler){
		this.applicationHandler = applicationHandler;
		run();
	}
	
	
	
	public void run(){
        try {
        	UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        	ApplicationWindow window = new ApplicationWindow(this);
            window.setVisisble();
            this.addObserver(window);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	public void setGame(Game game){
		this.game = game;
	}


	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(is(e,"N")){
			System.out.println("Move NORTH");
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
	public static void updateListModel(Inventory inventory) {
		listModel.setInventory(inventory);
		System.out.println(listModel.getSize());
	}


	/**
	 * @param hSize
	 * @param vSize
	 */
	public void setDim(int hSize, int vSize) {
		applicationHandler.createGame(hSize,vSize);
	}


}
