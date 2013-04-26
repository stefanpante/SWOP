package manager;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
/**
 * The Action Manager is an instance of the Mediator and Singleton pattern. There is
 * only one Action Manager and it is known globally.
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
		observers.add(observer);
		for(Observable observable : observables){
			observable.addObserver(observer);
		}
	}
	
	public void addObservable(Observable observable){
		observables.add(observable);
		for(Observer observer : observers){
			observable.addObserver(observer);
		}
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
