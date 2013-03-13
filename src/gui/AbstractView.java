/**
 * 
 */
package gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @author jonas
 *
 */
public abstract class AbstractView implements PropertyChangeListener {

    public abstract void propertyChange(final PropertyChangeEvent evt);
	
}
