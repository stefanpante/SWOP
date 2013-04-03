	/**
 * 
 */
package gui;

import effect.EffectValue;
import grid.Grid;
import item.Item;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import controller.GameHandler;

import player.Player;
import square.Direction;
import util.Coordinate;

/**
 * The application window of the game. Is used for the graphical user interface
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class ApplicationWindow extends AbstractView implements ActionListener {
	
	public static final int WINDOW_WIDTH 	= 800;
	public static final int WINDOW_HEIGHT 	= 523;
	public static final int GRID_WIDTH 		= 500;
	public static final int SIDEBAR_WIDTH 	= WINDOW_WIDTH - GRID_WIDTH;

	private int hSize, vSize;
	
	private GameHandler gameHandler;
	private JFrame frame;	
	private JLabel currentPlayerLabel;
	
	private GridPanel gridPanel;
	private ArrayList<Item> squareInventory;
	private ArrayList<Item> playerInventory;
	
	
	

	/**
	 * Creates a new applicationWindow with a given GuiHandler.
	 * The GuiHandler is used for the interaction between the model ant the view
	 * 
	 * @param controller	The GuiHandler that will handle all triggers.
	 */
    public ApplicationWindow(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
        this.squareInventory = new ArrayList<Item>();
        this.playerInventory = new ArrayList<Item>();
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
    		try{
	    	hSize = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter a value for the horizontal grid size (>="+Grid.MIN_HSIZE+")", 
	    			"Game Dimensions", 1));
	    	vSize = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter a value for the vertical grid size (>="+Grid.MIN_VSIZE+")", 
	    			"Game Dimensions", 1));
    		}catch(Exception exc){
    			// Alegria
    		}
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
        this.gridPanel = new GridPanel(GRID_WIDTH, GRID_WIDTH, this.vSize, this.hSize);
        gridPanel.setBounds(0, 0, GRID_WIDTH, GRID_WIDTH);	
        gridPanel.setFocusable(true);
        frame.getContentPane().add(gridPanel);
        
    	/* Cheats */
    	gridPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, InputEvent.SHIFT_MASK), "noPowerFails" );
    	gridPanel.getActionMap().put("noPowerFails", new CheatAction(3));
    	gridPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.SHIFT_MASK), "moreRemainingActions" );
    	gridPanel.getActionMap().put("moreRemainingActions", new CheatAction(4));
    	gridPanel.setFocusable(true);
        
        
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
        currentPlayerLabel.setText("...");
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
				.getBorder("TitledBorder.border"), "Actions",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
        inventoryPanel.setBounds(5, 230, SIDEBAR_WIDTH-10, 100);
        sideBarPanel.add(inventoryPanel);
        
        JButton pickup  = new JButton("Pick up item");
        pickup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
            {
				if(squareInventory.isEmpty()){
					showMessage("This square does not contain any items.");
				}else{
					Item[] a = new Item[squareInventory.size()];
					squareInventory.toArray(a);
	                Item input = (Item) JOptionPane.showInputDialog(null, "What item would you like to pick up?",
	                    "Pick up item", JOptionPane.QUESTION_MESSAGE, null,
	                    a, 
	                    null);
	                if(input != null){
	                	try{
	                		gameHandler.getPickupHandler().pickUp(input);
	                	}catch(Exception exc){
	            			showException(exc);
	            		}
	                }
				}

            }
        });      
        inventoryPanel.add(pickup);
        
        JButton use = new JButton("Use item");
        use.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
            {
				if(playerInventory.isEmpty()){
					showMessage("Your inventory is empty.");
				}else{
					Item[] a = new Item[playerInventory.size()];
					playerInventory.toArray(a);

	                Item input = (Item) JOptionPane.showInputDialog(null, "What item would you like to use?",
	                    "Use item", JOptionPane.QUESTION_MESSAGE, null,
	                    a, 
	                    null);
	                if(input != null){
		            	try{
		                	gameHandler.getUseItemHandler().useItem(input);
		            	}catch(Exception exc){
		        			showException(exc);
		        		}
	                }
				}

            }
        });      
        inventoryPanel.add(use);  
 

        
        JButton end = new JButton("End turn");
        end.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
            {
            	try{
                	gameHandler.getEndTurnHandler().endTurn();
            	}catch(Exception exc){
        			showException(exc);
        		}
            }
        }); 
        inventoryPanel.add(end);
        
        JButton newGame = new JButton("New game");
        newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
            {
				frame.dispose();
				gameHandler.main(null);
            }
        });      
        inventoryPanel.add(newGame);  
   }
    
    /**
     * Turns the Jframe on
     */
    public void setVisible(){
    	initialize();
    	this.frame.setVisible(true);
    }
    
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
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
			showException(exc);
		}
	}
	
	/**
	 * Visualize the given exception
	 * 
	 * @param 	exc
	 * 			The exception to visualize
	 */
	private void showException(Exception exc){
		exc.printStackTrace();
		JOptionPane.showMessageDialog(frame, exc.getMessage(), "Uh! Oh!", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Helper method to compate actions
	 * 
	 * @param 	e	
	 * 			The action event
	 * @param 	string
	 * 			The string to compare to
	 * @return
	 */
	private boolean is(ActionEvent e,String string){
		return e.getActionCommand().equals(string);
	}
	
	/**
	 * Create a navigation button with the given image
	 * 
	 * @param 	imageName
	 * 			The name of the image
	 * @param 	actionCommand
	 * 			The action command
	 * @param 	toolTipText
	 * 			The tool tip text
	 * @param 	border
	 * 			Show border or not
	 * @return
	 */
	protected JButton makeNavigationButton(String imageName,
		String actionCommand, String toolTipText, boolean border) {
		String imgLocation = "/res/" + imageName;
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
	
	/**
	 * Update the GUI with the received property
	 * @param	evt 
	 * 			The PropertyChangeEvent
	 */
	@SuppressWarnings("unchecked")
	@Override
    public void propertyChange(final PropertyChangeEvent evt) {
    	Object o = evt.getNewValue();
        if (evt.getPropertyName().equals(GameHandler.WALLS_PROPERTY)) {
        	this.gridPanel.setWalls((ArrayList<Coordinate>)o);
        }else if(evt.getPropertyName().equals(GameHandler.GRENADES_PROPERTY)){
        	this.gridPanel.setGrenades((ArrayList<Coordinate>)o);
        }else if(evt.getPropertyName().equals(GameHandler.PLAYERS_PROPERTY)){
        	this.gridPanel.setPlayers((ArrayList<Coordinate>)o);
        }else if(evt.getPropertyName().equals(GameHandler.POWER_FAILS_PROPERTY)){
        	this.gridPanel.setPowerFails((ArrayList<Coordinate>)o);
        }else if(evt.getPropertyName().equals(GameHandler.LIGHT_TRAILS_PROPERTY)) {
        	this.gridPanel.setLightTrails((HashMap<Player,ArrayList<Coordinate>>) o);
        }else if(evt.getPropertyName().equals(GameHandler.CURRENT_PLAYER_PROPERTY)){
        	String playerName = (String)o;
        	if(!this.currentPlayerLabel.getText().equals(playerName)){
        		this.currentPlayerLabel.setText(playerName);
        		showMessage("It's now "+playerName+ "'s turn.");
        	}
        }else if(evt.getPropertyName().equals(GameHandler.CURRENT_POSITION_PROPERTY)){
        	this.gridPanel.setCurrentPlayer((Coordinate)o);
        }else if(evt.getPropertyName().equals(GameHandler.SQUARE_INVENTORY_PROPERTY)){
        	this.squareInventory = (ArrayList<Item>)o;
        }else if(evt.getPropertyName().equals(GameHandler.PLAYER_INVENTORY_PROPERTY)){
        	this.playerInventory = (ArrayList<Item>)o;
        }else if(evt.getPropertyName().equals(GameHandler.END_TURN_PROPERTY)){
        	confirmEndTurn((String)o);
        }else if(evt.getPropertyName().equals(GameHandler.MESSAGE_PROPERTY)){
        	JOptionPane.showMessageDialog(frame, (String)o);
        }else if(evt.getPropertyName().equals(GameHandler.WIN_PROPERTY)){
        	String player = (String)o;
        	JOptionPane.showMessageDialog(frame, player+ " has won the game!");
        }else if(evt.getPropertyName().equals(GameHandler.LOSE_PROPERTY)){
        	String player = (String)o;
        	JOptionPane.showMessageDialog(frame, player+ " has lost the game...");
        }
        gridPanel.requestFocus();
    }

	/**
	 * Show the player the given message.
	 * 
	 * @param 	message
	 * 			The message to be shown
	 */
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(frame,
			    message);
	}

	/**
	 * Ask the player to confirm ending his or her turn.
	 * 
	 * @param 	msg
	 * 			The message to be shown.
	 */
	private void confirmEndTurn(String message) {
		int optionPane = JOptionPane.showConfirmDialog(frame, message, "End Turn", JOptionPane.YES_NO_OPTION);	
		if(optionPane == 0){
			gameHandler.getEndTurnHandler().confirm(true);
		}
	}
	
    private class CheatAction extends AbstractAction {
		private int type;
		
		public CheatAction(int type){
			this.type = type;
		}
		
		public void actionPerformed(ActionEvent ae){
			switch (type) {
			case 3:
				gameHandler.getGame().clearPowerFailures();
				gameHandler.fireChanges();
				System.out.println("Cheat 3 activated");
				break;
			case 4:
				gameHandler.getGame().getCurrentPlayer().endTurn(new EffectValue());
				System.out.println("Cheat 4 activated");
				break;
			}
		}
	}

}
