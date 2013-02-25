/**
 * 
 */
package gui;

import javax.swing.JFrame;

/**
 * @author jonas
 *
 */
public class ApplicationWindow {

	private JFrame frame;
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final int GRID_WIDTH = 500;

    public static void main(String[] args) {
        try {
        	ApplicationWindow window = new ApplicationWindow();
            window.frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ApplicationWindow() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        
        GuiGrid xyz = new GuiGrid(GRID_WIDTH, GRID_WIDTH, 10, 10);
        frame.add(xyz);

        
    }

}
