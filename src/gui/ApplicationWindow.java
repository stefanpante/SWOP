/**
 * 
 */
package gui;

import grid.Grid;
import handlers.GameHandler;
import items.Item;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
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
	
	public static GameModel MODEL = new GameModel();


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
    	while(hSize < Grid.MIN_HSIZE || vSize < Grid.MIN_VSIZE){
	    	hSize = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter a value for the horizontal grid size (>="+Grid.MIN_HSIZE+")", 
	    			"Game Dimensions", 1));
	    	vSize = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter a value for the vertical grid size (>="+Grid.MIN_VSIZE+")", 
	    			"Game Dimensions", 1));
	    	if(hSize < Grid.MIN_HSIZE || vSize < Grid.MIN_VSIZE){
	    		JOptionPane.showMessageDialog(frame, "One of the values did not meet the requirements. Please try again.");
	    	}
    	}
    	setSize(hSize, vSize);

    	/* Windows */
        frame = new JFrame();
        frame.setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
		frame.setTitle("Objectron");

        /* GRID */
        GridPanel gridPanel = new GridPanel(GRID_WIDTH, GRID_WIDTH, this.vSize, this.hSize);
        gridPanel.setBounds(0, 0, GRID_WIDTH, GRID_WIDTH);
        gridPanel.setFocusable(true);
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
        playerActionPanel.setBounds(5, 55, SIDEBAR_WIDTH-10, 175);
        sideBarPanel.setLayout(null);
        sideBarPanel.add(playerActionPanel);
        playerActionPanel.setLayout(null);
        
        JButton bNW = makeNavigationButton("arrow_NW.png", "NW", "North west",true);
        bNW.setBounds(80,25,40,40);
        playerActionPanel.add(bNW);
        
        JButton bN  = makeNavigationButton("arrow_N.png", "N", "North",true);
        bN.setBounds(125,25,40,40);
        playerActionPanel.add(bN);
        
        JButton bNE = makeNavigationButton("arrow_NE.png", "NE", "North east",true);
        bNE.setBounds(170,25,40,40);
        playerActionPanel.add(bNE);
        
        JButton bE  = makeNavigationButton("arrow_E.png", "E", "East",true);
        bE.setBounds(170,70,40,40);
        playerActionPanel.add(bE);
        
        JButton bW  = makeNavigationButton("arrow_W.png", "W", "West",true);
        bW.setBounds(80,70,40,40);
        playerActionPanel.add(bW);
        
        JButton bSW = makeNavigationButton("arrow_SW.png", "SW", "South west",true);
        bSW.setBounds(80,115,40,40);
        playerActionPanel.add(bSW);
        
        JButton bS  = makeNavigationButton("arrow_S.png", "S", "South",true);
        bS.setBounds(125,115,40,40);
        playerActionPanel.add(bS);

        JButton bSE = makeNavigationButton("arrow_SE.png", "SE", "South east",true);
        bSE.setBounds(170,115,40,40);
        playerActionPanel.add(bSE);
        
        /* Square Inventory Items */
        JPanel inventoryPanel = new JPanel();
        inventoryPanel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Inventories",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
        inventoryPanel.setBounds(5, 230, SIDEBAR_WIDTH-10, 70);
        sideBarPanel.add(inventoryPanel);
        
        JButton pickup  = new JButton("Pick up item");
        pickup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
            {
				ArrayList<Item> items = MODEL.getCurrentSquareInventory();
				Item[] a = new Item[items.size()];
				MODEL.getCurrentSquareInventory().toArray(a);

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
				ArrayList<Item> items = MODEL.getCurrentPlayerInventory();
				Item[] a = new Item[items.size()];
				MODEL.getCurrentPlayerInventory().toArray(a);

                Item input = (Item) JOptionPane.showInputDialog(null, "What item would you like to use?",
                    "Use item", JOptionPane.QUESTION_MESSAGE, null,
                    a, 
                    null);
                if(input != null)
                	gameHandler.getUseItemHandler().useItem(input);
            }
        });      
        inventoryPanel.add(use);       
 
        MODEL.addObserver(gridPanel);


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
		try{
			gameHandler.getMoveHandler().move(direction);
		}catch(Exception exc){
			MODEL.setMessage(exc.getMessage());
		}
		MODEL.update();
	}

	private boolean is(ActionEvent e,String string){
		return e.getActionCommand().equals(string);
	}
	
	protected JButton makeNavigationButton(String imageName,
		String actionCommand, String toolTipText, boolean border) {
		String imgLocation = "../res/" + imageName;
		URL imageURL = ApplicationWindow.class.getResource(imgLocation);
		JButton button = new JButton();
		button.setActionCommand(actionCommand);
		button.setToolTipText(toolTipText);
		button.addActionListener(this);
		if (!border)
			button.setBorder(null);
		button.setBackground(new Color(238, 238, 238));
		if (imageURL != null) { 
			ImageIcon icon = new ImageIcon(imageURL, toolTipText);
			button.setIcon(icon);
		} else {
			button.setText(toolTipText);
			System.err.println("Resource " + imageName + " not found.");
		}
		return button;
	}


}
