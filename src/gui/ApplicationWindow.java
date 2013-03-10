/**
 * 
 */
package gui;

import game.Game;
import handlers.GameHandler;
import items.Item;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import square.Direction;


//TODO: http://cs.nyu.edu/~yap/classes/visual/03s/lect/l7/ -> misschien handige tutorial

/**
 * The application window of the game. Is used for the graphical user interface
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class ApplicationWindow implements ActionListener {
	
	public static final int WINDOW_WIDTH 	= 800;
	public static final int WINDOW_HEIGHT 	= 523;
	public static final int GRID_WIDTH 		= 500;
	public static final int SIDEBAR_WIDTH 	= WINDOW_WIDTH - GRID_WIDTH;

	private int hSize, vSize;
	
	private GameHandler gameHandler;
	private JFrame frame;	
	private JLabel currentPlayerLabel;
	
	public static GridModel GRID_MODEL = new GridModel();


	/**
	 * Creates a new applicationWindow with a given GuiHandler.
	 * The GuiHandler is used for the interaction between the model ant the view
	 * 
	 * @param controller	The GuiHandler that will handle all triggers.
	 */
    public ApplicationWindow(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
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
    	gameHandler.createGame(hSize, vSize);
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
        // TODO: 
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
        bN.addActionListener(this);
        bN.setActionCommand("N");
        playerActionPanel.add(bN);
        
        JButton bNE = new JButton("NE");
        bNE.addActionListener(this);
        bNE.setActionCommand("NE");
        playerActionPanel.add(bNE);
        
        JButton bE  = new JButton("E");
        bE.addActionListener(this);
        bE.setActionCommand("E");
        playerActionPanel.add(bE);
        
        JButton bSE = new JButton("SE");
        bSE.addActionListener(this);
        bSE.setActionCommand("SE");
        playerActionPanel.add(bSE);
        
        JButton bS  = new JButton("S");
        bS.addActionListener(this);
        bS.setActionCommand("S");
        playerActionPanel.add(bS);
        
        JButton bSW = new JButton("SW");
        bSW.addActionListener(this);
        bSW.setActionCommand("SW");
        playerActionPanel.add(bSW);
        
        JButton bW  = new JButton("W");
        bW.addActionListener(this);
        bW.setActionCommand("W");
        playerActionPanel.add(bW);
        
        JButton bNW = new JButton("NW");
        bNW.addActionListener(this);
        bNW.setActionCommand("NW");
        playerActionPanel.add(bNW);

        
        /* Square Inventory Items */
        JPanel inventoryPanel = new JPanel();
        inventoryPanel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Inventories",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
        inventoryPanel.setBounds(5, 175, SIDEBAR_WIDTH-10, 70);
        sideBarPanel.add(inventoryPanel);
        
        JButton pickup  = new JButton("Pick up item");
        pickup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
            {
				ArrayList<Item> items = GRID_MODEL.getCurrentSquareInventory();
				Item[] a = new Item[items.size()];
				GRID_MODEL.getCurrentSquareInventory().toArray(a);

                Item input = (Item) JOptionPane.showInputDialog(null, "What item would you like to pick up?",
                    "Pick up item", JOptionPane.QUESTION_MESSAGE, null,
                    a, 
                    null);
                if(input != null)
                	gameHandler.getPickupHandler().pickUp(input);
            }
        });      
        inventoryPanel.add(pickup);
        
        JButton use = new JButton("Use item");
        use.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
            {
				ArrayList<Item> items = GRID_MODEL.getCurrentPlayerInventory();
				Item[] a = new Item[items.size()];
				GRID_MODEL.getCurrentPlayerInventory().toArray(a);

                Item input = (Item) JOptionPane.showInputDialog(null, "What item would you like to use?",
                    "Use item", JOptionPane.QUESTION_MESSAGE, null,
                    a, 
                    null);
                if(input != null)
                	gameHandler.getUseItemHandler().useItem(input);
            }
        });      
        inventoryPanel.add(use);       
 
        GRID_MODEL.addObserver(gridPanel);


    }
    
    /**
     * Turns the Jframe on
     */
    public void setVisible(){
    	this.frame.setVisible(true);
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
	}

	private boolean is(ActionEvent e,String string){
		return e.getActionCommand().equals(string);
	}


}
