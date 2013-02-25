package handlers;

import game.Game;
import gui.view.ApplicationWindow;

import javax.swing.UIManager;

/**
 * Controller/Handler which controls the game
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class GameHandler {
    public static void main(String[] args) {
        try {
        	UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        	ApplicationWindow window = new ApplicationWindow(new GuiHandler(null));
            window.setVisisble();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
