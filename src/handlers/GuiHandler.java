/**
 * 
 */
package handlers;

import game.Game;
import grid.core.Coordinate2D;
import grid.core.Grid;
import gui.view.ApplicationWindow;
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
 * @author jonas
 *
 */
public class GuiHandler extends Observable implements ActionListener, MouseListener {
	
	Inventory inventory;
	
	public GuiHandler(Game game){
		run();
		inventory = new Inventory();
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
	
	public Grid getGrid(){
		return new Grid(10, 10);
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
		Coordinate2D coordinate = new Coordinate2D((int)point.getX(), (int)point.getY());
		// TODO: Whole shebang
		System.out.println(getGridCoordinate(coordinate));
		inventory.addItem(new LightGrenade());
        setChanged();
        notifyObservers(null);
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

	public static Coordinate2D getGridCoordinate(Coordinate2D coordinate){
		int newX = coordinate.getX() / ApplicationWindow.COL_WIDTH;
		int newY = coordinate.getY() / ApplicationWindow.ROW_HEIGHT;
		return new Coordinate2D(newX, newY);
	}
	
	public static Coordinate2D getGuiCoordinate(Coordinate2D coordinate){
		int newX = coordinate.getX() * ApplicationWindow.COL_WIDTH;
		int newY = coordinate.getY() * ApplicationWindow.ROW_HEIGHT;
		return new Coordinate2D(newX, newY);
	}
	

	
	
}
