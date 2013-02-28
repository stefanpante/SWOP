/**
 * 
 */
package handlers;

import game.Game;
import gui.ApplicationWindow;
import items.Inventory;
import items.LightGrenade;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;

import javax.swing.UIManager;

/**
 * @author Jonas Devlieghere
 *
 */
public class GuiHandler extends Observable implements ActionListener, MouseListener {
	
	Inventory inventory;
	
	public GuiHandler(){
		inventory = new Inventory();
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
	
	public Inventory getInventory(){
		return this.inventory;
	}
		



	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		Point point = e.getPoint();
		// TODO: Whole shebang
		inventory.addItem(new LightGrenade());
        setChanged();
        notifyObservers();
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
