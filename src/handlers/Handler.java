/**
 * 
 */
package handlers;

import game.Game;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Observable;

/**
 * Handler  class.
 */
public abstract class Handler {

	private Game game;
    protected PropertyChangeSupport propertyChangeSupport;

    public Handler()
    {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    protected void firePropertyChange(String propertyName, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, new Object(), newValue);
    }
    
	public Handler(Game game) {
		this();
		this.game = game;
	}

	public Handler(Game game,  PropertyChangeListener listener) {
		this(game);
		addPropertyChangeListener(listener);
	}
	
	/**
	 * Returns the reference to the game.
	 * 
	 * @return
	 */
	protected Game getGame() {
		return this.game;
	}
	
	/**
	 * Sets the game. This normally done in constructor.
	 * 
	 * @param game
	 */
	protected void setGame(Game game) {
		this.game = game;
	}
}
