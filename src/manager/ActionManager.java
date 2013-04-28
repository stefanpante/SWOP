package manager;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
/**
 * The Action Manager is an instance of the Mediator and Singleton pattern. There is
 * only one Action Manager and it is known globally. Action observers and observees 
 * can register with each other trough this manager.
 *                                                                                      
 * @author Dieter Castel, Jonas Devlieghere
 */
public class ActionManager {

	private ArrayList<Observable> observables;
	private ArrayList<Observer> observers;
	
	/**
	 * Private constructor prevents initialization from outside this class
	 */
    private ActionManager() { 
    	this.observables = new ArrayList<Observable>();
    	this.observers = new ArrayList<Observer>();
    }
    
    /**
    * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
    * or the first access to SingletonHolder.INSTANCE, not before.
    */
    private static class ActionManagerHolder { 
            public static final ActionManager INSTANCE = new ActionManager();
    }

    public static ActionManager getInstance() {
            return ActionManagerHolder.INSTANCE;
    }
	
	public void addObserver(Observer observer){
		if(observers.contains(observer))
			throw new IllegalArgumentException("The given observer is already registered.");
		for(Observable observable : observables){
			observable.addObserver(observer);
		}
		observers.add(observer);
	}
	
	public void addObservable(Observable observable){
		if(observables.contains(observable))
			throw new IllegalArgumentException("The given observable is already registered.");
		for(Observer observer : observers){
			observable.addObserver(observer);
		}
		observables.add(observable);
	}
	
	public void deleteObserver(Observer observer){
		observers.remove(observer);
		for(Observable observable : observables){
			observable.deleteObserver(observer);
		}
	}
	
	public void deleteObservable(Observable observable){
		observables.remove(observable);
		for(Observer observer : observers){
			observable.deleteObserver(observer);
		}
	}	
	

}
