/**
 * 
 */
package gui;

import handlers.GuiHandler;
import items.Inventory;
import items.Item;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
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
public class ApplicationWindow implements Observer {

	
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 523;
	public static final int GRID_WIDTH = 500;
	public static final int SIDEBAR_WIDTH = WINDOW_WIDTH - GRID_WIDTH;
	public static final int ROWS = 10;
	public static final int COLS = 10;
	public static final int ROW_HEIGHT = GRID_WIDTH/ROWS;
	public static final int COL_WIDTH =  GRID_WIDTH/COLS;
	
	private GuiHandler controller;
	private JFrame frame;	
	private DefaultListModel<String> inventoryItems;
	private JLabel currentPlayerLabel;

    public ApplicationWindow(GuiHandler controller) {
    	setController(controller);
    	this.inventoryItems = new DefaultListModel<String>();
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Objectron");

        /* GRID */
        GridCanvas gridPanel = new GridCanvas(GRID_WIDTH, GRID_WIDTH, 10, 10);
        gridPanel.setBounds(0, 0, GRID_WIDTH, GRID_WIDTH);
        gridPanel.setFocusable(true);
        gridPanel.addMouseListener(getController());
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
        
        currentPlayerLabel = new JLabel();
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
    
    private GuiHandler getController(){
    	return this.controller;
    }
    
    private void setController(GuiHandler controller){
    	this.controller = controller;
    }
    
    public void setVisisble(){
    	this.frame.setVisible(true);
    }

	@Override
	public void update(Observable o, Object arg) {
		GuiHandler handler = (GuiHandler) o;
		updateInventory(handler.getInventory());
	}

	/**
	 * @param inventory
	 */
	private void updateInventory(Inventory inventory) {
		System.out.println("STF");
		ArrayList<Item> items = inventory.getAllItems();
		this.inventoryItems.clear();
		for(int i = 0; i < items.size(); i++){
			this.inventoryItems.add(i, items.get(i).toString());
		}
		
	}

	


}
