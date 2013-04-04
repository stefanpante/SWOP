/**
 * 
 */
package event.effect;

import java.util.ArrayList;

import effect.EffectValue;
import event.AbstractGameEvent;
import game.Game;

/**
 * @author Jonas Devlieghere
 *
 */
public abstract class EffectEvent extends AbstractGameEvent {

	private ArrayList<EffectEvent> combinations;
	
	/**
	 * @param game
	 */
	public EffectEvent(Game game) {
		super(game);
		this.combinations = new ArrayList<EffectEvent>();
	}
	
	public void add(EffectEvent event){
		combinations.add(event);
	}
	
	@Override
	public void run(){
		super.run();
		for(EffectEvent effectEvent : combinations){
			effectEvent.run();
		}
	}

}
