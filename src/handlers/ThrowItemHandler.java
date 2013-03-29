package handlers;

import game.Game;

import items.Item;

import java.beans.PropertyChangeListener;

import square.Direction;

public class ThrowItemHandler extends Handler {

	public ThrowItemHandler() {
		// TODO Auto-generated constructor stub
	}

	public ThrowItemHandler(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	public ThrowItemHandler(Game game, PropertyChangeListener listener) {
		super(game, listener);
		// TODO Auto-generated constructor stub
	}
	
	public void throwItem(Item item, Direction direction){
		
	}

}
