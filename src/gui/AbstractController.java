/**
 * 
 */
package gui;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @author jonas
 *
 */

public abstract class AbstractController implements PropertyChangeListener {

    private AbstractModel model;
    private AbstractView view;
	/**
	 * @return the model
	 */
	public AbstractModel getModel() {
		return model;
	}
	/**
	 * @param model the model to set
	 */
	public void setModel(AbstractModel model) {
		this.model = model;
	}
	/**
	 * @return the view
	 */
	public AbstractView getView() {
		return view;
	}
	/**
	 * @param view the view to set
	 */
	public void setView(AbstractView view) {
		this.view = view;
	}
    
	public void propertyChange(PropertyChangeEvent evt) {
            view.modelPropertyChange(evt);
    }
	
//    /**
//     * This is a convenience method that subclasses can call upon
//     * to fire property changes back to the models. This method
//     * uses reflection to inspect each of the model classes
//     * to determine whether it is the owner of the property
//     * in question. If it isn't, a NoSuchMethodException is thrown,
//     * which the method ignores.
//     *
//     * @param propertyName = The name of the property.
//     * @param newValue = An object that represents the new value
//     * of the property.
//     */
//    protected void setModelProperty(Property property, Object newValue) {
//            try {
//                Method method = model.getClass().
//                    getMethod("set"+property.toString(), new Class[] {
//                                                      newValue.getClass()
//                                                  });
//                method.invoke(model, newValue);
//            } catch (Exception ex) {
//                //  Handle exception.
//            }
//    }
}