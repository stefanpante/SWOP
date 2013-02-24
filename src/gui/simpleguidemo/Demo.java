package gui.simpleguidemo;

import java.awt.Graphics2D;
import java.awt.Image;

import simplegui.Button;
import simplegui.SimpleGUI;


public class Demo {

	public static void main(String[] args) {
		// All code that accesses the simple GUI must run in the AWT event handling thread.
		// A simple way to achieve this is to run the entire application in the AWT event handling thread.
		// This is done by simply wrapping the body of the main method in a call of EventQueue.invokeLater.
		java.awt.EventQueue.invokeLater(new Runnable() {
			Image playerRed;
			String status = "All's well.";
			int row;
			
			public void run() {
				final SimpleGUI gui = new SimpleGUI("Demo", 400, 600) {
					
					@Override
					public void paint(Graphics2D graphics) {
						// TODO Auto-generated method stub
						graphics.drawImage(playerRed, 0, row * 40, 40, 40, null);
						graphics.drawString(status, 0, 200);
					}

					@Override
					public void handleMouseClick(int x, int y, boolean doubleClick) {
						System.out.println((doubleClick ? "Doubleclicked" : "Clicked")+" at ("+x+", "+y+")");
					}
					
				};
				
				playerRed = gui.loadImage("res/player_red.png", 40, 40);
				final Button button = gui.createButton(40, 0, 80, 40, new Runnable() {
					public void run() {
						status = "Trouble ahead.";
						gui.repaint();
					}
				});
				button.setText("Hello");
				gui.createButton(0, 40, 40, 40, new Runnable() {
					public void run() {
						row++;
						gui.repaint();
						if (row % 2 == 0) {
							button.setText(null);
							button.setImage(playerRed);
							button.setEnabled(false);
						} else {
							button.setText("Foo");
							button.setImage(null);
							button.setEnabled(true);
						}
					}
				}).setImage(gui.loadImage("simpleguidemo/arrow_E.png", 40, 40));
				Button wButton = gui.createButton(40, 40, 40, 40, null);
				wButton.setImage(gui.loadImage("simpleguidemo/arrow_W.png", 40, 40));
				wButton.setEnabled(false);
			}
		});

	}

}
