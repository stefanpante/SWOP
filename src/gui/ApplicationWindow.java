/**
 * 
 */
package gui;

import grid.core.Coordinate2D;
import items.Item;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

/**
 * @author jonas
 *
 */
public class ApplicationWindow implements ActionListener, MouseListener {

	
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 523;
	public static final int GRID_WIDTH = 500;
	public static final int SIDEBAR_WIDTH = WINDOW_WIDTH - GRID_WIDTH;
	public static final int ROWS = 10;
	public static final int COLS = 10;
	public static final int ROW_HEIGHT = GRID_WIDTH/ROWS;
	public static final int COL_WIDTH =  GRID_WIDTH/COLS;
	
	private JFrame frame;	
	private String[] inventoryItems;

    public static void main(String[] args) {
        try {
        	UIManager.setLookAndFeel("com.apple.laf.AquaLookAndFeel");
        	ApplicationWindow window = new ApplicationWindow();
            window.frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ApplicationWindow() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Objectron");

        /* GRID */
        GuiGrid gridPanel = new GuiGrid(GRID_WIDTH, GRID_WIDTH, 10, 10);
        gridPanel.setBounds(0, 0, GRID_WIDTH, GRID_WIDTH);
        gridPanel.setFocusable(true);
        gridPanel.addMouseListener(this);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.getContentPane().add(gridPanel);
        
        /* SIDEBAR */
        JPanel sideBarPanel = new JPanel();
        sideBarPanel.setBounds(GRID_WIDTH, 0, SIDEBAR_WIDTH, WINDOW_HEIGHT);
        sideBarPanel.setLayout(null);
        frame.getContentPane().add(sideBarPanel);
       
        /* Current Player */
        JPanel currentPlayerPanel = new JPanel();
        currentPlayerPanel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Current Player",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
        currentPlayerPanel.setBounds(5, 5, SIDEBAR_WIDTH-10, 50);
        sideBarPanel.add(currentPlayerPanel);
        
        JLabel currentPlayerLabel = new JLabel();
        currentPlayerLabel.setText("Speler 1");
        currentPlayerPanel.add(currentPlayerLabel);
        
        /* Action */
        JPanel playerActionPanel = new JPanel();
        playerActionPanel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Player Actions",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
        playerActionPanel.setBounds(5, 55, SIDEBAR_WIDTH-10, 100);
        sideBarPanel.add(playerActionPanel);
        
        /* Inventory Items */
        JPanel inventoryPanel = new JPanel();
        inventoryPanel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Inventory",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
        inventoryPanel.setBounds(5, 155, SIDEBAR_WIDTH-10, 150);
        inventoryPanel.setLayout(null);
        sideBarPanel.add(inventoryPanel);
        
        JList list = new JList(inventoryItems);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	System.out.println("click");
            }
        });
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setBounds(10,20,270,120);
        inventoryPanel.add(listScroller);
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
		int newX = coordinate.getX() / COL_WIDTH;
		int newY = coordinate.getY() / ROW_HEIGHT;
		return new Coordinate2D(newX, newY);
	}
	
	public static Coordinate2D getGuiCoordinate(Coordinate2D coordinate){
		int newX = coordinate.getX() * COL_WIDTH;
		int newY = coordinate.getY() * ROW_HEIGHT;
		return new Coordinate2D(newX, newY);
	}
	
	public void setInventory(ArrayList<Item> list){
		String[] inventory = new String[list.size()];
		for(int i = 0; i < list.size(); i++)
			inventory[i] = list.get(i).toString();
		this.inventoryItems = inventory;
	}
}
