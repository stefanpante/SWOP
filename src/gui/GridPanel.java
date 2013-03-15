/**
 * 
 */
package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import player.Player;
import utils.Coordinate;

/**
 * 
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class GridPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private int width, height, rows, cols;
	private int rowHeight, colWidth;

	private String message;
	
	private ArrayList<Coordinate> walls;
	private ArrayList<Coordinate> grenades;
	private ArrayList<Coordinate> players;
	private ArrayList<Coordinate> powerFails;
	
	private HashMap<Player,ArrayList<Coordinate>> lightTrails;
	
	private Coordinate currentPlayer;
	
	/**
	 * 
	 * Creates a new GridCanvas with the specified width and height in pixels.
	 * the grid consists of squares of size height/rows x width/cols
	 * @param width		The width of the canvas
	 * @param height	The height of the canvas
	 * @param rows		number of rows in the grid
	 * @param cols		number of columns in the gris
	 */
	GridPanel(int width, int height, int rows, int cols){
		this.width = width;
		this.height = height;
		this.rows = rows;
		this.cols = cols;
		this.rowHeight = height/rows;
		this.colWidth = width/cols;
		this.walls = getEmptyCoordinateList();
		this.grenades = getEmptyCoordinateList();
		this.players = getEmptyCoordinateList();
		this.powerFails = getEmptyCoordinateList();
		this.lightTrails = new HashMap<Player,ArrayList<Coordinate>>();
		this.currentPlayer = new Coordinate(0, 0);
	}
	
	private ArrayList<Coordinate> getEmptyCoordinateList(){
		return new ArrayList<Coordinate>();
	}
	
	/**
	 * Paints the grid
	 */
	public void paintComponent(Graphics graphics){
		super.paintComponent(graphics);
		setBackground(Color.BLACK);
		
		/* Draw gridlines */
		graphics.setColor(Color.WHITE);
	    for (int i = 1; i <= rows-1; i++)
	    	graphics.drawLine(0, i * rowHeight, width, i * rowHeight);
	    for (int i = 1; i <= cols-1; i++)
	    	graphics.drawLine(i * colWidth, 0, i * colWidth, height);
	    
	    /* Draw power failed squares */
	    for(Coordinate coordinate: powerFails){
	    	Graphics2D g = (Graphics2D)graphics;
	    	g.setColor(Color.DARK_GRAY);
	    	g.fill(new Rectangle2D.Double(coordinate.getX()*colWidth+1,coordinate.getY()*rowHeight+1,colWidth-1,rowHeight-1));
	    }
	    
	    /* Draw walls & grenades */
	    for(Coordinate coordinate : walls)
	    	DrawImage(graphics, coordinate, "wall");
		for(Coordinate coordinate : grenades)
		    DrawImage(graphics, coordinate, "lightgrenade");	
		
		graphics.setColor(Color.ORANGE);
		graphics.drawRect(getCurrentPlayer().getX()*colWidth,getCurrentPlayer().getY()*rowHeight,colWidth,rowHeight);
		
		/* Draw Players */
		for(int i = 0; i < players.size(); i++)
			DrawImage(graphics, players.get(i), "player_"+i);
		
		/* Draw LightTrails */
		for(Player player: lightTrails.keySet()) {
			for(Coordinate coord: lightTrails.get(player))
				DrawImage(graphics, coord, "cell_lighttrail_" + (player.getID() - 1));
		}
	}
	
	private void DrawImage(Graphics graphics, Coordinate coordinate, String image){
		if(coordinate == null)
			return;
		Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/"+image+".png"));
    	graphics.drawImage(img,coordinate.getX()*colWidth+1,coordinate.getY()*rowHeight+1,colWidth-1,rowHeight-1,this);
	}
	
	
	public void showMessage(){
		String msg = getMessage();
		if(msg != null && msg.length() != 0){
			JOptionPane.showMessageDialog(this, msg);
		}
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		String msg = message;
		this.message = "";
		return msg;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @param newValue
	 */
	public void setWalls(ArrayList<Coordinate> walls) {
		this.walls = walls;
		this.repaint();
	}

	/**
	 * @param o
	 */
	public void setGrenades(ArrayList<Coordinate> grenades) {
		this.grenades = grenades;
		this.repaint();		
	}

	/**
	 * @param o
	 */
	public void setPlayers(ArrayList<Coordinate> players) {
		this.players = players;
		this.repaint();		
	}
	
	/**
	 * Updates the GridPanel with the new LightTrails.
	 * 
	 * @param lightTrails
	 */
	public void setLightTrails(HashMap<Player,ArrayList<Coordinate>> lightTrails) {
		this.lightTrails = lightTrails;
		this.repaint();
	}
	
	public void setCurrentPlayer(Coordinate coordinate){
		this.currentPlayer = coordinate;
		this.repaint();
	}

	/**
	 * @return the currentPlayer
	 */
	public Coordinate getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * @return the powerFails
	 */
	public ArrayList<Coordinate> getPowerFails() {
		return powerFails;
	}

	/**
	 * @param powerFails the powerFails to set
	 */
	public void setPowerFails(ArrayList<Coordinate> powerFails) {
		this.powerFails = powerFails;
	}
	
	

}
