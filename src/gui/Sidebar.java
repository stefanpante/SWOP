package gui;

import java.awt.Button;
import java.awt.Graphics2D;
import java.util.ArrayList;

import simplegui.SimpleGUI;

/**
 * Test class for a simple sidebar, which can be used for the selection of items
 * 
 * @author Stefan
 *
 */
public class Sidebar extends SimpleGUI{

	/**
	 * The buttons in this Sidebar
	 */
	private ArrayList<Button> buttons;
	
	public Sidebar(String title, int width, int height){
		super(title,width,height);
		buttons = new ArrayList<Button>();
		
		
		Runnable r1 = new Runnable(){

			@Override
			public void run() {
				System.out.println("First button was clicked");
				
			}
			
		};
		
		Runnable r2 = new Runnable(){

			@Override
			public void run() {
				System.out.println("Second button was clicked");
				
			}
			
		};
		
		Runnable r3 = new Runnable(){

			@Override
			public void run() {
				System.out.println("Third button was clicked");
				
			}
			
		};
		simplegui.Button b1 = createButton(5, 10, 150, 30, r1);
		b1.setText("Button 1");
		simplegui.Button b2 = createButton(5, 50, 150, 30, r2);
		b2.setText("Button 2");
		simplegui.Button b3 = this.createButton(5, 90, 150, 30, r3);
		b3.setText("Button 3");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run(){
				SimpleGUI g = new Sidebar("test", 160 ,130);
				g.repaint();
			}
		});
	}


	@Override
	public void paint(Graphics2D arg0) {
		// TODO Auto-generated method stub
		
	}

}
