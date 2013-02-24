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

	private ArrayList<Button> buttons;
	public Sidebar(String title, int width, int height){
		super(title,width,height);
		buttons = new ArrayList<Button>();
		simplegui.Button b1 = createButton(5, 10, 150, 30, null);
		b1.setText("button 1");
		simplegui.Button b2 = createButton(5, 50, 150, 30, null);
		b2.setText("button 2");
		simplegui.Button b3 = this.createButton(5, 90, 150, 30, null);
		b3.setText("button 3");
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
