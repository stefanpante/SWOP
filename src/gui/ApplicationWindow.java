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
 * @author jonas
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

    public ApplicationWindow(GuiHandler controller) {
    	setController(controller);
        initialize();
    }
    
    private void setSize(int hSize, int vSize){
    	this.hSize = hSize;
    	this.vSize = vSize;
    	controller.setDim(this.hSize, this.vSize);
    }

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

        
        /* Inventory Items */
        JPanel inventoryPanel = new JPanel();
        inventoryPanel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Inventory",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
        inventoryPanel.setBounds(5, 175, SIDEBAR_WIDTH-10, 150);
        inventoryPanel.setLayout(null);
        sideBarPanel.add(inventoryPanel);
        
        JList list = new JList(controller.getListModel());
        list.addMouseListener(controller);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
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


    
	
	


}
