/**
 * 
 */
package gui;

import game.Game;
import handlers.GuiHandler;

import java.awt.Button;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

/**
 * The application window of the game. Is used for the graphical user interface
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class ApplicationWindow {
	
	public static final int WINDOW_WIDTH 	= 800;
	public static final int WINDOW_HEIGHT 	= 523;
	public static final int GRID_WIDTH 		= 500;
	public static final int SIDEBAR_WIDTH 	= WINDOW_WIDTH - GRID_WIDTH;

	private int hSize, vSize;
	
	private GuiHandler controller;
	private JFrame frame;	
	private JLabel currentPlayerLabel;

	/**
	 * Creates a new applicationWindow with a given GuiHandler.
	 * The GuiHandler is used for the interaction between the model ant the view
	 * 
	 * @param controller	The GuiHandler that will handel all triggers.
	 */
    public ApplicationWindow(GuiHandler controller) {
    	setController(controller);
        initialize();
    }
    
    /**
     * Sets the dimensions used to draw the grid
     * @param hSize the number of horizontal cells in the grid.
     * @param vSize	the number of vertical cells in the grid.
     */
    private void setSize(int hSize, int vSize){
    	this.hSize = hSize;
    	this.vSize = vSize;
    	controller.setDim(this.hSize, this.vSize);
    }

    /**
     * Initializes a new application window, with a grid and a sidebar.
     * Adds listeners and buttons with which the flow of the game can
     * be controlled.
     */
    private void initialize() {
    	int hSize = 0, vSize = 0;
    	while(hSize < Game.MIN_HSIZE || vSize < Game.MIN_VSIZE){
	    	hSize = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter a value for the horizontal grid size (>="+Game.MIN_HSIZE+")", 
	    			"Game Dimensions", 1));
	    	vSize = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter a value for the vertical grid size (>="+Game.MIN_VSIZE+")", 
	    			"Game Dimensions", 1));
	    	if(hSize < Game.MIN_HSIZE || vSize < Game.MIN_VSIZE){
	    		JOptionPane.showMessageDialog(frame, "One of the values did not meet the requirements. Please try again.");
	    	}
    	}
    	setSize(hSize, vSize);

    	
        frame = new JFrame();
        frame.setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Objectron");

        /* GRID */
        GridCanvas gridPanel = new GridCanvas(GRID_WIDTH, GRID_WIDTH, this.vSize, this.hSize);
        gridPanel.setBounds(0, 0, GRID_WIDTH, GRID_WIDTH);
        gridPanel.setFocusable(true);
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
        playerActionPanel.setBounds(5, 55, SIDEBAR_WIDTH-10, 120);
        sideBarPanel.setLayout(null);
        sideBarPanel.add(playerActionPanel);
        
        JButton bN  = new JButton("N");
        bN.addActionListener(getController());
        bN.setActionCommand("N");
        playerActionPanel.add(bN);
        
        JButton bNE = new JButton("NE");
        bNE.addActionListener(getController());
        bNE.setActionCommand("NE");
        playerActionPanel.add(bNE);
        
        JButton bE  = new JButton("E");
        bE.addActionListener(getController());
        bE.setActionCommand("E");
        playerActionPanel.add(bE);
        
        JButton bSE = new JButton("SE");
        bSE.addActionListener(getController());
        bSE.setActionCommand("SE");
        playerActionPanel.add(bSE);
        
        JButton bS  = new JButton("S");
        bS.addActionListener(getController());
        bS.setActionCommand("S");
        playerActionPanel.add(bS);
        
        JButton bSW = new JButton("SW");
        bSW.addActionListener(getController());
        bNE.setActionCommand("SW");
        playerActionPanel.add(bSW);
        
        JButton bW  = new JButton("W");
        bW.addActionListener(getController());
        bW.setActionCommand("W");
        playerActionPanel.add(bW);
        
        JButton bNW = new JButton("NW");
        bNW.addActionListener(getController());
        bNW.setActionCommand("NW");
        playerActionPanel.add(bNW);

        
        /* Square Inventory Items */
        JPanel squareInventoryPanel = new JPanel();
        squareInventoryPanel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Square Inventory",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
        squareInventoryPanel.setBounds(5, 175, SIDEBAR_WIDTH-10, 100);
        squareInventoryPanel.setLayout(null);
        sideBarPanel.add(squareInventoryPanel);
        
        JList squareList = new JList(controller.getListModel());
        squareList.addMouseListener(controller);
        squareList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        squareList.setLayoutOrientation(JList.VERTICAL);
        squareList.setVisibleRowCount(-1);
        JScrollPane squareListScroller = new JScrollPane(squareList);
        squareListScroller.setBounds(10,20,270,70);
        squareInventoryPanel.add(squareListScroller);
        
        /* Player Inventory Items */
        JPanel inventoryPanel = new JPanel();
        inventoryPanel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Player's Inventory",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
        inventoryPanel.setBounds(5, 275, SIDEBAR_WIDTH-10, 200);
        inventoryPanel.setLayout(null);
        sideBarPanel.add(inventoryPanel);
        
        JList playerList = new JList(controller.getListModel());
        playerList.addMouseListener(controller);
        playerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        playerList.setLayoutOrientation(JList.VERTICAL);
        playerList.setVisibleRowCount(-1);
        JScrollPane playerListScroller = new JScrollPane(playerList);
        playerListScroller.setBounds(10,20,270,170);
        inventoryPanel.add(playerListScroller);
    }
    
    /**
     * 
     * @return	the GuiHandler which is used in this application window
     */
    private GuiHandler getController(){
    	return this.controller;
    }
    
    /**
     * Sets the controller which this application should use
     * @param controller	the GuiHandler instance which should be used 
     * 						to interact with the model
     */
    private void setController(GuiHandler controller){
    	this.controller = controller;
    }
    
    /**
     * Turns the Jframe on
     */
    public void setVisible(){
    	this.frame.setVisible(true);
    }


    
	
	


}
