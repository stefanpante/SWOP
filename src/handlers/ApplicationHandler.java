/**
 * 
 */
package handlers;

/**
 * @author jonas
 *
 */
public class ApplicationHandler extends Handler {
	
	private GuiHandler guiHandler;
	private GameHandler gameHander;
	
    public static void main(String[] args) {
    	ApplicationHandler applicationHandler = new ApplicationHandler();
    	applicationHandler.start();
    }
    
    public void start(){
    	initialize();
    }
    
    private void initialize(){
    	this.gameHander = new GameHandler();
    	this.guiHandler = new GuiHandler();
    }
}
