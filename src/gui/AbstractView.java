/**
 * 
 */
package gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Class representing an abstract view
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante

 */
public abstract class AbstractView implements PropertyChangeListener {

    public abstract void propertyChange(final PropertyChangeEvent evt);
	
}
