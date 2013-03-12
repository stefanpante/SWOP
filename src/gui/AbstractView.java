/**
 * 
 */
package gui;

import java.beans.PropertyChangeEvent;

/**
 * @author jonas
 *
 */
public abstract class AbstractView {

    public abstract void modelPropertyChange(final PropertyChangeEvent evt);
	
}
